package org.ulasalle.compiler.util;

import java.util.List;
/**
 *
 * @author francisco
 */
public abstract class Respuesta
{

    private String nombreArchivo;
    private List<? extends Resultado> resultados;
    private List<? extends Error> errores;

    public Respuesta(String nombreArchivo, List<? extends Resultado> resultados, List<? extends Error> errores)
    {
        this.nombreArchivo = nombreArchivo;
        this.resultados = resultados;
        this.errores = errores;
    }

    public String getNombreArchivo()
    {
        return nombreArchivo;
    }

    public List<? extends Resultado> getResultados()
    {
        return resultados;
    }

    public List<? extends Error> getErrores()
    {
        return errores;
    }

}
