package org.ulasalle.compiler.syntax.analizer;

/**
 *
 * @author francisco
 */
public class ReglaProduccion
{

    private NoTerminal noTerminal;
    private Simbolo[] derivacion;

    public ReglaProduccion()
    {
    }
    
    public ReglaProduccion(NoTerminal noTerminal, Simbolo[] derivacion)
    {
        this.noTerminal = noTerminal;
        this.derivacion = derivacion;
    }

    public NoTerminal getNoTerminal()
    {
        return noTerminal;
    }

    public Simbolo[] getDerivacion()
    {
        return derivacion;
    }

}
