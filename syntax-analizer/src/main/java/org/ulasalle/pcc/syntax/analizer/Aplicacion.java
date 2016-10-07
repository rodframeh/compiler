/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class Aplicacion
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token(TipoToken.PALABRA_RESERVADA, "int"));
        tokens.add(new Token(TipoToken.IDENTIFICADOR, "bc"));
        tokens.add(new Token(TipoToken.DELIMITADOR, ";"));
        tokens.add(new Token(TipoToken.PALABRA_RESERVADA, "int"));
        tokens.add(new Token(TipoToken.IDENTIFICADOR, "ab"));
        tokens.add(new Token(TipoToken.DELIMITADOR, ";"));
        AnalizadorSintactico sintactico = new AnalizadorSintactico();
        sintactico.analizar(tokens);

    }

}
