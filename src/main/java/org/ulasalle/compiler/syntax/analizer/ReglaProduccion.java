/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Simbolo;

/**
 *
 * @author francisco
 */
public class ReglaProduccion
{

    private NoTerminal noTerminal;
    private Simbolo[] derivacion;
    private Simbolo[] plantilla;
    private boolean nuevoBloque;
    
    public ReglaProduccion()
    {
    }
    
    public ReglaProduccion(NoTerminal noTerminal, Simbolo[] derivacion, Simbolo[] plantilla)
    {
        this.noTerminal = noTerminal;
        this.derivacion = derivacion;
        this.plantilla=plantilla;
    }

    public NoTerminal getNoTerminal()
    {
        return noTerminal;
    }

    public Simbolo[] getDerivacion()
    {
        return derivacion;
    }

    public Simbolo[] getPlantilla()
    {
        return plantilla;
    }

    public boolean isNuevoBloque() {
        return nuevoBloque;
    }

    public void setNuevoBloque(boolean nuevoBloque) {
        this.nuevoBloque = nuevoBloque;
    }
    
}
