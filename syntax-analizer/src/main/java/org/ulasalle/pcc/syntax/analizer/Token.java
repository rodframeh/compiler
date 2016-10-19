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
public class Token
{

    private TipoToken tipoToken;
    private String lexema;

    public Token()
    {
    }

    public Token(Token token)
    {
        this.lexema=token.lexema;
        this.tipoToken=token.tipoToken;
    }
    
    public Token(TipoToken tipoToken)
    {
        this.tipoToken = tipoToken;
    }
    
    public Token(TipoToken tipoToken, String lexema)
    {
        this.tipoToken = tipoToken;
        this.lexema = lexema;
    }

    public Token(String lexema)
    {
        this.lexema = lexema;
    }
    
    public TipoToken getTipoToken()
    {
        return tipoToken;
    }

    public void setTipoToken(TipoToken tipoToken)
    {
        this.tipoToken = tipoToken;
    }

    public String getLexema()
    {
        return lexema;
    }

    public void setLexema(String lexema)
    {
        this.lexema = lexema;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if ( obj == null || (getClass() != obj.getClass() && !(obj instanceof Token)))
        {
            return false;
        }
        final Token other = (Token) obj;
        return (this.lexema!=null && Objects.equals(getLexema(), other.getLexema())) || (this.tipoToken!=null && Objects.equals(getTipoToken(), other.getTipoToken()));
    }
    
}
