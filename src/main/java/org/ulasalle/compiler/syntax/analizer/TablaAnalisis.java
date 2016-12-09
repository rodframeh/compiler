package org.ulasalle.compiler.syntax.analizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ulasalle.compiler.util.Simbolo;
import org.ulasalle.compiler.util.CargadorPropiedades;

public class TablaAnalisis
{

    private List<Terminal> terminales;
    private List<NoTerminal> noTerminales;
    private int[][] exploradorDeReglas;
    private ReglaProduccion[] reglasProduccion;
    private int contadorReglas;
    private int contadorSimbolos;
    
    public TablaAnalisis()
    {
        llenarReglasDeProduccion();
        generarExploradorDeReglas();
        this.contadorReglas=0;
        this.contadorSimbolos=0;
    }

    private void llenarReglasDeProduccion()
    {
        try
        {
            CargadorPropiedades cp=new CargadorPropiedades();
            reglasProduccion=(ReglaProduccion[]) cp.leerJSON(ReglaProduccion[].class, "reglasproduccion.json");
        } catch (IOException ex)
        {
            Logger.getLogger(TablaAnalisis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Simbolo getNoTerminalInicial()
    {
        return this.reglasProduccion[0].getNoTerminal();
    }

    public int encontrarIndiceReglaProduccion(Terminal terminal, NoTerminal noTerminal)
    {
        for (int j = 0; j < this.noTerminales.size(); j++)
            if (this.noTerminales.get(j).equals(noTerminal))
                for (int i = 0; i < this.terminales.size(); i++)
                    if (this.terminales.get(i).equals(terminal))
                        return this.exploradorDeReglas[j][i];
        return -1;
    }


    private Simbolo[] copiarSimbolos(Simbolo[] simbolos)
    {
        Simbolo[] nuevos=new Simbolo[simbolos.length];
        for(int i=0;i<simbolos.length;i++)
        {
            nuevos[i]=simbolos[i].copiarValor();
        }
        return nuevos;
    }
    

    public Simbolo[] generarReglaProduccion(int indiceDeRegla)
    {
        Simbolo[] simbolos=copiarSimbolos(this.reglasProduccion[indiceDeRegla].getDerivacion());
        
        for(Simbolo simbolo:simbolos)
        {
            simbolo.setIndiceRegla(indiceDeRegla);
            simbolo.setIdRegla(this.contadorReglas);
            simbolo.setIdSimbolo(this.contadorSimbolos);
            this.contadorSimbolos++;
        }
        this.contadorReglas++;
        return simbolos;
    }

    public int getContadorReglasProduccion()
    {
        return contadorReglas;
    }

    
    
    public Simbolo[] getPlantilla(int indiceRegla)
    {
        return reglasProduccion[indiceRegla].getPlantilla();
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
            if (noTerminal.equals(reglaProduccion.getNoTerminal()))
                existeNoTerminal = true;
        if (!existeNoTerminal)
            noTerminales.add(reglaProduccion.getNoTerminal());
    }

    private void generarSimbolos()
    {
        terminales = new LinkedList<>();
        noTerminales = new LinkedList<>();
        for (ReglaProduccion reglaProduccion : reglasProduccion)
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

    private int encontrarRegla(NoTerminal noTerminal, Terminal terminal)
    {
        for (int i = 0; i < reglasProduccion.length; i++)
            if (reglasProduccion[i].getNoTerminal().equals(noTerminal))
                if (reglasProduccion[i].getDerivacion().length > 0)
                {
                    Simbolo simboloDerivado = reglasProduccion[i].getDerivacion()[0];
                    if (simboloDerivado instanceof Terminal && terminal.equals(reglasProduccion[i].getDerivacion()[0]))
                        return i;
                    else if (simboloDerivado instanceof NoTerminal && encontrarRegla((NoTerminal) simboloDerivado, terminal) != -1)
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
                exploradorDeReglas[i][j] = encontrarRegla(noTerminales.get(i), terminales.get(j));
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
    
    private String convertirAString(Simbolo simbolo)
    {
        if (simbolo == null)
            return "vacio";
        else
        {
            if(simbolo instanceof Terminal)
            {
                Terminal terminal=((Terminal) simbolo);
                return terminal.getLexema()==null ? terminal.getTipoToken().toString() : terminal.getLexema();
            }
            else
                return ((NoTerminal) simbolo).getValor();
        }
    }

    private String convertirAString(Simbolo[] simbolos)
    {
        String cadena="";
        for(Simbolo simbolo:simbolos)
        {
            cadena+=convertirAString(simbolo);
        }
        return cadena.trim();
    }
    

}
