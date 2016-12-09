package org.ulasalle.compiler.util;

import java.util.Objects;

public class Token 
{

    private TipoToken tipoToken;
    private String lexema;
    private int linea;
    
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
    
    public Token(TipoToken tipoToken, String lexema,int linea)
    {
        this.tipoToken = tipoToken;
        this.lexema = lexema;
        this.linea=linea;
    }

    public Token(String lexema)
    {
        this.lexema = lexema;
    }

    public int getLinea()
    {
        return linea;
    }

    public void setLinea(int linea)
    {
        this.linea = linea;
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
