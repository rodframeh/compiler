package org.ulasalle.lexical.analizer;

import java.util.HashMap;
import java.util.Map;

public class Automata {

    private FuncionTransicion[][] transiciones;
    private Map<Integer, TipoToken> estadosFinales;
    private int estadoActual;

    public Automata() {
        this.estadoActual = 0;
        llenarTransiciones();
        llenarEstadosFinales();
    }

    
 
    
    private void llenarTransiciones() {
        this.transiciones = new FuncionTransicion[16][16];
        transiciones[0][8] = new FuncionTransicion(
                unirCaracteres(
                        new char[]{'$', '_'},
                        getCaracteres_Letras()
                )
        );

        this.transiciones[0][7] = new FuncionTransicion(getDigitos());
        this.transiciones[0][5] = new FuncionTransicion(
                new char[]{'(', ')', '{', '}', ',', ';'});
        this.transiciones[0][4] = new FuncionTransicion(
                new char[]{'<', '>', '!', '='});
        this.transiciones[0][3] = new FuncionTransicion(
                new char[]{'+', '-', '%', '*'});
        this.transiciones[0][9] = new FuncionTransicion(
                new char[]{'&'});
        this.transiciones[0][6] = new FuncionTransicion(
                new char[]{'|'});
        this.transiciones[0][13] = new FuncionTransicion(
                new char[]{'/'});
        this.transiciones[0][1] = new FuncionTransicion(
                new char[]{'\"'});
        this.transiciones[1][1] = new FuncionTransicion(
                unirCaracteres(
                        new char[]{'*'}, getCaracteres_LiteralesCadena())
        );
        this.transiciones[1][2] = this.transiciones[0][1];
        this.transiciones[4][3] = new FuncionTransicion(new char[]{'='});
        this.transiciones[6][3] = new FuncionTransicion(new char[]{'|'});
        this.transiciones[7][7] = this.transiciones[0][7];
        this.transiciones[8][8] = new FuncionTransicion(getCaracteres_Alfanumerico());
        this.transiciones[9][3] = new FuncionTransicion(new char[]{'&'});
        this.transiciones[10][10] = new FuncionTransicion(getCaracteres_LiteralesCadena());
        this.transiciones[10][11] = new FuncionTransicion(new char[]{'*'});
        this.transiciones[11][12] = this.transiciones[0][13];
        this.transiciones[11][10] = new FuncionTransicion(getCaracteres_LiteralesCadena());
        this.transiciones[13][14] = this.transiciones[0][13];
        this.transiciones[13][10] = this.transiciones[10][11];
        this.transiciones[14][14] = this.transiciones[11][10];
        this.transiciones[14][15] = new FuncionTransicion(new char[]{'\n'});
    }

    public void imprimir() {
        for (int i = 0; i < this.transiciones.length; i++) {
            for (int j = 0; j < this.transiciones.length; j++) {
                if(this.transiciones[i][j]!=null){
                    System.out.print("["+i + "]["+j + "]");
                    for(int k=0;k<this.transiciones[i][j].getCaracteresAceptados().length;k++)
                        System.out.print( this.transiciones[i][j].getCaracteresAceptados()[k]+ " ");
                    System.out.println();
                }
            }

        }

    }

    private char[] getCaracteres_Letras() {
        return unirCaracteres(
                getCaracteres_AZ(), getCaracteres_az()
        );
    }

    private char[] getCaracteres_Alfanumerico() {
        return unirCaracteres(
                getCaracteres_Letras(),
                getDigitos()
        );
    }

    private char[] getCaracteres_LiteralesCadena() {
        return unirCaracteres(
                getCaracteres_Alfanumerico(),
                new char[]{'{', '}', '|', ' ', '_', '<', '>', '=', ';', '/', '-', '\'', '+', '(', ')', '&', '%', '$', '!'});
    }

    private char[] unirCaracteres(char[] primarios, char[] secundarios) {
        int tamanio = primarios.length + secundarios.length;
        char[] caracteres = new char[tamanio];
        for (int i = 0; i < tamanio; i++) {
            if (i < primarios.length) {
                caracteres[i] = primarios[i];
            } else {
                caracteres[i] = secundarios[i - primarios.length];
            }
        }
        return caracteres;
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

    private char[] getDigitos() {

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
                    this.estadoActual = j;
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
    
    private void llenarEstadosFinales() {
        this.estadosFinales = new HashMap<>();
        this.estadosFinales.put(2, TipoToken.LITERAL_CADENA);
        this.estadosFinales.put(3, TipoToken.OPERADOR);
        this.estadosFinales.put(4, TipoToken.OPERADOR);
        this.estadosFinales.put(5, TipoToken.DELIMITADOR);
        this.estadosFinales.put(7, TipoToken.CONST_NUMERICA);
        this.estadosFinales.put(8, TipoToken.IDENTIFICADOR);
        this.estadosFinales.put(13, TipoToken.OPERADOR);
        this.estadosFinales.put(12, TipoToken.COMENTARIO_MULTILINEA);
        this.estadosFinales.put(15,TipoToken.COMENTARIO);
    }
}
