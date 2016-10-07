package org.ulasalle.lexical.analizer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{

    public static void main(String[] args) throws IOException
    {
        try
        {
            AnalizadorLexico analizador = new AnalizadorLexico();
            ConjuntoLexico cl = analizador.analizar("/home/francisco/test.programa");

            for (Token token : cl.getTokens())
                System.out.println(token.getLexema() + " - " + token.getTipoToken());

            for (ErrorLexico error : cl.getErroresLexicos())
                System.out.println(error.getDescripcion());

        } catch (IOException ex)
        {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
