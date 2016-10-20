/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ulasalle.compiler.lexical.analizer.AnalizadorLexico;
import org.ulasalle.compiler.lexical.analizer.ConjuntoLexico;
import org.ulasalle.compiler.lexical.analizer.ErrorLexico;
import org.ulasalle.compiler.syntax.analizer.AnalizadorSintactico;
import org.ulasalle.compiler.util.Token;

/**
 *
 * @author francisco
 */
public class Aplicacion
{
    
    public static void main(String[] args)
    {
        try
        {
            AnalizadorLexico lexico = new AnalizadorLexico();
            ConjuntoLexico conjuntoLexico = lexico.analizar("/home/francisco/programa.programa");
            
//                        for (Token token : conjuntoLexico.getTokens())
//                System.out.println(token.getLexema() + " - " + token.getTipoToken());
//
//            for (ErrorLexico error : conjuntoLexico.getErroresLexicos())
//                System.out.println(error.getDescripcion());
            
            AnalizadorSintactico sintactico = new AnalizadorSintactico();
            sintactico.analizar(conjuntoLexico.getTokens());
            

            
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
