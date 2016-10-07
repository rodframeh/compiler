package org.ulasalle.lexical.analizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Automata
{

    private static FuncionTransicion[][] transiciones;
    private static Map<Integer, TipoToken> estadosFinales;
    private int estadoActual;

    public Automata()
    {
        this.estadoActual = 0;
        if (transiciones == null)
            llenarTransiciones();
        if (estadosFinales == null)
            llenarEstadosFinales();

    }

    private void llenarEstadosFinales()
    {
        this.estadosFinales = new HashMap<>();
        this.estadosFinales.put(2, TipoToken.LITERAL_CADENA);
        this.estadosFinales.put(3, TipoToken.OPERADOR);
        this.estadosFinales.put(4, TipoToken.OPERADOR);
        this.estadosFinales.put(5, TipoToken.DELIMITADOR);
        this.estadosFinales.put(7, TipoToken.CONST_NUMERICA);
        this.estadosFinales.put(8, TipoToken.IDENTIFICADOR);
        this.estadosFinales.put(13, TipoToken.OPERADOR);
        this.estadosFinales.put(12, TipoToken.COMENTARIO_MULTILINEA);
        this.estadosFinales.put(15, TipoToken.COMENTARIO);
    }

    private void llenarTransiciones()
    {
        this.transiciones = new FuncionTransicion[16][16];
        try
        {
            TransicionDTO[] transicionDTOs = (TransicionDTO[]) new CargadorPropiedades().leerJSON(TransicionDTO[].class, "automata.json");
            Arrays.stream(transicionDTOs).forEach(t -> transiciones[t.getEstadoActual()][t.getEstadoSiguiente()] = new FuncionTransicion(t.getCaracteresAceptados()));
        } catch (IOException ex)
        {
            Logger.getLogger(Automata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir()
    {
        for (int i = 0; i < this.transiciones.length; i++)
            for (int j = 0; j < this.transiciones.length; j++)
                if (this.transiciones[i][j] != null)
                {
                    System.out.print("[" + i + "][" + j + "]");
                    for (int k = 0; k < this.transiciones[i][j].getCaracteresAceptados().length; k++)
                        System.out.print(this.transiciones[i][j].getCaracteresAceptados()[k] + " ");
                    System.out.println();
                }
    }

    public boolean mover(char letra)
    {
        for (int j = 0; j < transiciones.length; j++)
            if (transiciones[this.estadoActual][j] != null)
                if (transiciones[this.estadoActual][j].aceptar(letra))
                {
                    this.estadoActual = j;
                    return true;
                }
        return false;
    }

    public boolean esFinal()
    {
        for (int i = 0; i < estadosFinales.size(); i++)
            return this.estadosFinales.get(this.estadoActual) != null;
        return false;
    }

    public TipoToken obtenerTipo()
    {
        return this.estadosFinales.get(this.estadoActual);
    }

    public void reiniciar()
    {
        this.estadoActual = 0;
    }

}
