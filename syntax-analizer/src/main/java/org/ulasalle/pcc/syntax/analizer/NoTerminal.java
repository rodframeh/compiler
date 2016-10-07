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
public class NoTerminal implements Simbolo
{

    private String nombre;

    public NoTerminal(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || (getClass() != obj.getClass()))
            return false;
        return Objects.equals(this.nombre, ((NoTerminal) obj).nombre);
    }

}
