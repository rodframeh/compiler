/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;


import org.ulasalle.compiler.util.Simbolo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Objects;
import org.ulasalle.compiler.util.TipoToken;
import org.ulasalle.compiler.util.Token;

/**
 *
 * @author francisco
 */
@JsonDeserialize(as= Terminal.class)
public class Terminal extends Token implements Simbolo 
{
    private int indiceRegla;
    private int idRegla;
    private int idSimbolo;
    private Simbolo padre;
    private Bloque contexto;
    
    public Terminal()
    {
    }
    
    public Terminal(Token token)
    {
        super(token);
    }
    
    public Terminal(TipoToken tipoToken)
    {
        super(tipoToken);
    }

    public Terminal(String lexema)
    {
        super(lexema);
    }

    public Terminal(TipoToken tipoToken, String lexema)
    {
        super(tipoToken, lexema);
    }

    public Bloque getContexto()
    {
        return contexto;
    }

    public void setContexto(Bloque contexto)
    {
        this.contexto = contexto;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null )
        {
            return false;
        }
        if(!(obj instanceof Terminal))
        {
            return super.equals(obj);
        }
        final Terminal other=(Terminal) obj;
        return (super.getLexema()!=null 
                && Objects.equals(super.getLexema(), other.getLexema())) 
                ||
                (super.getTipoToken()!=null
                && Objects.equals(super.getTipoToken(), other.getTipoToken()));
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
        Terminal terminal=new Terminal();
        terminal.idRegla=this.idRegla;
        terminal.idSimbolo=this.idSimbolo;
        terminal.indiceRegla=this.indiceRegla;
        terminal.padre=this.padre;
        terminal.setLexema(this.getLexema());
        terminal.setTipoToken(this.getTipoToken());
        return terminal;
    }
    
}
