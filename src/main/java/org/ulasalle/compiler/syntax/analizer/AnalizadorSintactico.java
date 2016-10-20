/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.ulasalle.compiler.util.Respuesta;
import org.ulasalle.compiler.util.Token;

/**
 *
 * @author francisco
 */
public class AnalizadorSintactico //implements Analizador
{

    private static TablaAnalisis tablaAnalisis;

    public AnalizadorSintactico()
    {
        if (tablaAnalisis == null)
            tablaAnalisis = new TablaAnalisis();
    }

    /**
     *
     * @param tokens
     * @return
     */
    public Respuesta analizar(List<Token> tokens)
    {
        List<ErrorSintactico> errores=new ArrayList<>();
        Stack<Simbolo> pila = new Stack<>();
        pila.add(tablaAnalisis.getNoTerminalBase());
        int indiceTokens = 0;
        while (!pila.empty())
            if (esAceptadoPorPila(pila, tokens.get(indiceTokens)))
            {
                pila.pop();
                if ((indiceTokens + 1) < tokens.size())
                    indiceTokens++;
            } else
            {
                TipoError tipo = derivarToken(pila, tablaAnalisis, tokens.get(indiceTokens));
                if (tipo == TipoError.TOKEN_IRRECONOCIBLE)
                {
                    errores.add(new ErrorSintactico(tipo, tokens.get(indiceTokens)));
                    if ((indiceTokens + 1) < tokens.size())
                    indiceTokens++;
                    else break;
                }
                else if (tipo==TipoError.NOTERMINAL_IRRECONOCIBLE)
                {
                    errores.add(new ErrorSintactico(tipo, tokens.get(indiceTokens)));
                    pila.pop();
                }
                else if (pila.isEmpty() && (indiceTokens + 1) < tokens.size())
                {
                    tipo=TipoError.TOKENS_SIN_LEER;
                    errores.add(new ErrorSintactico(tipo, tokens.get(indiceTokens)));
                }
            }
        if (pila.empty())
            System.out.println("Esta vacia");
        return new RespuestaSintactica("nombreArchivo", new ArrayList<Cuadruplo>(), errores);
    }

    private boolean esAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }

    private TipoError derivarToken(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, Token token)
    {
        if (pila.peek().getClass() == Terminal.class)
        {
            return TipoError.TOKEN_IRRECONOCIBLE;
        }
        int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(token), (NoTerminal) pila.peek());
        if (indiceRegla == -1)
        {
            return TipoError.NOTERMINAL_IRRECONOCIBLE;
        } else
            reemplazarSimbolos(pila, tablaAnalisis, indiceRegla);
        return TipoError.SIN_ERRORES;
    }

    private void reemplazarSimbolos(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, int indiceRegla)
    {
        if (pila.peek().getClass() == NoTerminal.class)
        {
            pila.pop();
            Simbolo[] simbolos = tablaAnalisis.getReglaDeProduccion(indiceRegla);
            for (int i = (simbolos.length - 1); i >= 0; i--)
                pila.add(simbolos[i]);

        }
    }

    private void imprimirPila(Stack<Simbolo> pila)
    {
        for (Simbolo simbolo : pila)
            System.out.print((simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getValor()));
        System.out.println();
    }

}
