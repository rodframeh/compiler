/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.syntax.analizer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.ulasalle.compiler.util.SimboloDeserializer;

/**
 *
 * @author francisco
 */
@JsonDeserialize(using = SimboloDeserializer.class)
public interface Simbolo
{
    public int getIndiceRegla();
    public void setIndiceRegla(int indiceRegla);
}
