/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author francisco
 */
public class AnalizadorSintactico
{

    public void analizar(List<Token> tokens)
    {
        Stack<Simbolo> pila = new Stack<>();
        TablaDeAnalisis tablaDeAnalisis = new TablaDeAnalisis();
        //insertamos no terminal base de la pila
        pila.add(tablaDeAnalisis.getNoTerminalBase());
        int indiceDeTokens = 0;
        int indiceDeRegla = -1;

        while (indiceDeTokens < tokens.size())
        {
            System.out.println("Indice: " + indiceDeTokens);
            if (pila.peek().getClass() == Terminal.class
                    && tokens.get(indiceDeTokens).getLexema().equals(((Terminal) pila.peek()).getLexema()))
            {
                System.out.println("Sacando:" + ((Terminal) pila.pop()).getLexema());
                indiceDeTokens++;
            } else
            {
                if (pila.peek().getClass() == Terminal.class)
                {
                    System.out.println(((Terminal) pila.peek()).getLexema());
                    System.out.println("Error 1");
                    return;
                }
                
//                if (tokens.get(indiceDeTokens).getTipoToken() == TipoToken.IDENTIFICADOR)
//                    tokens.get(indiceDeTokens).setLexema("&IDENTIFICADOR&");
//                else if (tokens.get(indiceDeTokens).getTipoToken() == TipoToken.CONST_NUMERICA)
//                    tokens.get(indiceDeTokens).setLexema("&CONSTANTE_NUMERICA&");

                indiceDeRegla = tablaDeAnalisis.getIndiceDeReglaDeProduccion( tokens.get(indiceDeTokens), (NoTerminal) pila.peek());

                System.out.println("Regla: " + indiceDeRegla);

                if (indiceDeRegla == -1)
                {
                    System.out.println("Error 2");
                    return;
                } else
                    if (pila.peek().getClass() == NoTerminal.class)
                    {
                        pila.pop();
                        List<Simbolo> simbolos = tablaDeAnalisis.getReglaDeProduccion(indiceDeRegla);
                        //Collections.reverse(simbolos);
                        for (int i = (simbolos.size() - 1); i >= 0; i--)
                            pila.add(simbolos.get(i));
                        //pila.addAll(simbolos);
                        imprimirPila(pila);
                    }
            }

        }
        if (pila.empty())
            System.out.println("Esta vacia");
    }

    private void imprimirPila(Stack<Simbolo> pila)
    {

        for (Simbolo simbolo : pila)
            System.out.print((simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getNombre()));
        System.out.println();

    }

}
