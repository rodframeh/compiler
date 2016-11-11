/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Objects;

/**
 *
 * @author francisco
 */
@JsonDeserialize(as=NoTerminal.class)
public class NoTerminal implements Simbolo
{

    private String valor;
    private int indiceRegla;
    
    public NoTerminal()
    {
    }
    
    public NoTerminal(String valor)
    {
        this.valor = valor;
    }

    public String getValor()
    {
        return valor;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || (getClass() != obj.getClass()))
            return false;
        return Objects.equals(this.valor, ((NoTerminal) obj).valor);
    }

    @Override
    public int getIndiceRegla()
    {
        return this.indiceRegla;
    }
    
    @Override
    public void setIndiceRegla(int indiceRegla)
    {
        this.indiceRegla=indiceRegla;
    }

}
