/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.ArrayList;
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
        derivacion.add(new Terminal(TipoToken.IDENTIFICADOR,"&IDENTIFICADOR&"));
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
        derivacion.add(new Terminal(TipoToken.CONST_NUMERICA,"&CONSTANTE_NUMERICA&"));
        reglasDeProduccion.add(new ReglaProduccion(new NoTerminal("<EM>"), derivacion));

    }

    private void llenarExploradorDeReglas()
    {
        exploradorDeReglas = new int[][]
        {
            {
                0, 1, 1, 1, 1, 1
            },
            {
                2, -1, -1, -1, -1, -1
            },
            {
                8, -1, -1, -1, -1, -1
            },
            {
                -1, -1, -1, -1, 5, -1
            },
            {
                4, 4, 3, 4, 4, 4
            },
            {
                7, 6, 7, 7, 7, 7
            },
            {
                -1, -1, -1, -1, -1, 9
            }
        };
    }

    private void llenarListaDeNoTerminales()
    {
        noTerminales = new ArrayList<>();
        noTerminales.add(new NoTerminal("<DC>"));
        noTerminales.add(new NoTerminal("<SD>"));
        noTerminales.add(new NoTerminal("<TD>"));
        noTerminales.add(new NoTerminal("<VA>"));
        noTerminales.add(new NoTerminal("<RD*>"));
        noTerminales.add(new NoTerminal("<A>"));
        noTerminales.add(new NoTerminal("<EM>"));
    }

    private void llenarListaDeTerminales()
    {
        terminales = new ArrayList<>();
        terminales.add(new Terminal("int"));
        terminales.add(new Terminal("="));
        terminales.add(new Terminal(","));
        terminales.add(new Terminal(";"));
        terminales.add(new Terminal(TipoToken.IDENTIFICADOR));
        terminales.add(new Terminal(TipoToken.CONST_NUMERICA));
    }

    public TablaAnalisis()
    {
        llenarListaDeTerminales();
        llenarListaDeNoTerminales();
        llenarExploradorDeReglas();
        llenarReglasDeProduccion();
    }

    public Simbolo getNoTerminalBase()
    {
        return reglasDeProduccion.get(0).getNoTerminalInicial();
    }

    public int encontrarIndiceReglaProduccion(Terminal terminal, NoTerminal noTerminal)
    {
        for (int i = 0; i < terminales.size(); i++){
            if (terminales.get(i).equals(terminal))
                for (int j = 0; j < noTerminales.size(); j++)
                    if (noTerminales.get(j).equals(noTerminal))
                        return exploradorDeReglas[j][i];
        }
        return -1;
    }

    public List<Simbolo> getReglaDeProduccion(int indiceDeRegla)
    {
        return reglasDeProduccion.get(indiceDeRegla).getDerivacion();

    }

}
