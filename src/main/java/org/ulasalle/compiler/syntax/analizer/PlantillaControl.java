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
    private Simbolo[] plantilla;
    private boolean estaControlada;

    public PlantillaControl()
    {
    }

    public PlantillaControl(Simbolo[] plantilla, boolean estaControlada)
    {
        this.plantilla = plantilla;
        this.estaControlada = estaControlada;
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
    
    
}
