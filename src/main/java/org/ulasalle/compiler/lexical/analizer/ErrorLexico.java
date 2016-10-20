package org.ulasalle.compiler.lexical.analizer;

import org.ulasalle.compiler.util.Error;
/**
 *
 * @author christianlp
 */
public class ErrorLexico implements Error
{

    private String buffer;
    private char caracter;
    private int linea;

    public ErrorLexico()
    {
    }

    public ErrorLexico(String buffer, char caracter, int linea)
    {
        this.buffer = buffer;
        this.linea = linea;
        this.caracter = caracter;
    }

    public String getBuffer()
    {
        return buffer;
    }

    public int getLinea()
    {
        return linea;
    }

    public char getCaracter()
    {
        return caracter;
    }

    public String getDescripcion()
    {
        String detalles;
        if (caracter == '\n')
            detalles = "en el salto de linea";
        else
            detalles = "con el caracter '" + caracter + "'";

        return "Error lexico en la linea " + linea + " del codigo fuente ["
                + buffer.trim() + "] " + detalles;
    }

}
