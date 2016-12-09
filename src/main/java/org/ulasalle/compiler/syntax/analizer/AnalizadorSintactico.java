package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Simbolo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.ulasalle.compiler.util.Token;

public class AnalizadorSintactico
{
    
    private static TablaAnalisis tablaAnalisis;
    
    public AnalizadorSintactico()
    {
        if (tablaAnalisis == null)
            tablaAnalisis = new TablaAnalisis();
    }
    
    private int encontrarFinError(List<Token> tokens, int indiceTokens, Stack<Simbolo> pila, List<Cuadruplo> cuadruplos, Temporal temporal)
    {
        for (; indiceTokens < tokens.size(); indiceTokens++)
            if (tokens.get(indiceTokens).getLexema().equals(";"))
            {
                while (!pila.isEmpty())
                    if (pila.peek() instanceof NoTerminal)
                    {
                        int indiceReglaProduccion = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(";"), (NoTerminal) pila.peek());
                        if (indiceReglaProduccion == -1)
                            pila.pop();
                        else
                        {
                            this.reemplazarSimbolos(pila, tablaAnalisis, indiceTokens, cuadruplos, temporal);
                            break;
                        }
                    } else if (((Terminal) pila.peek()).equals(new Terminal(";")))
                    {
                        pila.pop();
                        break;
                    } else
                        pila.pop();
                return ++indiceTokens;
            } else
                indiceTokens++;
        return indiceTokens;
    }
    
    private void configurarCuadruplo(int i, List<Cuadruplo> cuadruplos, int indiceCuadruplo, Simbolo simbolo)
    {
        switch (i)
        {
            case 0:
                cuadruplos.get(indiceCuadruplo).setResultado(simbolo);
                break;
            case 1:
                cuadruplos.get(indiceCuadruplo).setOperando1(simbolo);
                break;
            case 2:
                cuadruplos.get(indiceCuadruplo).setOperacion(simbolo);
                break;
            case 3:
                cuadruplos.get(indiceCuadruplo).setOperando2(simbolo);
                break;
        }
    }
    
    private void construirCuadruplo(Simbolo simbolo, Simbolo[] plantilla, List<Cuadruplo> cuadruplos, int indiceCuadruplo)
    {
        for (int i = 0; i < plantilla.length; i++)
            if (plantilla[i] != null)
                if (simbolo instanceof Terminal && plantilla[i] instanceof Terminal && ((Terminal) plantilla[i]).equals(simbolo))
                {
                    configurarCuadruplo(i, cuadruplos, indiceCuadruplo, simbolo);
                    break;
                } else if (simbolo instanceof NoTerminal && plantilla[i] instanceof NoTerminal && ((NoTerminal) plantilla[i]).equals(simbolo))
                {
                    configurarCuadruplo(i, cuadruplos, indiceCuadruplo, simbolo);
                    break;
                }
    }
    
    private void crearCuadruplo(Simbolo simbolo, Simbolo[] plantilla, List<Cuadruplo> cuadruplos)
    {
        Cuadruplo cuadruplo = new Cuadruplo();
        cuadruplo.setIdRegla(simbolo.getIdRegla());
        cuadruplo.setIndiceRegla(simbolo.getIndiceRegla());
        cuadruplo.setResultado(simbolo.getPadre());
        if (plantilla[2] != null)
            cuadruplo.setOperacion(plantilla[2]);
        cuadruplos.add(cuadruplo);
        construirCuadruplo(simbolo, plantilla, cuadruplos, cuadruplos.size() - 1 == 0 ? 0 : cuadruplos.size() - 1);
    }
    
    private void generarBloque(Simbolo simbolo)
    {
        Bloque bloque = null;
        if (simbolo instanceof NoTerminal && ((NoTerminal) simbolo).isBloque())
            if (simbolo.getPadre() != null)
                if (simbolo.getPadre().getContexto() == null)
                    bloque = new Bloque();
                else
                    bloque = new Bloque(simbolo.getPadre().getContexto());
            else
                bloque = new Bloque();
        else if (simbolo.getPadre() != null)
            bloque = simbolo.getPadre().getContexto();
        if (bloque != null)
            simbolo.setContexto(bloque);
    }
    
    private void generarCuadruplo(Simbolo simbolo, List<Cuadruplo> cuadruplos)
    {
        Simbolo[] plantilla = tablaAnalisis.getPlantilla(simbolo.getIndiceRegla());
        if (plantilla.length > 0)
            if (cuadruplos.isEmpty())
                crearCuadruplo(simbolo, plantilla, cuadruplos);
            else
            {
                for (int indiceCuadruplo = 0; indiceCuadruplo < cuadruplos.size(); indiceCuadruplo++)
                    if (cuadruplos.get(indiceCuadruplo).getIdRegla() == simbolo.getIdRegla())
                    {
                        construirCuadruplo(simbolo, plantilla, cuadruplos, indiceCuadruplo);
                        return;
                    }
                if (cuadruplos.get(cuadruplos.size() - 1).getIdRegla() != simbolo.getIdRegla())
                    crearCuadruplo(simbolo, plantilla, cuadruplos);
            }
    }
    
    private void acumularIdentificadores(Map<Integer, Integer> identificadores, int identificador)
    {
        int acumulado = (identificadores.get(identificador) == null ? 1 : identificadores.get(identificador) + 1);
        identificadores.put(identificador, acumulado);
    }
    
    private void eliminarAsignacionSimple(List<Cuadruplo> cuadruplos)
    {
        //elimino = null
        List<Cuadruplo> eliminados = new ArrayList<>();
        for (Cuadruplo cuadruplo : cuadruplos)
            if (((cuadruplo.getOperacion() instanceof Terminal
                    && ((Terminal) cuadruplo.getOperacion()).getLexema().equals("="))
                    || cuadruplo.getOperacion() == null)
                    && cuadruplo.getOperando2() == null)
                eliminados.add(cuadruplo);
        if (eliminados.size() > 0)
            for (Cuadruplo eliminado : eliminados)
                for (int c = 0; c < cuadruplos.size(); c++)
                {
                    int identificador = eliminado.getResultado().getIdSimbolo();
                    if (cuadruplos.get(c).getOperando1() != null && cuadruplos.get(c).getOperando1().getIdSimbolo() == identificador)
                        cuadruplos.get(c).setOperando1(eliminado.getOperando1());
                    else if (cuadruplos.get(c).getOperando2() != null && cuadruplos.get(c).getOperando2().getIdSimbolo() == identificador)
                        cuadruplos.get(c).setOperando2(eliminado.getOperando1());
                    else if (cuadruplos.get(c).getOperacion() != null && cuadruplos.get(c).getOperacion().getIdSimbolo() == identificador)
                        cuadruplos.get(c).setOperacion(eliminado.getOperando1());
                }
        for (Cuadruplo eliminado : eliminados)
            cuadruplos.remove(eliminado);
    }
    
    private void eliminarSimbolosObsoletos(List<Cuadruplo> cuadruplos)
    {
        //elimino simbolos obsoletos
        Map<Integer, Integer> identificadores = new HashMap<>();
        for (int i = 0; i < cuadruplos.size(); i++)
        {
            if (cuadruplos.get(i).getResultado() instanceof NoTerminal)
                acumularIdentificadores(identificadores, cuadruplos.get(i).getResultado().getIdSimbolo());
            if (cuadruplos.get(i).getOperando1() instanceof NoTerminal)
                acumularIdentificadores(identificadores, cuadruplos.get(i).getOperando1().getIdSimbolo());
            if (cuadruplos.get(i).getOperacion() instanceof NoTerminal)
                acumularIdentificadores(identificadores, cuadruplos.get(i).getOperacion().getIdSimbolo());
            if (cuadruplos.get(i).getOperando2() instanceof NoTerminal)
                acumularIdentificadores(identificadores, cuadruplos.get(i).getOperando2().getIdSimbolo());
        }
        
        identificadores.keySet().stream()
                .filter((identificador) -> (identificadores.get(identificador) == 1))
                .forEachOrdered((identificador) ->
                {
                    cuadruplos.forEach((cuadruplo) ->
                    {
                        if (cuadruplo.getOperando1() instanceof NoTerminal
                                && cuadruplo.getOperando1().getIdSimbolo() == identificador)
                            cuadruplo.setOperando1(null);
                        else if (cuadruplo.getOperando2() instanceof NoTerminal
                                && cuadruplo.getOperando2().getIdSimbolo() == identificador)
                            cuadruplo.setOperando2(null);
                    });
                });
        
    }
    
    private void eliminarSimpleRelacion(List<Cuadruplo> cuadruplos)
    {
        //elimino los null null
        List<Cuadruplo> eliminados = new ArrayList<>();
        for (Cuadruplo cuadruplo : cuadruplos)
            if (cuadruplo.getOperacion() == null
                    && cuadruplo.getOperando2() == null)
                eliminados.add(cuadruplo);
        if (eliminados.size() > 0)
            for (Cuadruplo eliminado : eliminados)
                for (int c = 0; c < cuadruplos.size(); c++)
                {
                    int identificador = eliminado.getResultado().getIdSimbolo();
                    if (cuadruplos.get(c).getOperando1() != null && cuadruplos.get(c).getOperando1().getIdSimbolo() == identificador)
                        cuadruplos.get(c).setOperando1(eliminado.getOperando1());
                    else if (cuadruplos.get(c).getOperando2() != null && cuadruplos.get(c).getOperando2().getIdSimbolo() == identificador)
                        cuadruplos.get(c).setOperando2(eliminado.getOperando1());
                    else if (cuadruplos.get(c).getOperacion() != null && cuadruplos.get(c).getOperacion().getIdSimbolo() == identificador)
                        cuadruplos.get(c).setOperacion(eliminado.getOperando1());
                }
        for (Cuadruplo eliminado : eliminados)
            cuadruplos.remove(eliminado);
        
    }
    
    private void eliminarRelacionOperacion(List<Cuadruplo> cuadruplos)
    {
        List<Cuadruplo> eliminados = new ArrayList<>();
        for (Cuadruplo cuadruplo : cuadruplos)
            if ((cuadruplo.getOperando1() == null && cuadruplo.getOperacion() != null && cuadruplo.getOperando2() != null))
                eliminados.add(cuadruplo);
        
        if (eliminados.size() > 0)
            for (Cuadruplo eliminado : eliminados)
                for (int c = 0; c < cuadruplos.size(); c++)
                {
                    int identificador = eliminado.getResultado().getIdSimbolo();
                    if (cuadruplos.get(c).getOperando2() != null && cuadruplos.get(c).getOperando2().getIdSimbolo() == identificador)
                    {
                        cuadruplos.get(c).setOperacion(eliminado.getOperacion());
                        cuadruplos.get(c).setOperando2(eliminado.getOperando2());
                    }
                }
        
        for (Cuadruplo eliminado : eliminados)
            cuadruplos.remove(eliminado);
    }
    
    private void configurarVariables(List<Cuadruplo> cuadruplos)
    {
        
        List<Cuadruplo> eliminados = new ArrayList<>();
        for (Cuadruplo cuadruplo : cuadruplos)
            if (convertirAString(cuadruplo.getOperando1()).equals("entero")
                    && convertirAString(cuadruplo.getOperacion()).equals("declaracion"))
                eliminados.add(cuadruplo);
        for (int indice = 0; indice < cuadruplos.size(); indice++)
            for (Cuadruplo eliminado : eliminados)
                if (eliminado.equals(cuadruplos.get(indice)))
                {
                    boolean candado = true;
                    while (candado)
                    {
                        candado = false;
                        indice++;
                        if (indice >= cuadruplos.size())
                            break;
                        while (cuadruplos.get(indice).getOperando1() instanceof NoTerminal
                                && convertirAString(cuadruplos.get(indice).getOperacion()).equals("declaracion"))
                        {
                            int identificador = cuadruplos.get(indice).getOperando1().getIdSimbolo();
                            indice++;
                            if (indice >= cuadruplos.size())
                                break;
                            if (identificador == cuadruplos.get(indice).getResultado().getIdSimbolo())
                            {
                                cuadruplos.get(indice - 1).setOperando1(eliminado.getOperando1());
                                cuadruplos.get(indice - 1).setOperando2(cuadruplos.get(indice).getOperando1());
                            }
                            indice++;
                            candado = true;
                        }
                        while (cuadruplos.get(indice).getOperando1() instanceof Terminal
                                && convertirAString(cuadruplos.get(indice).getOperacion()).equals("declaracion")
                                && cuadruplos.get(indice).getOperando2() == null)
                        {
                            cuadruplos.get(indice).setOperando2(cuadruplos.get(indice).getOperando1());
                            cuadruplos.get(indice).setOperando1(eliminado.getOperando1());
                            candado = true;
                        }
                    }
                }
        
        for (Cuadruplo eliminado : eliminados)
            cuadruplos.remove(eliminado);
    }
    
    private void fijarBloquesACuadruplos(List<Cuadruplo> cuadruplos)
    {
        for (Cuadruplo cuadruplo : cuadruplos)
            if (cuadruplo.getOperando1() != null)
                cuadruplo.setBloque(cuadruplo.getOperando1().getContexto());
            else if (cuadruplo.getOperando2() != null)
                cuadruplo.setBloque(cuadruplo.getOperando2().getContexto());
            else if (cuadruplo.getOperacion() != null)
                cuadruplo.setBloque(cuadruplo.getOperacion().getContexto());
    }
    
    private void eliminarNoOperacion(List<Cuadruplo> cuadruplos)
    {
        
        List<Cuadruplo> eliminados = new ArrayList<>();
        for (Cuadruplo cuadruplo : cuadruplos)
            if (cuadruplo.getOperando1() instanceof NoTerminal && cuadruplo.getOperacion() == null && cuadruplo.getOperando2() instanceof NoTerminal)
                eliminados.add(cuadruplo);
        
        for (Cuadruplo eliminado : eliminados)
            cuadruplos.remove(eliminado);
    }
    
    private void reducirCuadruplos(List<Cuadruplo> cuadruplos)
    {
        eliminarSimbolosObsoletos(cuadruplos);
        eliminarSimpleRelacion(cuadruplos);
        eliminarAsignacionSimple(cuadruplos);
        eliminarRelacionOperacion(cuadruplos);
        eliminarNoOperacion(cuadruplos);
    }
    
    private int controlarErrores(List<Token> tokens, List<Error> errores, Stack<Simbolo> pila, int indiceTokens, List<Cuadruplo> cuadruplos, Temporal temporal)
    {
        if (pila.peek().getClass() == Terminal.class)//obligatorio para el metodo reeemplazar simbolo
        {
            errores.add(new Error(TipoError.TOKEN_IRRECONOCIBLE, tokens.get(indiceTokens)));
            return encontrarFinError(tokens, indiceTokens, pila, cuadruplos, temporal);// -1 termina
        } else
        {
            int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(tokens.get(indiceTokens)), (NoTerminal) pila.peek());
            if (indiceRegla == -1)
            {
                errores.add(new Error(TipoError.NOTERMINAL_IRRECONOCIBLE, tokens.get(indiceTokens)));
                return encontrarFinError(tokens, indiceTokens, pila, cuadruplos, temporal);// -1 termina
            } else if (pila.isEmpty() && (indiceTokens + 1) < tokens.size())
                errores.add(new Error(TipoError.TOKENS_SIN_LEER, tokens.get(indiceTokens)));
            else
                reemplazarSimbolos(pila, tablaAnalisis, indiceRegla, cuadruplos, temporal);
        }
        return indiceTokens;
    }
    
    private Simbolo configurarSimbolo(Simbolo simbolo, Token token)
    {
        if (simbolo instanceof Terminal)
        {
            Terminal terminal = (Terminal) simbolo;
            if (terminal.getLexema() == null)
            {
                terminal.setLexema(token.getLexema());
                return terminal;
            }
        }
        return simbolo;
    }
    
    public List<Cuadruplo> analizar(List<Token> tokens)
    {
        List<Error> errores = new ArrayList<>();
        List<Cuadruplo> cuadruplos = new ArrayList<>();
        Stack<Simbolo> pila = new Stack<>();
        
        Temporal temporal = new Temporal();
        
        pila.add(tablaAnalisis.getNoTerminalInicial());
        
        int indice = 0;
        while (!pila.empty())
            if (esTokenAceptadoPorPila(pila, tokens.get(indice)))
            {
                Simbolo simbolo = configurarSimbolo(pila.pop(), tokens.get(indice));
                generarBloque(simbolo);
                generarCuadruplo(simbolo, cuadruplos);
                if (!pila.empty() && pila.peek() instanceof NoTerminal)
                    temporal.setSimbolo(pila.peek());
                indice = (indice + 1) < tokens.size() ? indice + 1 : indice;
            } else
            {
                indice = controlarErrores(tokens, errores, pila, indice, cuadruplos, temporal);
                if (indice == -1)
                    break;
            }
        reducirCuadruplos(cuadruplos);
        fijarBloquesACuadruplos(cuadruplos);
        configurarVariables(cuadruplos);
        imprimirCuadruplos(cuadruplos);
        return cuadruplos;
    }
    
    
    
    
    private boolean esTokenAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }
    
    private void reemplazarSimbolos(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, int indiceRegla, List<Cuadruplo> cuadruplos, Temporal temporal)
    {
        Simbolo simbolo = pila.pop();
        generarBloque(simbolo);
        generarCuadruplo(simbolo, cuadruplos);
        Simbolo[] derivacion = tablaAnalisis.generarReglaProduccion(indiceRegla);
        for (int i = (derivacion.length - 1); i >= 0; i--)
        {
            derivacion[i].setPadre(temporal.getSimbolo());
            pila.add(derivacion[i]);
        }
        if (simbolo.getIdRegla() != pila.peek().getIdRegla() && pila.peek() instanceof NoTerminal)
            temporal.setSimbolo(pila.peek());
        
    }
    
    private String convertirAString(Simbolo[] simbolos)
    {
        String cadena = "";
        for (Simbolo simbolo : simbolos)
            cadena += convertirAString(simbolo);
        return cadena.trim();
    }
    
    private void imprimirPila(Stack<Simbolo> pila)
    {
        pila.stream().forEach((simbolo) ->
        {
            System.out.print((simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getValor()));
        });
        System.out.println();
    }
    
    private void imprimirCuadruplos(List<Cuadruplo> cuadruplos)
    {
        cuadruplos.stream().forEach(
                cuadruplo ->
        {
            System.out.println(
                    (cuadruplo.getBloque() != null ? cuadruplo.getBloque().getNivel() : "")
                    + "\t" + convertirAString(cuadruplo.getResultado())
                    + "\t" + convertirAString(cuadruplo.getOperando1())
                    + "\t" + convertirAString(cuadruplo.getOperacion())
                    + "\t" + convertirAString(cuadruplo.getOperando2())
            );
        }
        );
    }
    
    private String convertirAString(Simbolo simbolo)
    {
        if (simbolo == null)
            return "vacio";
        else if (simbolo instanceof Terminal)
        {
            Terminal terminal = ((Terminal) simbolo);
            return terminal.getLexema() == null ? terminal.getTipoToken().toString() : terminal.getLexema();
        } else
            return ((NoTerminal) simbolo).getValor();
    }
    
}
