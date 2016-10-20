/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.util;

import java.util.List;

/**
 *
 * @author francisco
 */
public interface Analizador
{
    public Respuesta analizar(List<? extends Resultado> resultado);
}
