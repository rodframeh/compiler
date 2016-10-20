/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import java.util.List;

/**
 *
 * @author francisco
 */
public class ReglaProduccion
{

    private NoTerminal noTerminalInicial;
    private Simbolo[] derivacion;

    public ReglaProduccion()
    {
    }
    
    public ReglaProduccion(NoTerminal noTerminalInicial, Simbolo[] derivacion)
    {
        this.noTerminalInicial = noTerminalInicial;
        this.derivacion = derivacion;
    }

    public NoTerminal getNoTerminalInicial()
    {
        return noTerminalInicial;
    }

    public Simbolo[] getDerivacion()
    {
        return derivacion;
    }

}
