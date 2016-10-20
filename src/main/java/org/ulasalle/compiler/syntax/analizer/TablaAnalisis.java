/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ulasalle.compiler.util.CargadorPropiedades;

/**
 *
 * @author francisco
 */
public class TablaAnalisis
{

    private List<Terminal> terminales;
    private List<NoTerminal> noTerminales;
    private int[][] exploradorDeReglas;
    private ReglaProduccion[] reglasDeProduccion;

    public TablaAnalisis()
    {
        llenarReglasDeProduccion();
        generarExploradorDeReglas();
    }

    private void llenarReglasDeProduccion()
    {
        try
        {
            CargadorPropiedades cp=new CargadorPropiedades();
            reglasDeProduccion=(ReglaProduccion[]) cp.leerJSON(ReglaProduccion[].class, "reglasproduccion.json");
        } catch (IOException ex)
        {
            Logger.getLogger(TablaAnalisis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Simbolo getNoTerminalBase()
    {
        return reglasDeProduccion[0].getNoTerminalInicial();
    }

    public int encontrarIndiceReglaProduccion(Terminal terminal, NoTerminal noTerminal)
    {
        for (int j = 0; j < noTerminales.size(); j++)
            if (noTerminales.get(j).equals(noTerminal))
                for (int i = 0; i < terminales.size(); i++)
                    if (terminales.get(i).equals(terminal))
                        return exploradorDeReglas[j][i];
        return -1;
    }

    public Simbolo[] getReglaDeProduccion(int indiceDeRegla)
    {
        return reglasDeProduccion[indiceDeRegla].getDerivacion();

    }

    private void agregarTerminales(ReglaProduccion reglaProduccion)
    {
        for (Simbolo simbolo : reglaProduccion.getDerivacion())
            if (simbolo instanceof Terminal)
            {
                boolean existeTerminal = false;
                for (Terminal terminal : terminales)
                    if (terminal.equals(simbolo))
                        existeTerminal = true;
                if (!existeTerminal)
                    terminales.add((Terminal) simbolo);
            }
    }

    private void agregarNoTerminales(ReglaProduccion reglaProduccion)
    {
        boolean existeNoTerminal = false;
        for (NoTerminal noTerminal : noTerminales)
            if (noTerminal.equals(reglaProduccion.getNoTerminalInicial()))
                existeNoTerminal = true;
        if (!existeNoTerminal)
            noTerminales.add(reglaProduccion.getNoTerminalInicial());
    }

    private void generarSimbolos()
    {
        terminales = new LinkedList<>();
        noTerminales = new LinkedList<>();
        for (ReglaProduccion reglaProduccion : reglasDeProduccion)
        {
            agregarNoTerminales(reglaProduccion);
            agregarTerminales(reglaProduccion);
        }
    }

    private void generarMatriz()
    {
        exploradorDeReglas = new int[noTerminales.size()][terminales.size()];
        for (int i = 0; i < noTerminales.size(); i++)
            for (int j = 0; j < terminales.size(); j++)
                exploradorDeReglas[i][j] = -1;
    }

    private int encontrarReglaDeProduccion(NoTerminal noTerminal, Terminal terminal)
    {
        for (int i = 0; i < reglasDeProduccion.length; i++)
            if (reglasDeProduccion[i].getNoTerminalInicial().equals(noTerminal))
                if (reglasDeProduccion[i].getDerivacion().length > 0)
                {
                    Simbolo simboloDerivado = reglasDeProduccion[i].getDerivacion()[0];
                    if (simboloDerivado instanceof Terminal && terminal.equals(reglasDeProduccion[i].getDerivacion()[0]))
                        return i;
                    else if (simboloDerivado instanceof NoTerminal && encontrarReglaDeProduccion((NoTerminal) simboloDerivado, terminal) != -1)
                        return i;
                } else
                    return i;
        return -1;
    }

    private void generarExploradorDeReglas()
    {
        generarSimbolos();
        generarMatriz();
        for (int i = 0; i < noTerminales.size(); i++)
            for (int j = 0; j < terminales.size(); j++)
                exploradorDeReglas[i][j] = encontrarReglaDeProduccion(noTerminales.get(i), terminales.get(j));
    }

    private void imprimirSimbolos()
    {
        System.out.println("Terminales:");
        Arrays.stream(terminales.toArray()).forEach(t -> System.out.println((((Terminal) t).getLexema()) == null ? ((Terminal) t).getTipoToken() : ((Terminal) t).getLexema()));
        System.out.println("No terminales:");
        Arrays.stream(noTerminales.toArray()).forEach(t -> System.out.println(((NoTerminal) t).getValor()));
    }

    private void imprimirMatriz()
    {
        for (int i = 0; i < noTerminales.size(); i++)
        {
            for (int j = 0; j < terminales.size(); j++)
                System.out.print(exploradorDeReglas[i][j] + " ");
            System.out.println();
        }
    }

}
