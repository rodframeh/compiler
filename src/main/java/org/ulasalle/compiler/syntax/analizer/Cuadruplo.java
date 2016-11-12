/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Resultado;

/**
 *
 * @author francisco
 */
public class Cuadruplo implements Resultado
{
    private int bloque;
    private Simbolo resultado;
    private Simbolo operando1;
    private Simbolo operacion;
    private Simbolo operando2;

    public Cuadruplo()
    {
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
