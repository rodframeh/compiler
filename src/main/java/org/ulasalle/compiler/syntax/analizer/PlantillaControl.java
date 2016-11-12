/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

/**
 *
 * @author francisco
 */
public class PlantillaControl
{
    private int indiceRegla;
    private Simbolo[] plantilla;
    private boolean estaControlada;
    private int bloque;

    public PlantillaControl()
    {
    }

    public PlantillaControl(int indiceRegla, Simbolo[] plantilla, boolean estaControlada, int bloque) {
        this.indiceRegla = indiceRegla;
        this.plantilla = plantilla;
        this.estaControlada = estaControlada;
        this.bloque = bloque;
    }



    public Simbolo[] getPlantilla()
    {
        return plantilla;
    }

    public void setPlantilla(Simbolo[] plantilla)
    {
        this.plantilla = plantilla;
    }

    public boolean isEstaControlada()
    {
        return estaControlada;
    }

    public void setEstaControlada(boolean estaControlada)
    {
        this.estaControlada = estaControlada;
    }

    public int getIndiceRegla()
    {
        return indiceRegla;
    }

    public void setIndiceRegla(int indiceRegla)
    {
        this.indiceRegla = indiceRegla;
    }

    public int getBloque() {
        return bloque;
    }

    public void setBloque(int bloque) {
        this.bloque = bloque;
    }
    
    
    
}
