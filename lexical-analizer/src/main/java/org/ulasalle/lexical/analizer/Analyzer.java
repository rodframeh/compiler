package org.ulasalle.lexical.analizer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Analyzer {

    private String line;
    private final List<Token> tokens= new LinkedList<>();
    private final List<LexicalError> lexicalErrors= new LinkedList<>();

    public List<Token> getTokens() {
        return tokens;
    }

    public List<LexicalError> getLexicalErrors() {
        return lexicalErrors;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<String> start() throws Exception {
        //a eliminar posteriormente
        int positionInFile = 0;//linea en el archivo 
        StringTokenizer stringTokenizer = new StringTokenizer(line);
        //necesarios
        Reader reader = new Reader();
        Stack<String> stack = new Stack<>();
        //añado lexemas a mi stack 
        while (stringTokenizer.hasMoreTokens()) {
            stack.add(stringTokenizer.nextToken());
        }
        //revierto la pila
        Collections.reverse(stack);
        //comienzo a consumir mi stack(leo, luego elimino)
        while (!stack.empty()) {
            try {
                String characters = stack.pop();
                reader.boot();
                reader.setCharacters(characters);
                // recorro mi cadena
                for (int i = 0; i < characters.length(); i++) {
                    reader.next();
                }
                //añade el token a la lista
                if (!reader.getTypeToken().equals(TypeToken.IS_NOT_FINAL)) {
                    Token token = new Token();
                    //si se ha leido todo el token(palabra) por completo
                    if (reader.getReadStatus().equals(ReadStatus.COMPLETED)) {
                        token.setLexema(characters);
                        token.setTypeState(reader.getTypeToken());
                        tokens.add(token);
                    } else {
                        //si una parte del lexema es un token y la otra tambien
                        token.setLexema(characters.substring(0, 
                                reader.getPositionStop()));
                        token.setTypeState(reader.getTypeToken());
                        tokens.add(token);
                        stack.push(characters.substring(
                                reader.getPositionStop(), characters.length()));
                    }
                } else {
                    LexicalError lexicalError = new LexicalError();
                    lexicalError.setPositionInFile(positionInFile);
                    lexicalError.setDescription(reader.getReadStatus());
                    lexicalError.setCharacters(characters);
                    lexicalError.setPositionInCharacters(reader.getPositionStop());
                    lexicalError.setPositionInLine(line.indexOf(characters));
                    lexicalErrors.add(lexicalError);
                }
            } catch (Exception ex) {
                Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //solo para test
    public void show() {
        if(tokens!=null)tokens.stream().forEach((token) -> {
            System.out.println(token.getLexema() + " - " + token.getTypeState());
        });
        if(lexicalErrors!=null)lexicalErrors.stream().forEach((lexicalError) -> {
            System.out.println(lexicalError.getPositionInFile()
                    + " - " + lexicalError.getPositionInLine()
                    + " - " + lexicalError.getPositionInCharacters()
                    + " - " + lexicalError.getCharacters()
                    + " - " + lexicalError.getDescription()
            );
        });
    }
}
