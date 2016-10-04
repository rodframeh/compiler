package org.ulasalle.lexical.analizer;

import java.util.HashMap;
import java.util.Map;

public class Automata {

    private FuncionTransicion[][] transiciones;
    private Map<Integer,TipoToken> estadosFinales;
    private int estadoActual;

    public Automata() {
        this.estadoActual = 0;
        llenarTransiciones();
        llenarEstadosFinales();
    }
    
    private void llenarTransiciones(){
        this.transiciones = new FuncionTransicion[16][16];
        transiciones[0][8]=new FuncionTransicion(
                unirCaracteres(
                        new char[]{'$','_'},
                        unirCaracteres(
                                getCaracteres_AZ(), getCaracteres_az()
                        )
                )
        );
        
        this.transiciones[0][7] = new FuncionTransicion(getCaracteres_09());
        this.transiciones[0][5] = new FuncionTransicion(
                new char[]{'(', ')', '{', '}' ,',', ';'});
        this.transiciones[0][4] = new FuncionTransicion(
                new char[]{'<','>','!'});
        this.transiciones[0][3] = new FuncionTransicion(
                new char[]{'+','-','%'});
        this.transiciones[0][9] = new FuncionTransicion(
                new char[]{'&'});
        this.transiciones[0][6] = new FuncionTransicion(
                new char[]{'|'});
        this.transiciones[0][13] = new FuncionTransicion(
                new char[]{'/'});
        this.transiciones[0][1] = new FuncionTransicion(
                new char[]{'\"'});

    }
    
    private char[] unirCaracteres(char[] primarios,char[] secundarios){
        int tamanio=primarios.length+secundarios.length;
        char[] caracteres=new char[tamanio];
        for(int i=0;i<tamanio;i++){
            if(i<primarios.length)caracteres[i]=primarios[i];
            else caracteres[i]=secundarios[i-primarios.length];
        }
        return caracteres;
    }
    
    private void llenarEstadosFinales(){
        this.estadosFinales=new HashMap<>();
    }

    private char[] getCaracteres_AZ() {
        char[] caracteresAceptados = new char[26];
        int i = 0;
        for (char character = 'A'; character <= 'Z'; character++, i++) {
            caracteresAceptados[i] = character;
        }
        return caracteresAceptados;
    }

    private char[] getCaracteres_az() {
        char[] caracteresAceptados = new char[26];
        int i = 0;
        for (char character = 'a'; character <= 'z'; character++, i++) {
            caracteresAceptados[i] = character;
        }
        return caracteresAceptados;
    }

    private char[] getCaracteres_09() {

        char[] caracteresAceptados = new char[10];
        int i = 0;
        for (char character = '0'; character <= '9'; character++, i++) {
            caracteresAceptados[i] = character;
        }
        return caracteresAceptados;
    }
    
    public boolean mover(char letra) {
        for (int j = 0; j < transiciones.length; j++) {
            if (transiciones[this.estadoActual][j] != null) {
                if (transiciones[this.estadoActual][j].aceptar(letra)) {
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
