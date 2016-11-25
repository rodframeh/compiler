package org.ulasalle.compiler.syntax.analizer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Objects;

@JsonDeserialize(as=NoTerminal.class)
public class NoTerminal implements Simbolo
{

    private String valor;
    private int indiceRegla;
    private int idRegla;
    private int idSimbolo;
    private Simbolo padre;
    
    public NoTerminal()
    {
    }
    
    public NoTerminal(String valor)
    {
        this.valor = valor;
    }

    public NoTerminal(String valor, int indiceRegla)
    {
        this.valor = valor;
        this.indiceRegla = indiceRegla;
    }
    
    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
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

    public int getIdRegla()
    {
        return idRegla;
    }

    public void setIdRegla(int idRegla)
    {
        this.idRegla = idRegla;
    }

    public int getIdSimbolo()
    {
        return idSimbolo;
    }

    public void setIdSimbolo(int idSimbolo)
    {
        this.idSimbolo = idSimbolo;
    }

    public Simbolo getPadre()
    {
        return padre;
    }

    public void setPadre(Simbolo padre)
    {
        this.padre = padre;
    }

   
}
