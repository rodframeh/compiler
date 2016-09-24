package org.ulasalle.lexical.analizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Automata {
    

    private Map<Integer, List<List<FuncionTransicion>>> delta;

    private Map<Integer, TipoToken> conjuntoEstadosFinales;

    private int estado;
    
    public Automata() {
        estado=-1;
        this.delta = new HashMap<>();
        this.conjuntoEstadosFinales=new HashMap<>();
        
        List<List<FuncionTransicion>> transitionOf0 = new ArrayList<>();
        transitionOf0.add(getLetters(8));
        transitionOf0.add(getSpecials(8));
        transitionOf0.add(getNumbers(7));
        transitionOf0.add(getDelimiters(5));
        transitionOf0.add(getOperatorsBooleans(4));
        transitionOf0.add(getOperators(3));
        transitionOf0.add(getEqual(4));
        transitionOf0.add(getAsterisk(3));
        transitionOf0.add(getOperatorsBooleanAND(9));
        transitionOf0.add(getOperatorsBooleanOR(6));
        transitionOf0.add(getSlash(13));
        transitionOf0.add(getQuote(1));

        List<List<FuncionTransicion>> transitionOf1 = new ArrayList<>();
        transitionOf1.add(getLetters(1));
        transitionOf1.add(getSpecials(1));
        transitionOf1.add(getNumbers(1));
        transitionOf1.add(getDelimiters(1));
        transitionOf1.add(getOperatorsBooleans(1));
        transitionOf1.add(getEqual(1));
        transitionOf1.add(getOperatorsBooleanOR(1));
        transitionOf1.add(getOperatorsBooleanAND(1));
        transitionOf1.add(getSlash(1));
        transitionOf1.add(getOperators(1));
        transitionOf1.add(getAsterisk(1));
        transitionOf1.add(getQuote(2));
        transitionOf1.add(getSpace(10));

        List<List<FuncionTransicion>> transitionOf4 = new ArrayList<>();
        transitionOf4.add(getEqual(3));

        List<List<FuncionTransicion>> transitionOf6 = new ArrayList<>();
        transitionOf6.add(getOperatorsBooleanOR(3));

        List<List<FuncionTransicion>> transitionOf7 = new ArrayList<>();
        transitionOf7.add(getNumbers(7));

        List<List<FuncionTransicion>> transitionOf8 = new ArrayList<>();
        transitionOf8.add(getLetters(8));
        transitionOf8.add(getNumbers(8));

        List<List<FuncionTransicion>> transitionOf9 = new ArrayList<>();
        transitionOf9.add(getOperatorsBooleanAND(3));

        List<List<FuncionTransicion>> transitionOf10 = new ArrayList<>();
        transitionOf10.add(getLetters(10));
        transitionOf10.add(getSpecials(10));
        transitionOf10.add(getNumbers(10));
        transitionOf10.add(getDelimiters(10));
        transitionOf10.add(getOperatorsBooleans(10));
        transitionOf10.add(getEqual(10));
        transitionOf10.add(getOperatorsBooleanOR(10));
        transitionOf10.add(getOperatorsBooleanAND(10));
        transitionOf10.add(getSlash(10));
        transitionOf10.add(getOperators(10));
        transitionOf10.add(getQuote(10));
        transitionOf10.add(getSpace(10));
        transitionOf10.add(getAsterisk(11));

        List<List<FuncionTransicion>> transitionOf11 = new ArrayList<>();
        transitionOf11.add(getLetters(10));
        transitionOf11.add(getSpecials(10));
        transitionOf11.add(getNumbers(10));
        transitionOf11.add(getDelimiters(10));
        transitionOf11.add(getOperatorsBooleans(10));
        transitionOf11.add(getEqual(10));
        transitionOf11.add(getOperatorsBooleanOR(10));
        transitionOf11.add(getOperatorsBooleanAND(10));
        transitionOf11.add(getOperators(10));
        transitionOf11.add(getQuote(10));
        transitionOf11.add(getSpace(10));
        transitionOf11.add(getSlash(12));

        List<List<FuncionTransicion>> transitionOf13 = new ArrayList<>();
        transitionOf13.add(getSlash(14));

        List<List<FuncionTransicion>> transitionOf14 = new ArrayList<>();
        transitionOf14.add(getLetters(10));
        transitionOf14.add(getSpecials(10));
        transitionOf14.add(getNumbers(10));
        transitionOf14.add(getDelimiters(10));
        transitionOf14.add(getOperatorsBooleans(10));
        transitionOf14.add(getEqual(10));
        transitionOf14.add(getOperatorsBooleanOR(10));
        transitionOf14.add(getOperatorsBooleanAND(10));
        transitionOf14.add(getOperators(10));
        transitionOf14.add(getQuote(10));
        transitionOf14.add(getSpace(10));
		//salto de linea	

        this.delta.put(0, transitionOf0);
        this.delta.put(1, transitionOf1);
        this.delta.put(4, transitionOf4);
        this.delta.put(6, transitionOf6);
        this.delta.put(7, transitionOf7);
        this.delta.put(8, transitionOf8);
        this.delta.put(9, transitionOf9);
        this.delta.put(10, transitionOf10);
        this.delta.put(11, transitionOf11);
        this.delta.put(13, transitionOf13);
        this.delta.put(14, transitionOf14);

        this.conjuntoEstadosFinales.put(5, TipoToken.DELIMITADOR);
        this.conjuntoEstadosFinales.put(2, TipoToken.LITERAL_CADENA);
        this.conjuntoEstadosFinales.put(7, TipoToken.CONST_NUMERICA);
        this.conjuntoEstadosFinales.put(8, TipoToken.IDENTIFICADOR);
        this.conjuntoEstadosFinales.put(4, TipoToken.OPERADOR);
        this.conjuntoEstadosFinales.put(3, TipoToken.OPERADOR);
        this.conjuntoEstadosFinales.put(13, TipoToken.OPERADOR);
        this.conjuntoEstadosFinales.put(15, TipoToken.COMENTARIO);
        this.conjuntoEstadosFinales.put(12, TipoToken.COMENTARIO_MULTILINEA);
    }

    private List<FuncionTransicion> getNumbers(int state) {
        List<FuncionTransicion> transitions_09 = new ArrayList<>();
        transitions_09.add(new FuncionTransicion(getCharacters_09(), state));
        return transitions_09;
    }

    private List<FuncionTransicion> getSpecials(int state) {
        List<FuncionTransicion> transitionsSpecial = new ArrayList<>();
        transitionsSpecial.add(new FuncionTransicion(new char[]{'_', '$'}, state));
        return transitionsSpecial;
    }

    private List<FuncionTransicion> getLetters(int state) {
        List<FuncionTransicion> transitionsletters = new ArrayList<>();
        transitionsletters.add(new FuncionTransicion(getCharacters_AZ(), state));
        transitionsletters.add(new FuncionTransicion(getCharacters_az(), state));
        return transitionsletters;
    }

    private List<FuncionTransicion> getDelimiters(int state) {
        List<FuncionTransicion> transitionsDelimiters = new ArrayList<>();
        transitionsDelimiters.add(new FuncionTransicion(new char[]{'(', ')', '{', '}', ',', ';'}, state));
        return transitionsDelimiters;
    }

    private List<FuncionTransicion> getOperatorsBooleans(int state) {
        List<FuncionTransicion> transitionsOperatorsBooleans = new ArrayList<>();
        transitionsOperatorsBooleans.add(new FuncionTransicion(new char[]{'<', '>', '!'}, state));
        return transitionsOperatorsBooleans;
    }

    private List<FuncionTransicion> getOperators(int state) {
        List<FuncionTransicion> transitionsOperators = new ArrayList<>();
        transitionsOperators.add(new FuncionTransicion(new char[]{'+', '-', '%'}, state));
        return transitionsOperators;
    }

    private List<FuncionTransicion> getOperatorsBooleanAND(int state) {
        List<FuncionTransicion> transitionsOperatorsBooleanAND = new ArrayList<>();
        transitionsOperatorsBooleanAND.add(new FuncionTransicion(new char[]{'&'}, state));
        return transitionsOperatorsBooleanAND;
    }

    private List<FuncionTransicion> getOperatorsBooleanOR(int state) {
        List<FuncionTransicion> transitionsOperatorsBooleanOR = new ArrayList<>();
        transitionsOperatorsBooleanOR.add(new FuncionTransicion(new char[]{'|'}, state));
        return transitionsOperatorsBooleanOR;
    }

    private List<FuncionTransicion> getSlash(int state) {
        List<FuncionTransicion> transitionsSlash = new ArrayList<>();
        transitionsSlash.add(new FuncionTransicion(new char[]{'/'}, state));
        return transitionsSlash;
    }

    private List<FuncionTransicion> getQuote(int state) {
        List<FuncionTransicion> transitionsQuote = new ArrayList<>();
        transitionsQuote.add(new FuncionTransicion(new char[]{'"'}, state));
        return transitionsQuote;
    }

    private List<FuncionTransicion> getAsterisk(int state) {
        List<FuncionTransicion> transitionsAsterisk = new ArrayList<>();
        transitionsAsterisk.add(new FuncionTransicion(new char[]{'*'}, state));
        return transitionsAsterisk;
    }

    private List<FuncionTransicion> getEqual(int state) {
        List<FuncionTransicion> transitionsAsterisk = new ArrayList<>();
        transitionsAsterisk.add(new FuncionTransicion(new char[]{'='}, state));
        return transitionsAsterisk;
    }

    private List<FuncionTransicion> getSpace(int state) {
        List<FuncionTransicion> transitionsSpace = new ArrayList<>();
        transitionsSpace.add(new FuncionTransicion(new char[]{' '}, state));
        return transitionsSpace;
    }

    public void show() {
        for (Map.Entry hash : this.delta.entrySet()) {
            for (List<FuncionTransicion> transitions : (List<List<FuncionTransicion>>) hash.getValue()) {
                for (FuncionTransicion funcionTransicion : transitions) {
                    System.out.print(hash.getKey() + " >> ");
                    for (char character : funcionTransicion.getCaracteresAceptados()) {
                        System.out.print(character + " ");
                    }
                    System.out.println(" >> \t" + funcionTransicion.getEstadoSiguiente());
                }
            }
        }
    }

    private char[] getCharacters_AZ() {
        char[] caracteresAceptados = new char[26];
        int i = 0;
        for (char character = 'A'; character <= 'Z'; character++, i++) {
            caracteresAceptados[i] = character;
        }
        return caracteresAceptados;
    }

    private char[] getCharacters_az() {
        char[] caracteresAceptados = new char[26];
        int i = 0;
        for (char character = 'a'; character <= 'z'; character++, i++) {
            caracteresAceptados[i] = character;
        }
        return caracteresAceptados;
    }

    private char[] getCharacters_09() {

        char[] caracteresAceptados = new char[10];
        int i = 0;
        for (char character = '0'; character <= '9'; character++, i++) {
            caracteresAceptados[i] = character;
        }
        return caracteresAceptados;
    }

    
    public boolean mover(char letra){
        for (Map.Entry hash : this.delta.entrySet()) {
            for (List<FuncionTransicion> transitions : (List<List<FuncionTransicion>>) hash.getValue()) {
                for (FuncionTransicion funcionTransicion : transitions) {
                    for (char character : funcionTransicion.getCaracteresAceptados()) {                        
                        if(letra==character){
                            estado= funcionTransicion.getEstadoSiguiente();
                            return true;
                        }
                    }
                    
                }
            }
        }
        return false;
        
     }
    
    
    public boolean esFinal(){
        for(int i=0;i<conjuntoEstadosFinales.size();i++){
            return this.conjuntoEstadosFinales.get(estado)!=null;
        }
        return false;
    }
     
    
    public TipoToken obtenerTipo(){
        return this.conjuntoEstadosFinales.get(estado);
    }
    
    public void reset(){
        estado=-1;
    }
}
