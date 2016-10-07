/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.lexical.analizer;

/**
 *
 * @author francisco
 */
public class TransicionDTO
{

    private int estadoActual;
    private int estadoSiguiente;
    private char[] caracteresAceptados;

    public TransicionDTO()
    {
    }

    public TransicionDTO(int estadoActual, int estadoSiguiente, char[] caracteresAceptados)
    {
        this.estadoActual = estadoActual;
        this.estadoSiguiente = estadoSiguiente;
        this.caracteresAceptados = caracteresAceptados;
    }

    public int getEstadoActual()
    {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActual)
    {
        this.estadoActual = estadoActual;
    }

    public int getEstadoSiguiente()
    {
        return estadoSiguiente;
    }

    public void setEstadoSiguiente(int estadoSiguiente)
    {
        this.estadoSiguiente = estadoSiguiente;
    }

    public char[] getCaracteresAceptados()
    {
        return caracteresAceptados;
    }

    public void setCaracteresAceptados(char[] caracteresAceptados)
    {
        this.caracteresAceptados = caracteresAceptados;
    }

}
