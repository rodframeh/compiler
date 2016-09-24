package org.ulasalle.lexical.analizer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        
        try {
   
            AnalizadorLexico analizador = new AnalizadorLexico();
            
            analizador.analizar("/home/christianlp/textoUno.programa");
            for(Token token:analizador.getTokens()){
                System.out.println(token);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
