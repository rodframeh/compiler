package org.ulasalle.lexical.analizer;

public class LexicalError {

    private String description;
    private int positionInFile;//numero de linea
    private int positionInLine;
    private String characters;
    private int positionInCharacters;

    public String getDescription() {
        return description;
    }

    public void setDescription(ReadStatus readStatus) {
        switch (readStatus) {
            case UNRECOGNIZABLE:
                this.description = "[Error][Lexico]: no se reconoce "
                        + "el caracter escrito ['" 
                        + characters.charAt(positionInCharacters) + "']";
                break;
            case STOP:
                this.description = "[Error][Lexico]: no se reconoce "
                        + "la secuencia de caracteres escrita ['" 
                        + characters.charAt(positionInCharacters) + "']";
                break;
        }
    }

    public int getPositionInFile() {
        return positionInFile;
    }

    public void setPositionInFile(int positionInFile) {
        this.positionInFile = positionInFile;
    }

    public int getPositionInLine() {
        return positionInLine;
    }

    public void setPositionInLine(int positionInLine) {
        this.positionInLine = positionInLine;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public int getPositionInCharacters() {
        return positionInCharacters;
    }

    public void setPositionInCharacters(int positionInCharacters) {
        this.positionInCharacters = positionInCharacters;
    }

}
