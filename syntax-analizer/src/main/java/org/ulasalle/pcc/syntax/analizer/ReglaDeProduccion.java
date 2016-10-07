/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.List;

/**
 *
 * @author francisco
 */
public class ReglaDeProduccion
{

    private NoTerminal noTerminalInicial;
    private List<Simbolo> derivacion;

    public ReglaDeProduccion(NoTerminal noTerminalInicial, List<Simbolo> derivacion)
    {
        this.noTerminalInicial = noTerminalInicial;
        this.derivacion = derivacion;
    }

    public NoTerminal getNoTerminalInicial()
    {
        return noTerminalInicial;
    }

    public List<Simbolo> getDerivacion()
    {
        return derivacion;
    }

}
