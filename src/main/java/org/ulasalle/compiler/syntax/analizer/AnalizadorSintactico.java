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
                short resultado = derivarToken(pila, tablaAnalisis, tokens.get(indiceTokens));
                if (resultado == 1)
                    if ((indiceTokens + 1) < tokens.size())
                    indiceTokens++;
                    else break;
                else if (resultado == 2)
                    pila.pop();
                else if (pila.isEmpty() && (indiceTokens + 1) < tokens.size())
                    System.out.println("error 3 " + (indiceTokens + 1));

            }
        if (pila.empty())
            System.out.println("Esta vacia");
        return new RespuestaSintactica("nombreArchivo", new ArrayList<Cuadruplo>(), new ArrayList<ErrorSintactico>());
    }

    private boolean esAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }

    private short derivarToken(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, Token token)
    {
        if (pila.peek().getClass() == Terminal.class)
        {
            System.out.println("Error 1");
            return 1;
        }
        int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(token), (NoTerminal) pila.peek());
        if (indiceRegla == -1)
        {
            System.out.println("Error 2");
            return 2;
        } else
            reemplazarSimbolos(pila, tablaAnalisis, indiceRegla);
        return 0;
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
