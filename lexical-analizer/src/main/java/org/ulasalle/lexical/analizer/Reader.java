package org.ulasalle.lexical.analizer;

public class Reader {

    private int index;
    private int currentState;
    private int lengthSymbols;
    private String characters;
    private ReadStatus readStatus;
    private final Automata automata= new Automata();
    private TypeToken typeToken;
    private int positionStop;

    public void setCharacters(String characters) {
        this.characters = characters;
        this.lengthSymbols = this.characters.length();
    }

    public void boot() {
        this.index = 0;
        this.currentState = -1;
        this.positionStop=-1;
        this.typeToken = TypeToken.IS_NOT_FINAL;
        this.readStatus=ReadStatus.INIT;
        if (this.automata.getAlphabet() == null) {
            this.automata.build();
        }
    }

    public ReadStatus getReadStatus() {
        return readStatus;
    }

    public TypeToken getTypeToken() {
        return typeToken;
    }

    public int getPositionStop() {
        return positionStop;
    }

    public ReadStatus next() throws Exception {
        if (this.readStatus.equals(ReadStatus.INIT) || this.readStatus.equals(ReadStatus.RUN)) {
            if (this.index < this.lengthSymbols) {
                char symbol = this.characters.charAt(this.index);
                if (this.currentState == -1) {
                    this.currentState = 0;
                }
                //buscar y verifica si existe en el alfabeto
                for (int j = 0; j < this.automata.getLengthTransitions(); j++) {
                    if (this.automata.getAlphabet()[j] == symbol) {
                        
                        //verifica si se puede mover a otro estado
                        if (automata.getTransitionsAccepted()[this.currentState][j]) {
                            currentState = automata.getTransitions()[this.currentState][j];
                            this.typeToken = automata.getFinalState(this.currentState);
                            index++;
                            //verifica llego al final de la cadena
                            if (index == lengthSymbols) {
                                return this.readStatus = ReadStatus.COMPLETED;
                            }
                            return this.readStatus = ReadStatus.RUN;
                        } else {
                            this.positionStop=this.index;
                            return this.readStatus = ReadStatus.STOP;
                        }
                    }
                }
                return this.readStatus = ReadStatus.UNRECOGNIZABLE;
            } else {
                return this.readStatus = ReadStatus.OUT_OF_RANGE;
            }
        }
        return this.readStatus;
    }
}
