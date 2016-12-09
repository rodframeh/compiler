package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Simbolo;
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
    private boolean bloque;
    
    public NoTerminal()
    {
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public boolean isBloque()
    {
        return bloque;
    }

    public void setBloque(boolean bloque)
    {
        this.bloque = bloque;
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

    @Override
    public Simbolo copiarValor()
    {
        NoTerminal noTerminal=new NoTerminal();
        noTerminal.idRegla=this.idRegla;
        noTerminal.idSimbolo=this.idSimbolo;
        noTerminal.indiceRegla=this.indiceRegla;
        noTerminal.padre=this.padre;
        noTerminal.valor=this.valor;
        noTerminal.bloque=this.bloque;
        return noTerminal;
    }

   
}
