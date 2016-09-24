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
public class ErrorLexico {
    private String buffer;
    private int linea;

    public ErrorLexico() {
    }

    public ErrorLexico(String buffer, int linea) {
        this.buffer = buffer;
        this.linea = linea;
    }
    
    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
    
    
}
