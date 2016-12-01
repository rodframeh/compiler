package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Simbolo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.ulasalle.compiler.util.Respuesta;
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

    private void reducirCuadruplos(List<Cuadruplo> cuadruplos)
    {
        Map<Integer,Integer> identificadores=new HashMap<>();
        for(int i=0;i<cuadruplos.size();i++)
        {
            if(cuadruplos.get(i).getOperando2() instanceof NoTerminal )
            {
                int identificador=cuadruplos.get(i).getOperando2().getIdSimbolo();
                int acumulado=(identificadores.get(identificador)==null?1:identificadores.get(identificador)+1);
                identificadores.put(identificador, acumulado);
            }
            if(cuadruplos.get(i).getResultado()instanceof NoTerminal)
            {
                int identificador=cuadruplos.get(i).getResultado().getIdSimbolo();
                int acumulado=(identificadores.get(identificador)==null?1:identificadores.get(identificador)+1);
                identificadores.put(identificador, acumulado);
            }
        }
        cuadruplos.forEach((c) ->identificadores.keySet().stream().filter((i) -> (c.getOperando2() instanceof NoTerminal && c.getOperando2().getIdSimbolo()==i && identificadores.get(i)==1)).forEachOrdered((_item) ->c.setOperando2(null)));
        
        
        
        
        List<Integer> eliminados=new ArrayList<>();
        
        List<Cuadruplo> copia=new ArrayList<>(cuadruplos.size());
        Collections.copy(cuadruplos, copia);
        
        for(int i=0;i<copia.size();i++)
        {
            if(copia.get(i).getOperacion()==null && copia.get(i).getOperando2()==null)
            {
                for(int j=0;j<cuadruplos.size();j++)
                {
                    if(cuadruplos.get(j).getOperando2()!=null && cuadruplos.get(j).getOperando2().getIdSimbolo()==copia.get(i).getResultado().getIdSimbolo())
                    {
                        cuadruplos.get(j).setOperando2(copia.get(i).getResultado());
                       eliminados.add(i);
                    }else if(cuadruplos.get(j).getOperando1()!=null && cuadruplos.get(j).getOperando1().getIdSimbolo()==copia.get(i).getResultado().getIdSimbolo())
                    {
                        cuadruplos.get(j).setOperando1(copia.get(i).getResultado());
                        eliminados.add(i);
                    } else if(cuadruplos.get(j).getOperacion()!=null && cuadruplos.get(j).getOperacion().getIdSimbolo()==copia.get(i).getResultado().getIdSimbolo())
                    {
                        cuadruplos.get(j).setOperacion(copia.get(i).getResultado());
                        eliminados.add(i);
                    } 
                }
            }
        }
        
        for(int i:eliminados)
        {
            cuadruplos.remove(i);
        }
        
        
        
    }
    
    private int controlarErrores(List<Token> tokens, List<ErrorSintactico> errores, Stack<Simbolo> pila, int indiceTokens, List<Cuadruplo> cuadruplos, Temporal temporal)
    {
        if (pila.peek().getClass() == Terminal.class)//obligatorio para el metodo reeemplazar simbolo
        {
            errores.add(new ErrorSintactico(TipoError.TOKEN_IRRECONOCIBLE, tokens.get(indiceTokens)));
            return encontrarFinError(tokens, indiceTokens, pila, cuadruplos, temporal);// -1 termina
        } else
        {
            int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(tokens.get(indiceTokens)), (NoTerminal) pila.peek());
            if (indiceRegla == -1)
            {
                errores.add(new ErrorSintactico(TipoError.NOTERMINAL_IRRECONOCIBLE, tokens.get(indiceTokens)));
                return encontrarFinError(tokens, indiceTokens, pila, cuadruplos, temporal);// -1 termina
            } else if (pila.isEmpty() && (indiceTokens + 1) < tokens.size())
            {
                errores.add(new ErrorSintactico(TipoError.TOKENS_SIN_LEER, tokens.get(indiceTokens)));
            }
            else
                reemplazarSimbolos(pila, tablaAnalisis, indiceRegla, cuadruplos, temporal);
        }
        return indiceTokens;
    }

    private Simbolo mezclar(Simbolo simbolo, Token token)
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

    public Respuesta analizar(List<Token> tokens)
    {
        List<ErrorSintactico> errores = new ArrayList<>();
        List<Cuadruplo> cuadruplos = new ArrayList<>();
        Stack<Simbolo> pila = new Stack<>();

        Temporal temporal = new Temporal();

        pila.add(tablaAnalisis.getNoTerminalInicial());

        int indice = 0;
        while (!pila.empty())
            if (esTokenAceptadoPorPila(pila, tokens.get(indice)))
            {
                generarCuadruplo(mezclar(pila.pop(), tokens.get(indice)), cuadruplos);
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
        imprimirCuadruplos(cuadruplos);
        return new RespuestaSintactica("nombreArchivo", new ArrayList<>(), errores);
    }
    
    private boolean esTokenAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }

    private void reemplazarSimbolos(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, int indiceRegla, List<Cuadruplo> cuadruplos, Temporal temporal)
    {
        Simbolo simbolo = pila.pop();
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
                    cuadruplo.getBloque()
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
