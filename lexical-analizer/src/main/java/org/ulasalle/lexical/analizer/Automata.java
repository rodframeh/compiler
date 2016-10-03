package org.ulasalle.lexical.analizer;

import java.util.HashMap;
import java.util.Map;

public class Automata {

    private FuncionTransicion[][] transicionses;
    private Map<Integer,TipoToken> estadosFinales;
    private int estadoActual;

    public Automata() {
        this.estadoActual = 0;
        llenarTransiciones();
        llenarEstadosFinales();
    }
    
    private void llenarTransiciones(){
        this.transicionses = new FuncionTransicion[16][16];
    }
    
    private void llenarEstadosFinales(){
        this.estadosFinales=new HashMap<>();
    }

    public boolean mover(char letra) {
        for (int j = 0; j < transicionses.length; j++) {
            if (transicionses[this.estadoActual][j] != null) {
                if (transicionses[this.estadoActual][j].aceptar(letra)) {
                    this.estadoActual=j;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean esFinal() {
        for (int i = 0; i < estadosFinales.size(); i++) {
            return this.estadosFinales.get(this.estadoActual) != null;
        }
        return false;
    }

    public TipoToken obtenerTipo() {
        return this.estadosFinales.get(this.estadoActual);
    }

    public void reset() {
        this.estadoActual = 0;
    }
}
