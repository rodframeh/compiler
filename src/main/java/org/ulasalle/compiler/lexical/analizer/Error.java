package org.ulasalle.compiler.lexical.analizer;

public class Error
{

    private String buffer;
    private char caracter;
    private int linea;

    public Error()
    {
    }

    public Error(String buffer, char caracter, int linea)
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
