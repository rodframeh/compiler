package org.ulasalle.lexical.analizer;

public class Automata {

    private char[] alphabet;
    private boolean[][] transitionsAccepted;
    private int[][] transitions;
    private final int lengthStates = 9;
    private final int lengthTransitions = 82;

    public void build() {
        loadAlphabet();
        //reservamos memoria
        this.transitions = new int[lengthStates][lengthTransitions];
        this.transitionsAccepted = new boolean[lengthStates][lengthTransitions];
        //cargamos transiciones
        prepareTransitions();
        loadTransitions();
    }

    public TypeToken getTypeFinalState(int state) {
        switch (state) {
            case 2:
                return TypeToken.STRING_LITERAL;
            case 3:
            case 4:
                return TypeToken.OPERATOR;
            case 8:
                return TypeToken.IDENTIFIER;
            case 7:
                return TypeToken.CONSTANT_INTEGER;
            case 5:
                return TypeToken.DELIMITER;
            default:
                return TypeToken.IS_NOT_FINAL;
        }
    }

    private void prepareTransitions() {
        //iniciamos las matrices en -1 (transitions) y en false (transitionsAccepted)
        for (int i = 0; i < lengthStates; i++) {
            for (int j = 0; j < lengthTransitions; j++) {
                transitions[i][j] = -1;
                transitionsAccepted[i][j] = false;
            }
        }
    }

    public void showTransitions() {
        System.out.print("\t");
        for (int i = 0; i < lengthTransitions; i++) {
            System.out.print(alphabet[i] + "\t");
        }
        for (int i = 0; i < lengthStates; i++) {
            System.out.print("\n" + i + "\t");
            for (int j = 0; j < lengthTransitions; j++) {
                System.out.print(transitions[i][j] + "\t");
            }
        }
        System.out.print("\n");
    }

    private void loadTransitions() {
        // agregamos comillas apertura                                     q0-q1
        transitions[0][81] = 1;
        transitionsAccepted[0][81] = true;
        // agregamos todo excepto comillas                                 q1-q1
        for (int i = 0; i < 81; i++) {
            transitions[1][i] = 1;
            transitionsAccepted[1][i] = true;
        }
        // agregamos comillas cierre                                       q1-q2
        transitions[1][81] = 2;
        transitionsAccepted[1][81] = true;
        // agregamos operadores aritmeticos                                q0-q3
        for (int i = 62; i < 67; i++) {
            transitions[0][i] = 3;
            transitionsAccepted[0][i] = true;
        }
        // agregamos operadores logicos y asignacion                       q0-q4
        for (int i = 67; i < 71; i++) {
            transitions[0][i] = 4;
            transitionsAccepted[0][i] = true;
        }
        //                                                                 q4-q3
        transitions[4][70] = 3;
        transitionsAccepted[4][70] = true;
        // agregamos operadores de coneccion logica                        q0-q6
        for (int i = 71; i < 73; i++) {
            transitions[0][i] = 6;
            transitionsAccepted[0][i] = true;
        }
        //                                                                 q6-q3
        for (int i = 71; i < 73; i++) {
            transitions[6][i] = 3;
            transitionsAccepted[6][i] = true;
        }
        // agregamos identificadores                                       q0-q8
        for (int i = 0; i < 52; i++) {
            transitions[0][i] = 8;
            transitionsAccepted[0][i] = true;
        }
        //                                                                 q0-q8
        for (int i = 79; i < 81; i++) {
            transitions[0][i] = 8;
            transitionsAccepted[0][i] = true;
        }
        //                                                                 q8-q8
        for (int i = 0; i < 62; i++) {
            transitions[8][i] = 8;
            transitionsAccepted[8][i] = true;
        }
        // agregamos numeros                                               q0-q7
        for (int i = 52; i < 62; i++) {
            transitions[0][i] = 7;
            transitionsAccepted[0][i] = true;
        }
        //                                                                 q7-q7
        for (int i = 52; i < 62; i++) {
            transitions[7][i] = 7;
            transitionsAccepted[7][i] = true;
        }
        // agregamos delimitadores                                        q0-q5
        for (int i = 73; i < 79; i++) {
            transitions[0][i] = 5;
            transitionsAccepted[0][i] = true;
        }
    }

    private void loadAlphabet() {
        this.alphabet = new char[]{
            // letras                                          52           0-51
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            // numeros                                         10          52-61
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //operadores aritmeticos                            5          62-66
            '-', '+', '*', '/', '%',
            //operadores logicos y asignacion                   4          67-70
            '<', '>', '!', '=',
            //operadores coneccion logica                       2          71-72
            '&', '|',
            //delimitadores                                     6          73-78
            ',', ';', '{', '}', '(', ')',
            //especiales                                        3          79-81
            '_', '$', '"'
        };
    }

    public int getLengthStates() {
        return lengthStates;
    }

    public int getLengthTransitions() {
        return lengthTransitions;
    }

    public char[] getAlphabet() {
        return alphabet;
    }

    public boolean[][] getTransitionsAccepted() {
        return transitionsAccepted;
    }

    public int[][] getTransitions() {
        return transitions;
    }

}
