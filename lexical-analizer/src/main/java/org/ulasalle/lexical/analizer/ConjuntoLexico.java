package org.ulasalle.lexical.analizer;

import java.util.List;

/**
 *
 * @author francisco
 */
public class ConjuntoLexico
{

    private String nombreArchivo;
    private List<Token> tokens;
    private List<ErrorLexico> erroresLexicos;

    public ConjuntoLexico(String nombreArchivo, List<Token> tokens, List<ErrorLexico> erroresLexicos)
    {
        this.nombreArchivo = nombreArchivo;
        this.tokens = tokens;
        this.erroresLexicos = erroresLexicos;
    }

    public String getNombreArchivo()
    {
        return nombreArchivo;
    }

    public List<Token> getTokens()
    {
        return tokens;
    }

    public List<ErrorLexico> getErroresLexicos()
    {
        return erroresLexicos;
    }

}
