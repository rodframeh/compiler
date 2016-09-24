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
    private List<Token> tokens;
    private boolean flag;
    
    public AnalizadorLexico(){
        palabrasReservadas=new ArrayList<>();
        tokens=new ArrayList<>();
        errorLexicos=new ArrayList<>();
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

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    
    
    public void analizar(String path) throws IOException {
        StringBuilder sourceCode = new StringBuilder();
        try (Stream<String> sourceFileStream = Files.lines((new File(path)).toPath())) {
            sourceFileStream.forEach(lineStream -> sourceCode.append(lineStream).append("\n"));
        }
     
        int index = 0;
        char character = sourceCode.charAt(index);
        StringBuilder buffer = new StringBuilder();
        
        if(automata.mover(character)){
            buffer.append(character);
        }else{
            if(automata.esFinal()){
                TipoToken tipoToken=automata.obtenerTipo();
                if(tipoToken == TipoToken.IDENTIFICADOR){
                    for(String palabraReservada:palabrasReservadas){
                        if(buffer.toString().equals(palabraReservada)){
                            tipoToken=TipoToken.PALABRA_RESERVADA;
                        }
                    }
                }
                tokens.add(new Token(tipoToken, buffer.toString()));// reinicio todo
                buffer=new StringBuilder();
                automata.reset();
                
            }else{
                if(buffer.length()>0){
                    errorLexicos.add(new ErrorLexico(buffer.toString(), 0));
                    index++;
                }else{
                    if(character==' '){
                        while(character==' '){
                            character=sourceCode.charAt(index++);
                        }
                    }else{
                        errorLexicos.add(new ErrorLexico(buffer.toString(), 0));
                        index++;
                    }
                    
                }
            }
        }
    }
    
    

}
