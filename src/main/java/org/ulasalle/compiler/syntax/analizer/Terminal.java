/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;


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

}
