/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.lexical.analizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author christianlp
 */
public class AnalizadorLexico {
    
    private Automata automata;
    private List<String> palabrasReservadas;
    private List<ErrorLexico> errorLexicos;
    
    public AnalizadorLexico(){
        automata=new Automata();
        errorLexicos=new ArrayList<>();
        llenarPalabrasReservadas();
    }

    private void llenarPalabrasReservadas(){
               palabrasReservadas=new ArrayList<>();
            palabrasReservadas.add("Programa");
        palabrasReservadas.add("entero");
        palabrasReservadas.add("Si");
        palabrasReservadas.add("OsiNo");
        palabrasReservadas.add("Mientras");
        palabrasReservadas.add("LeerTeclado");
        palabrasReservadas.add("EscribirPantalla");
    }
    
    public List<ErrorLexico> getErrorLexicos() {
        return errorLexicos;
    }

    public void setErrorLexicos(List<ErrorLexico> errorLexicos) {
        this.errorLexicos = errorLexicos;
    }
    
    private String leerArchivo(String path) throws IOException
    {
        StringBuilder sourceCode = new StringBuilder();
        
        try (Stream<String> sourceFileStream = Files.lines((new File(path)).toPath())) 
        {
            sourceFileStream.forEach(lineStream -> sourceCode.append(lineStream).append("\n"));
        }
        
        return sourceCode.toString().trim();
    }
    
    private boolean esBlanco( char c )
    {
        return c == ' ' || c == '\t' || c == '\n';
    }
    
    private Token crearToken(StringBuilder buffer){
        String lexema = buffer.toString();
        TipoToken tipoToken=automata.obtenerTipo();
        buffer.delete(0, buffer.length());
        automata.reset();
        if(tipoToken!=TipoToken.COMENTARIO && tipoToken!=TipoToken.COMENTARIO_MULTILINEA)
        {
            if( tipoToken == TipoToken.IDENTIFICADOR && palabrasReservadas.stream().filter( p->p.equals(lexema) ).count()>0)
                tipoToken=TipoToken.PALABRA_RESERVADA;
     
            return new Token(tipoToken, lexema);
        }
        return null;
    }
    
    public List<Token> analizar(String path) throws IOException 
    {
        List<Token> tokens=new ArrayList<>();
        
        String codigoFuente = leerArchivo(path);
//        System.out.println("{"+codigoFuente+"}");
        StringBuilder buffer = new StringBuilder();
        
        for(int index = 0;index<codigoFuente.length();)
        {          
            char caracter = codigoFuente.charAt(index);
            
            if(automata.mover(caracter))
            {
                buffer.append(caracter);
                if(index==codigoFuente.length()-1)
                {
                    Token token=crearToken(buffer);
                    if (token!=null) tokens.add(token);
                }
                index++;
            }
            else
            {
                if(automata.esFinal())
                {
                    Token token=crearToken(buffer);
                    if (token!=null) tokens.add(token);
                }
                else
                {
                    if(buffer.length()>0)
                    {
                        errorLexicos.add(new ErrorLexico(buffer.toString(), 0));
                        index++;
                    }
                    else
                    {
                        if(esBlanco(caracter))
                        {
                            do  caracter=codigoFuente.charAt(++index);
                            while(esBlanco(caracter));
                        }
                        else
                        {
                            errorLexicos.add(new ErrorLexico(buffer.toString(), 0));
                            index++;
                        }

                    }
                }
            }
        }
        return tokens;
    }
    
    

}
