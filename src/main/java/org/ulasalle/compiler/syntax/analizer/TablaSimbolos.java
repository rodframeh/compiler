package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.EntradaTablaSimbolos;
import java.util.List;
import java.util.ArrayList;

public class TablaSimbolos
{
    private List<EntradaTablaSimbolos> variables;
    
    TablaSimbolos padre;
    
    public TablaSimbolos( TablaSimbolos padre )
    {
        variables = new ArrayList<>();
        this.padre = padre;
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
        EntradaTablaSimbolos nuevaEntrada = new EntradaTablaSimbolos(lexema, lineaDeclaracion);
        variables.add(nuevaEntrada);
    }
}
