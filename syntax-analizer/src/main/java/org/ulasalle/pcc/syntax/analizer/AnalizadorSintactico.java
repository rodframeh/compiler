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
        TablaAnalisis tablaAnalisis = new TablaAnalisis();
        pila.add(tablaAnalisis.getNoTerminalBase());
        int indiceTokens = 0;
        while (!pila.empty())
            if (esAceptadoPorPila(pila, tokens.get(indiceTokens)))
            {
                pila.pop();
                if ((indiceTokens + 1) < tokens.size())
                    indiceTokens++;
            } else
                derivarToken(pila, tablaAnalisis,tokens.get(indiceTokens));
        if (pila.empty())
            System.out.println("Esta vacia");
    }

    private boolean esAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }
    
    private void derivarToken(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, Token token)
    {
        if (pila.peek().getClass() == Terminal.class)
        {
            System.out.println("Error 1");
            return;
        }
        int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(token), (NoTerminal) pila.peek());
        if (indiceRegla == -1)
        {
            System.out.println("Error 2");
            return;
        } else reemplazarSimbolos(pila, tablaAnalisis, indiceRegla);
    }

    private void reemplazarSimbolos(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis,int indiceRegla)
    {
        if (pila.peek().getClass() == NoTerminal.class)
        {
            pila.pop();
            List<Simbolo> simbolos = tablaAnalisis.getReglaDeProduccion(indiceRegla);
            for (int i = (simbolos.size() - 1); i >= 0; i--)
                pila.add(simbolos.get(i));
            imprimirPila(pila);
        }
    }
    
    private void imprimirPila(Stack<Simbolo> pila)
    {

        for (Simbolo simbolo : pila)
            System.out.print((simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getNombre()));
        System.out.println();

    }

}
