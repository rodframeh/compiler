package org.ulasalle.compiler.lexical.analizer;

public class FuncionTransicion
{

    private char[] caracteresAceptados;

    public FuncionTransicion(char[] caracteresAceptados)
    {
        this.caracteresAceptados = caracteresAceptados;

    }

    public char[] getCaracteresAceptados()
    {
        return caracteresAceptados;
    }

    public boolean aceptar(char letra)
    {
        for (int i = 0; i < caracteresAceptados.length; i++)
            if (letra == caracteresAceptados[i])
                return true;
        return false;
    }

}
