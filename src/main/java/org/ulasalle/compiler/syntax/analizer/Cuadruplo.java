package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Simbolo;
import org.ulasalle.compiler.util.Resultado;

public class Cuadruplo implements Resultado
{
    private TablaSimbolos contexto;
    private int bloque;
    private int idRegla;
    private int indiceRegla;

    private Simbolo resultado;
    private Simbolo operando1;
    private Simbolo operacion;
    private Simbolo operando2;

    public Cuadruplo()
    {
    }
    
    public TablaSimbolos getTablaSimbolos()
    {
        return contexto;
    }
        
    public int getIndiceRegla()
    {
        return indiceRegla;
    }

    public void setIndiceRegla(int indiceRegla)
    {
        this.indiceRegla = indiceRegla;
    }

    public int getIdRegla()
    {
        return idRegla;
    }

    public void setIdRegla(int idRegla)
    {
        this.idRegla = idRegla;
    }
    
    public int getBloque()
    {
        return bloque;
    }

    public void setBloque(int bloque)
    {
        this.bloque = bloque;
    }

    public Simbolo getResultado()
    {
        return resultado;
    }

    public void setResultado(Simbolo resultado)
    {
        this.resultado = resultado;
    }

    public Simbolo getOperando1()
    {
        return operando1;
    }

    public void setOperando1(Simbolo operando1)
    {
        this.operando1 = operando1;
    }

    public Simbolo getOperacion()
    {
        return operacion;
    }

    public void setOperacion(Simbolo operacion)
    {
        this.operacion = operacion;
    }

    public Simbolo getOperando2()
    {
        return operando2;
    }

    public void setOperando2(Simbolo operando2)
    {
        this.operando2 = operando2;
    }
    
    
}
