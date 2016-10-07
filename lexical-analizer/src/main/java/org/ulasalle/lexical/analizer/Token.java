/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.lexical.analizer;

/**
 *
 * @author christianlp
 */
public class Token
{

    private TipoToken tipoToken;
    private String lexema;

    public Token()
    {
    }

    public Token(TipoToken tipoToken, String lexema)
    {
        this.tipoToken = tipoToken;
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

}
