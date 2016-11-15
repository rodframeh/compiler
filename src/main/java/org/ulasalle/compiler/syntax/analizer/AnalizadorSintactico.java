/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import java.lang.reflect.Field;
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

    private int encontrarFinError(List<Token> tokens,int indiceTokens, Stack<Simbolo> pila,List<Cuadruplo> cuadruplos,Stack<PlantillaControl> plantillas)
    {
        for(;indiceTokens<tokens.size();indiceTokens++)
        {
            if(tokens.get(indiceTokens).getLexema().equals(";") )
            {
                while(!pila.isEmpty()){
                    if(pila.peek() instanceof NoTerminal)
                    {
                        int indiceReglaProduccion=tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(";"),(NoTerminal) pila.peek());
                        if(indiceReglaProduccion==-1)
                        {
                            pila.pop();
                        }
                        else
                        {
                            this.reemplazarSimbolos(pila, tablaAnalisis, indiceTokens,cuadruplos,plantillas);
                            break;
                        }
                    }
                    else
                    {
                        if(((Terminal)pila.peek()).equals(new Terminal(";")))
                        {
                            pila.pop();
                            break;
                        }
                        else
                        {
                            pila.pop();
                        }
                    }
                }
                return ++indiceTokens;
            }
            else
                indiceTokens++;
        }
        return indiceTokens;
    }
    
    private int controlarErrores(List<Token> tokens,List<ErrorSintactico> errores,Stack<Simbolo> pila,int indiceTokens,List<Cuadruplo> cuadruplos,Stack<PlantillaControl> plantillas)
    {
        TipoError tipo = derivarToken(pila, tablaAnalisis, tokens.get(indiceTokens),cuadruplos,plantillas);
        if (tipo == TipoError.TOKEN_IRRECONOCIBLE)
        {
            errores.add(new ErrorSintactico(tipo, tokens.get(indiceTokens)));
            if ((indiceTokens + 1) < tokens.size())
            {
                indiceTokens=encontrarFinError(tokens, indiceTokens,pila,cuadruplos,plantillas);
                if((indiceTokens + 1) > tokens.size()) return -1;
                return indiceTokens;
            }
            else
                return -1;
        } else if (tipo == TipoError.NOTERMINAL_IRRECONOCIBLE)
        {
            errores.add(new ErrorSintactico(tipo, tokens.get(indiceTokens)));
            pila.pop();
        } else if (pila.isEmpty() && (indiceTokens + 1) < tokens.size())
        {
            tipo = TipoError.TOKENS_SIN_LEER;
            errores.add(new ErrorSintactico(tipo, tokens.get(indiceTokens)));
        }
        return indiceTokens;
    }

    private void setCuadruplo(Simbolo simbolo,List<Cuadruplo> cuadruplos,Stack<PlantillaControl> plantillasControl)
    {
        int indiceRegla=simbolo.getIndiceRegla();
        Simbolo[] plantilla=tablaAnalisis.getPlantilla(indiceRegla);
        
        if(simbolo instanceof NoTerminal){
            System.out.println(((NoTerminal) simbolo).getValor());
        }
        
        if(plantillasControl.empty())
        {
            if(plantilla!=null && plantilla.length>0){
                plantillasControl.add(new PlantillaControl(indiceRegla, plantilla,false,0));
                cuadruplos.add(new Cuadruplo());
            }
        }
        else 
        {
            if(plantillasControl.peek().getIndiceRegla()!=simbolo.getIndiceRegla())
            {
                if(plantilla!=null && plantilla.length>0){
                    plantillasControl.add(new PlantillaControl(indiceRegla, plantilla,false,0));
                    cuadruplos.add(new Cuadruplo());
                }
            }
            else
            {
                for(int i=0;i<plantilla.length;i++)
                {
                    if(plantilla[i]!=null)
                    {
                        if(plantilla[i].getClass() == Terminal.class && ((Terminal) plantilla[i]).equals(simbolo))
                        {
                                switch(i)
                                {
                                    case 0:
                                        cuadruplos.get(cuadruplos.size()-1).setResultado(simbolo);
                                        break;
                                    case 1:
                                        cuadruplos.get(cuadruplos.size()-1).setOperando1(simbolo);
                                        break;
                                    case 2:
                                        cuadruplos.get(cuadruplos.size()-1).setOperacion(simbolo);
                                        break;
                                    case 3:
                                        cuadruplos.get(cuadruplos.size()-1).setOperando2(simbolo);
                                        break;
                                }
                                plantilla[i]=null;
                                break;
                        }
                        else if(plantilla[i].getClass() == NoTerminal.class && ((NoTerminal) plantilla[i]).equals(simbolo))
                        {
                                switch(i)
                                {
                                    case 0:
                                        cuadruplos.get(cuadruplos.size()-1).setResultado(simbolo);
                                        break;
                                    case 1:
                                        cuadruplos.get(cuadruplos.size()-1).setOperando1(simbolo);
                                        break;
                                    case 2:
                                        cuadruplos.get(cuadruplos.size()-1).setOperacion(simbolo);
                                        break;
                                    case 3:
                                        cuadruplos.get(cuadruplos.size()-1).setOperando2(simbolo);
                                        break;
                                }
                                plantilla[i]=null;
                                break;
                        }
                        
                    }   
                }
            }
        }
        //plantillas.peek().
    }
    
    public Respuesta analizar(List<Token> tokens)
    {
        List<ErrorSintactico> errores = new ArrayList<>();
        List<Cuadruplo> cuadruplos=new ArrayList<>();
        Stack<Simbolo> pila = new Stack<>();
        Stack<PlantillaControl> plantillas =new Stack<>();
        pila.add(tablaAnalisis.getNoTerminalBase());
        int indiceTokens = 0;
        while (!pila.empty())
            if (esAceptadoPorPila(pila, tokens.get(indiceTokens)))
            {
                setCuadruplo(pila.pop(),cuadruplos,plantillas);
                if ((indiceTokens + 1) < tokens.size())
                    indiceTokens++;
            } else
            {
                indiceTokens=controlarErrores(tokens, errores, pila, indiceTokens,cuadruplos,plantillas);
                if(indiceTokens==-1) break;
            }
        if (pila.empty())
            System.out.println("Esta vacia");
        imprimirCuadruplos(cuadruplos);
        return new RespuestaSintactica("nombreArchivo", new ArrayList<>(), errores);
    }

    private boolean esAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }

    private TipoError derivarToken(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, Token token,List<Cuadruplo> cuadruplos,Stack<PlantillaControl> plantillas)
    {
        if (pila.peek().getClass() == Terminal.class){
            System.out.println("entro!!!");
            return TipoError.TOKEN_IRRECONOCIBLE;
        }
        int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(token), (NoTerminal) pila.peek());
        if (indiceRegla == -1)
            return TipoError.NOTERMINAL_IRRECONOCIBLE;
        else
            reemplazarSimbolos(pila, tablaAnalisis, indiceRegla,cuadruplos,plantillas);
        return TipoError.SIN_ERRORES;
    }

    private void reemplazarSimbolos(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, int indiceRegla,List<Cuadruplo> cuadruplos,Stack<PlantillaControl> plantillas)
    {
        if (pila.peek().getClass() == NoTerminal.class)//sacarlo a un nivel superior
        {
            setCuadruplo(pila.pop(), cuadruplos,plantillas);
            Simbolo[] simbolos = tablaAnalisis.getReglaDeProduccion(indiceRegla);
            for (int i = (simbolos.length - 1); i >= 0; i--)
                pila.add(simbolos[i]);
        }
    }

    private void imprimirPila(Stack<Simbolo> pila)
    {
        pila.stream().forEach((simbolo) -> {
            System.out.print((simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getValor()));
        });
        System.out.println();
    }

    private void imprimirCuadruplos(List<Cuadruplo> cuadruplos)
    {
        cuadruplos.stream().forEach(
                cuadruplo-> {
                    System.out.println(
                            cuadruplo.getBloque()
                                    +"\t"+getContenido(cuadruplo.getResultado())
                                    +"\t"+getContenido(cuadruplo.getOperando1())
                                    +"\t"+getContenido(cuadruplo.getOperacion())
                                    +"\t"+getContenido(cuadruplo.getOperando2())
                    );
                }
        );
    }
    
    private String getContenido(Simbolo simbolo)
    {   
        if(simbolo==null) return "vacio";
        return simbolo instanceof Terminal?((Terminal) simbolo).getLexema():((NoTerminal) simbolo).getValor();
    }
    
}
