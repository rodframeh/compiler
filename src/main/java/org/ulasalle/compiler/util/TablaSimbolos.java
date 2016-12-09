package org.ulasalle.compiler.util;

public class TablaSimbolos 
{
    private int offset;// Posicion en la pila
    private int tipo;//Entero = 1
    private int lineaDeclaracion;
    private int lineaPrimeraAsignacion;
    private String lexema;
    
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getLineaDeclaracion() {
        return lineaDeclaracion;
    }

    public void setLineaDeclaracion(int lineaDeclaracion) {
        this.lineaDeclaracion = lineaDeclaracion;
    }

    public int getLineaPrimeraAsignacion() {
        return lineaPrimeraAsignacion;
    }

    public void setLineaPrimeraAsignacion(int lineaPrimeraAsignacion) {
        this.lineaPrimeraAsignacion = lineaPrimeraAsignacion;
    }
    
    public TablaSimbolos(String lexema, int lineaDeclaracion)
    {
        this.lexema = lexema;
        this.lineaDeclaracion = lineaDeclaracion;
    }
    
    public String getLexema()
    {
        return lexema;
    }
}
