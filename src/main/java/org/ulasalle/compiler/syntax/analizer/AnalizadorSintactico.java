package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.TablaAnalisis;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.ulasalle.compiler.util.Respuesta;
import org.ulasalle.compiler.util.Token;

public class AnalizadorSintactico //implements Analizador
{

    private static TablaAnalisis tablaAnalisis;

    public AnalizadorSintactico()
    {
        if (tablaAnalisis == null)
            tablaAnalisis = new TablaAnalisis();
    }

    private int encontrarFinError(List<Token> tokens, int indiceTokens, Stack<Simbolo> pila, List<Cuadruplo> cuadruplos,Temporal temporal)
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
                            this.reemplazarSimbolos(pila, tablaAnalisis, indiceTokens, cuadruplos,temporal);
                            break;
                        }
                    } else
                        if (((Terminal) pila.peek()).equals(new Terminal(";")))
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

    private void construirCuadruplo(Simbolo simbolo, Simbolo[] plantilla, List<Cuadruplo> cuadruplos,int indiceCuadruplo)
    {
        for (int i = 0; i < plantilla.length; i++)
        {    
            if (plantilla[i] != null)
            {   
                if (simbolo instanceof Terminal && plantilla[i] instanceof Terminal && ((Terminal) plantilla[i]).equals(simbolo))
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
                    break;
                } else{
                    if (simbolo instanceof NoTerminal) 
                    {   
                        if (plantilla[i] instanceof NoTerminal )
                        {
                            if(((NoTerminal) plantilla[i]).equals(simbolo))
                            {
                                Simbolo s=(NoTerminal) simbolo;
                                switch (i)
                                {
                                    case 0:
                                        cuadruplos.get(indiceCuadruplo).setResultado(s);
                                        break;
                                    case 1:
                                        cuadruplos.get(indiceCuadruplo).setOperando1(s);
                                        break;
                                    case 2:
                                        cuadruplos.get(indiceCuadruplo).setOperacion(s);
                                        break;
                                    case 3:
                                        cuadruplos.get(indiceCuadruplo).setOperando2(s);
                                        break;
                                }
                                //System.out.println(i+":(3):"+((NoTerminal) simbolo).getValor()+"::"+((NoTerminal) plantilla[i]).getValor()+"::::"+convertirAString(plantilla));
                                break;
                            }
                            else
                            {
                               // System.out.println(i+":(2):"+((NoTerminal) simbolo).getValor()+"::"+((NoTerminal) plantilla[i]).getValor()+"::::"+convertirAString(plantilla));
                            } 
                        }
                        else
                            {
                               // System.out.println(i+":(1):"+((NoTerminal) simbolo).getValor());
                            } 
                    }
                        
//                    else
//                    {
//                        if(simbolo instanceof  Terminal){
//                            System.out.println(((Terminal) simbolo).getLexema());
//                        }else{
//                            if(i==0)
//                                System.out.println(((NoTerminal) simbolo).getValor()+"::"+((NoTerminal) plantilla[i]).getValor());
//                        }
//                    }
                }
            }    
        }
    }

    private void generarCuadruplo(Simbolo simbolo, List<Cuadruplo> cuadruplos,Temporal temporal)
    {
        int indiceRegla = simbolo.getIndiceRegla();
        Simbolo[] plantilla = tablaAnalisis.getPlantilla(indiceRegla);
        if(temporal.getSimbolo()!=null)
        {
            for(int indiceCuadruplo=0;indiceCuadruplo<cuadruplos.size();indiceCuadruplo++)
            {
                if(temporal.getSimbolo().getPadre()!=null && cuadruplos.get(indiceCuadruplo).getPadre()!=null && cuadruplos.get(indiceCuadruplo).getPadre().equals(temporal.getSimbolo().getPadre()))
                {
              //      System.out.println(convertirAString(temporal.getSimbolo())+"->"+convertirAString(tablaAnalisis.getReglaProduccion(cuadruplos.get(indiceCuadruplo).getIndiceRegla())));
//                    if(temporal.getSimbolo() instanceof  Terminal){
//                        System.out.println(((Terminal) temporal.getSimbolo()).getLexema());
//                    }else{
//                        System.out.println(((NoTerminal) temporal.getSimbolo()).getValor());
//                    }
                    construirCuadruplo(temporal.getSimbolo(), tablaAnalisis.getPlantilla(temporal.getSimbolo().getIndiceRegla()), cuadruplos,indiceCuadruplo);
                }
            }
        }
        
        if(plantilla.length>0)
        {
            if(cuadruplos.isEmpty())
            {
                Cuadruplo cuadruplo=new Cuadruplo();
                cuadruplo.setIdRegla(simbolo.getIdRegla());
                cuadruplo.setIndiceRegla(indiceRegla);
                cuadruplo.setPadre(simbolo.getPadre());
                cuadruplos.add(cuadruplo);
                construirCuadruplo(simbolo, plantilla, cuadruplos,0);
            }
            else
            {
//                if(simbolo instanceof  Terminal){
//                    System.out.println(((Terminal) simbolo).getLexema()+"::"+cuadruplos.get(cuadruplos.size()-1).getIdRegla()+":"+simbolo.getIdRegla()+":::"+cuadruplos.get(cuadruplos.size()-1).getIndiceRegla()+"::"+simbolo.getIndiceRegla()+"::"+convertirSimbolosAString(plantilla));
//                }else{
//                    System.out.println(((NoTerminal) simbolo).getValor()+"::"+cuadruplos.get(cuadruplos.size()-1).getIdRegla()+":"+simbolo.getIdRegla()+":::"+cuadruplos.get(cuadruplos.size()-1).getIndiceRegla()+"::"+simbolo.getIndiceRegla()+"::"+convertirSimbolosAString(plantilla));
//                }
                for(int indiceCuadruplo=0;indiceCuadruplo<cuadruplos.size();indiceCuadruplo++)
                {
                    if(cuadruplos.get(indiceCuadruplo).getIdRegla()==simbolo.getIdRegla())
                    {
                        construirCuadruplo(simbolo, plantilla, cuadruplos,indiceCuadruplo);
                        return;
                    }
                }
                if(cuadruplos.get(cuadruplos.size()-1).getIdRegla() != simbolo.getIdRegla() )
                {
                    Cuadruplo cuadruplo=new Cuadruplo();
                    cuadruplo.setIdRegla(simbolo.getIdRegla());
                    cuadruplo.setIndiceRegla(indiceRegla);
                    cuadruplo.setPadre(simbolo.getPadre());
                    cuadruplos.add(cuadruplo);
                    construirCuadruplo(simbolo, plantilla, cuadruplos,cuadruplos.size()-1);
                }

            }
        }
    }
    


    private int controlarErrores(List<Token> tokens, List<ErrorSintactico> errores, Stack<Simbolo> pila, int indiceTokens, List<Cuadruplo> cuadruplos,Temporal temporal)
    {
        if (pila.peek().getClass() == Terminal.class)//obligatorio para el metodo reeemplazar simbolo
        {
            errores.add(new ErrorSintactico(TipoError.TOKEN_IRRECONOCIBLE, tokens.get(indiceTokens)));
            return encontrarFinError(tokens, indiceTokens, pila, cuadruplos,temporal);// -1 termina
        } else
        {
            int indiceRegla = tablaAnalisis.encontrarIndiceReglaProduccion(new Terminal(tokens.get(indiceTokens)), (NoTerminal) pila.peek());
            if (indiceRegla == -1)
            {
                errores.add(new ErrorSintactico(TipoError.NOTERMINAL_IRRECONOCIBLE, tokens.get(indiceTokens)));
                return encontrarFinError(tokens, indiceTokens, pila, cuadruplos,temporal);// -1 termina
            } else if (pila.isEmpty() && (indiceTokens + 1) < tokens.size())
                errores.add(new ErrorSintactico(TipoError.TOKENS_SIN_LEER, tokens.get(indiceTokens)));
            else
                reemplazarSimbolos(pila, tablaAnalisis, indiceRegla, cuadruplos,temporal);
        }
        return indiceTokens;
    }

    private Simbolo cargarSimbolo(Simbolo simbolo, Token token)
    {
        return ((Terminal) simbolo).getLexema() == null ? new Terminal(token, simbolo.getIndiceRegla()) : simbolo;
    }

    public Respuesta analizar(List<Token> tokens)
    {
        List<ErrorSintactico> errores = new ArrayList<>();
        List<Cuadruplo> cuadruplos = new ArrayList<>();
        Stack<Simbolo> simbolos = new Stack<>();
        
        Temporal temporal=new Temporal();
        
        simbolos.add(tablaAnalisis.getNoTerminalInicial());

        int indice = 0;
        while (!simbolos.empty())
        {
            if (esTokenAceptadoPorPila(simbolos, tokens.get(indice)))
            {
                generarCuadruplo(cargarSimbolo(simbolos.pop(), tokens.get(indice)), cuadruplos,temporal);
                indice = (indice + 1) < tokens.size() ? indice + 1 : indice;
            } else
            {
                indice = controlarErrores(tokens, errores, simbolos, indice, cuadruplos,temporal);
                if (indice == -1)
                    break;
            }
        }
        imprimirCuadruplos(cuadruplos);
        return new RespuestaSintactica("nombreArchivo", new ArrayList<>(), errores);
    }

    private boolean esTokenAceptadoPorPila(Stack<Simbolo> pila, Token token)
    {
        return pila.peek().getClass() == Terminal.class && ((Terminal) pila.peek()).equals(token);
    }

    private void reemplazarSimbolos(Stack<Simbolo> pila, TablaAnalisis tablaAnalisis, int indiceRegla, List<Cuadruplo> cuadruplos,Temporal temporal)
    {
        Simbolo simbolo=pila.pop();
        generarCuadruplo(simbolo, cuadruplos,temporal);
        Simbolo[] derivacion = tablaAnalisis.generarReglaProduccion(indiceRegla);
        if(derivacion.length>0) System.out.print(convertirAString(temporal.getSimbolo())+"->"); 
        for (int i = (derivacion.length - 1); i >= 0; i--)
        {
            System.out.print(convertirAString(derivacion[i]));
            derivacion[i].setPadre(temporal.getSimbolo());
            pila.add(derivacion[i]);
        }
        if(derivacion.length>0) System.out.println();
        System.out.println("SACAR::"+convertirAString(simbolo)+"  PILA::"+convertirAString(pila.peek()));
        if(simbolo.getIdRegla()!=pila.peek().getIdRegla() && pila.peek() instanceof NoTerminal)
        {
            System.out.println("INGRESA::"+convertirAString(simbolo)+"  PILA::"+convertirAString(pila.peek()));
            temporal.setSimbolo(pila.peek());
        } 
    }

    private String convertirAString(Simbolo[] simbolos)
    {
        String cadena="";
        for(Simbolo simbolo:simbolos)
        {
            cadena+=simbolo ==null ?"vacio ":(simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getValor())+" ";
        }
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
        return simbolo instanceof Terminal ? ((Terminal) simbolo).getLexema() : ((NoTerminal) simbolo).getValor();
    }


}
