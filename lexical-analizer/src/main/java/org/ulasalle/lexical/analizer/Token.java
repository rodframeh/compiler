package org.ulasalle.lexical.analizer;


public class Token {
    
    private TypeToken typeState;
    private String lexema;

    public TypeToken getTypeState() {
        return typeState;
    }

    public void setTypeState(TypeToken typeState) {
        this.typeState = typeState;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    
    
}
