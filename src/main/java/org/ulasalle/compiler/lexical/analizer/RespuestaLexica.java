/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.lexical.analizer;

import java.util.List;
import org.ulasalle.compiler.util.Respuesta;
import org.ulasalle.compiler.util.Token;

/**
 *
 * @author francisco
 */
public class RespuestaLexica extends Respuesta
{
    
    public RespuestaLexica(String nombreArchivo, List<Token> resultados, List<ErrorLexico> errores)
    {
        super(nombreArchivo, resultados, errores);
    }
    
    
    
}
