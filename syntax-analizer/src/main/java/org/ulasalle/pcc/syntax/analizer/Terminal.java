/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.pcc.syntax.analizer;

import java.util.Objects;

/**
 *
 * @author francisco
 */
public class Terminal extends Token implements Simbolo 
{

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
        return Objects.equals(super.getLexema(), other.getLexema()) || (super.getTipoToken()!=null && Objects.equals(super.getTipoToken(), other.getTipoToken()));
    }

}
