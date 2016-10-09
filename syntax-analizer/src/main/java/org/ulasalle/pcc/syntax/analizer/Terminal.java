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

    public Terminal()
    {
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
    
    public String getLexema()
    {
        return lexema;
    }
 
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || (getClass() != obj.getClass())){
            return false;
        }
         System.out.println("lala"+((Terminal) obj).lexema);   
        return Objects.equals(super.lexema, ((Terminal) obj).lexema) || Objects.equals(super.tipoToken, ((Terminal) obj).tipoToken);
    }

}
