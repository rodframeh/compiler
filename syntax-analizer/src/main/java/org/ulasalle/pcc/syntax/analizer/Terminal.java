/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.Objects;

/**
 *
 * @author francisco
 */
public class Terminal implements Simbolo
{

    private String valor;

    public Terminal(String valor)
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
        return Objects.equals(this.valor, ((Terminal) obj).valor);
    }

}
