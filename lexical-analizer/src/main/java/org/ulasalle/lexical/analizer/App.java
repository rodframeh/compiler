package org.ulasalle.lexical.analizer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
       Automata automata=new  Automata();
       automata.mover(')');
        
        try {
   
            AnalizadorLexico analizador = new AnalizadorLexico();
            
            
            for(Token token:analizador.analizar("/home/christianlp/textoUno.programa")){
                System.out.println(token.getLexema() +" - "+token.getTipoToken());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

 


    }

}
