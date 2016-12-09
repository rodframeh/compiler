package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.TablaSimbolos;
import java.util.List;
import java.util.ArrayList;

public class Bloque
{
    private List<TablaSimbolos> variables;
    private int nivel;
    
    Bloque padre;

    public Bloque()
    {
        variables = new ArrayList<>();
        nivel=0;
    }
    
    public Bloque( Bloque padre )
    {
        variables = new ArrayList<>();
        this.padre = padre;
        nivel=padre.getNivel()+1;
    }

    public int getNivel()
    {
        return nivel;
    }
    
    
    
    public boolean fueDecladara( String lexema )
    {
        if(variables.stream().filter(v->v.getLexema().equals(lexema)).count()>0)
            return true;
        if(padre == null)
            return false;
        return padre.fueDecladara(lexema);
    }
    
    public void declarar(String lexema, int lineaDeclaracion)
    {
        TablaSimbolos nuevaEntrada = new TablaSimbolos(lexema, lineaDeclaracion);
        variables.add(nuevaEntrada);
    }
}
