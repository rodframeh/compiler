package org.ulasalle.compiler;

import java.util.ArrayList;
import java.util.List;
import org.ulasalle.compiler.syntax.analizer.AnalizadorSintactico;
import org.ulasalle.compiler.util.TipoToken;
import org.ulasalle.compiler.util.Token;

/**
 *
 * @author francisco
 */
public class Aplicacion
{
    
    public static void main(String[] args)
    {
//        try
//        {
//            AnalizadorLexico lexico = new AnalizadorLexico();
//            Respuesta respuestaLexico = lexico.analizar("examples/Example02.programa");
//            List<Token> tokens=(List<Token>) respuestaLexico.getResultados();
//            tokens.forEach((token) ->
//            {
//                System.out.println(token.getLexema() + " - " + token.getTipoToken());
//            });
//            List<ErrorLexico> erroresLexicos =(List<ErrorLexico>) respuestaLexico.getErrores();
//            erroresLexicos.forEach((error) ->
//            {
//                System.out.println(error.getDescripcion());
//            });
//            
//            AnalizadorSintactico sintactico = new AnalizadorSintactico();
//            Respuesta respuestaSintactico =sintactico.analizar(tokens);
//            List<ErrorSintactico> erroresSintacticos = (List<ErrorSintactico>) respuestaSintactico.getErrores();
//            erroresSintacticos.forEach((error)->
//            {
//                System.out.println(error.getDescripcion());
//            });
//             
//        } 
//        catch (IOException ex)
//        {
//            Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
//        }
        List<Token> tokens=new ArrayList<>();
        tokens.add(new Token(TipoToken.IDENTIFICADOR, "Programa"));
        tokens.add(new Token(TipoToken.DELIMITADOR, "("));
        tokens.add(new Token(TipoToken.DELIMITADOR, ")"));
        tokens.add(new Token(TipoToken.DELIMITADOR, "{"));
        tokens.add(new Token(TipoToken.PALABRA_RESERVADA, "entero"));
        tokens.add(new Token(TipoToken.IDENTIFICADOR, "a"));
        tokens.add(new Token(TipoToken.OPERADOR, "="));
        tokens.add(new Token(TipoToken.CONST_NUMERICA, "5"));
                tokens.add(new Token(TipoToken.DELIMITADOR, ";"));
                  tokens.add(new Token(TipoToken.IDENTIFICADOR, "a"));
        tokens.add(new Token(TipoToken.OPERADOR, "="));
                          tokens.add(new Token(TipoToken.IDENTIFICADOR, "a"));
        tokens.add(new Token(TipoToken.OPERADOR,"*"));
        tokens.add(new Token(TipoToken.CONST_NUMERICA, "7"));
        tokens.add(new Token(TipoToken.DELIMITADOR, ";"));
        tokens.add(new Token(TipoToken.DELIMITADOR, "}"));
        
        AnalizadorSintactico sintactico=new AnalizadorSintactico();
        sintactico.analizar(tokens);
    }
}
