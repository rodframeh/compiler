/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class TablaAnalisis
{

    private List<Terminal> terminales;
    private List<NoTerminal> noTerminales;
    private int[][] exploradorDeReglas;
    private List<ReglaProduccion> reglasDeProduccion;

    private void llenarReglasDeProduccion()
    {
        reglasDeProduccion = new ArrayList<>();
        List<Simbolo> derivacion = new ArrayList<>();

        derivacion.add(new NoTerminal("<SD>"));
        derivacion.add(new NoTerminal("<DC>"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<DC>"), derivacion));

        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<DC>"), new ArrayList<Simbolo>()));

        derivacion = new ArrayList<>();
        derivacion.add(new NoTerminal("<TD>"));
        derivacion.add(new NoTerminal("<VA>"));
        derivacion.add(new NoTerminal("<RD*>"));
        derivacion.add(new Terminal(";"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<SD>"), derivacion));

        derivacion = new ArrayList<>();
        derivacion.add(new Terminal(","));
        derivacion.add(new NoTerminal("<VA>"));
        derivacion.add(new NoTerminal("<RD*>"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<RD*>"), derivacion));

        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<RD*>"), new ArrayList<Simbolo>()));

        derivacion = new ArrayList<>();
        derivacion.add(new Terminal(TipoToken.IDENTIFICADOR));
        derivacion.add(new NoTerminal("<A>"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<VA>"), derivacion));

        derivacion = new ArrayList<>();
        derivacion.add(new Terminal("="));
        derivacion.add(new NoTerminal("<EM>"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<A>"), derivacion));

        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<A>"), new ArrayList<Simbolo>()));

        derivacion = new ArrayList<>();
        derivacion.add(new Terminal("int"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<TD>"), derivacion));

        derivacion = new ArrayList<>();
        derivacion.add(new Terminal(TipoToken.CONST_NUMERICA));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<EM>"), derivacion));

    }

    public TablaAnalisis()
    {
        llenarReglasDeProduccion();
        generarExploradorDeReglas();
    }

    public Simbolo getNoTerminalBase()
    {
        return reglasDeProduccion.get(0).getNoTerminalInicial();
    }

    public int encontrarIndiceReglaProduccion(Terminal terminal, NoTerminal noTerminal)
    {
        for (int i = 0; i < terminales.size(); i++) {
            if (terminales.get(i).equals(terminal)) {
                for (int j = 0; j < noTerminales.size(); j++) {
                    if (noTerminales.get(j).equals(noTerminal)) {
                        return exploradorDeReglas[j][i];
                    }
                }
            }
        }
        return -1;
    }

    public List<Simbolo> getReglaDeProduccion(int indiceDeRegla)
    {
        return reglasDeProduccion.get(indiceDeRegla).getDerivacion();

    }

    private void generarSimbolos()
    {
        terminales=new LinkedList<>();
        noTerminales=new LinkedList<>();
          for (ReglaProduccion reglaProduccion : reglasDeProduccion) {
            boolean existeNoTerminal = false;
            for (NoTerminal noTerminal : noTerminales) {
                if (noTerminal.equals(reglaProduccion.getNoTerminalInicial())) {
                    existeNoTerminal = true;
                }
            }
            if (!existeNoTerminal) {
                noTerminales.add(reglaProduccion.getNoTerminalInicial());
            }
            for (Simbolo simbolo : reglaProduccion.getDerivacion()) {
                if (simbolo instanceof Terminal) {
                    boolean existeTerminal = false;
                    for (Terminal terminal : terminales) {
                        if (terminal.equals(simbolo)) {
                            existeTerminal = true;
                        }
                    }
                    if (!existeTerminal) {
                        terminales.add((Terminal) simbolo);
                    }
                }
            }
        }
    }
    
    private void imprimirSimbolos()
    {
        System.out.println("Terminales:");
        Arrays.stream(terminales.toArray()).forEach(t -> System.out.println((((Terminal) t).getLexema()) == null?((Terminal) t).getTipoToken():((Terminal) t).getLexema()));
        System.out.println("No terminales:");
        Arrays.stream(noTerminales.toArray()).forEach(t -> System.out.println(((NoTerminal) t).getNombre()));

    }
    
    private void generarMatriz()
    {
        exploradorDeReglas = new int[noTerminales.size()][terminales.size()];
        for (int i = 0; i < noTerminales.size(); i++) {
            for (int j = 0; j < terminales.size(); j++) {
                exploradorDeReglas[i][j] = -1;
            }
        }
    }
    
    private void imprimirMatriz()
    {
        System.out.println("Matriz vacia:");
        for (int i = 0; i < noTerminales.size(); i++) {
            for (int j = 0; j < terminales.size(); j++) {
                System.out.print(exploradorDeReglas[i][j] + " ");
            }
            System.out.println();
        }
    }
 
  
    
    private int encontrarReglaDeProduccion(NoTerminal noTerminal,Terminal terminal)
    {
        for(int i=0;i<reglasDeProduccion.size();i++)
        {
            if(reglasDeProduccion.get(i).getNoTerminalInicial().equals(noTerminal))
            {
                if(reglasDeProduccion.get(i).getDerivacion().size()>0)
                {
                    Simbolo simboloDerivado= reglasDeProduccion.get(i).getDerivacion().get(0);
                    if(simboloDerivado instanceof Terminal)
                    {
                        if(terminal.equals(reglasDeProduccion.get(i).getDerivacion().get(0)))
                        {
                            return i;
                        } 
                    }
                    else if(simboloDerivado instanceof NoTerminal)
                    {
                        NoTerminal noTerminalDerivado=(NoTerminal)simboloDerivado;
                        if (encontrarReglaDeProduccion(noTerminalDerivado,terminal)!=-1)
                        {
                            return i;
                        }
                    }
                }
                else
                {
                    return i;
                }    
            }
        }
        return -1;
    }
    
    private void generarExploradorDeReglas()
    {
        generarSimbolos();
        imprimirSimbolos();
        generarMatriz();
        imprimirMatriz();

        for (int i = 0; i < noTerminales.size(); i++) {
            for (int j = 0; j < terminales.size(); j++) {
                exploradorDeReglas[i][j]=encontrarReglaDeProduccion(noTerminales.get(i), terminales.get(j));
            }
        }

        System.out.println("Matriz llena:");

        for (int i = 0; i < noTerminales.size(); i++) {
            for (int j = 0; j < terminales.size(); j++) {
                System.out.print(exploradorDeReglas[i][j] + " ");
            }
            System.out.println();
        }

    }

}
