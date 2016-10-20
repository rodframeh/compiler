package org.ulasalle.compiler.lexical.analizer;

import org.ulasalle.compiler.util.Respuesta;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.ulasalle.compiler.util.TipoToken;
import org.ulasalle.compiler.util.Token;

/**
 *
 * @author christianlp
 */
public class AnalizadorLexico //implements Analizador
{

    private Automata automata;
    private List<String> palabrasReservadas;

    public AnalizadorLexico()
    {
        automata = new Automata();
        llenarPalabrasReservadas();
    }

    private void llenarPalabrasReservadas()
    {
        palabrasReservadas = new ArrayList<>();
        palabrasReservadas.add("Programa");
        palabrasReservadas.add("entero");
        palabrasReservadas.add("Si");
        palabrasReservadas.add("OsiNo");
        palabrasReservadas.add("Mientras");
        palabrasReservadas.add("LeerTeclado");
        palabrasReservadas.add("EscribirPantalla");
    }

    private String leerArchivo(String rutaArchivo) throws IOException
    {
        StringBuilder sourceCode = new StringBuilder();
        try (Stream<String> sourceFileStream = Files.lines((new File(rutaArchivo)).toPath()))
        {
            sourceFileStream.forEach(lineStream -> sourceCode.append(lineStream).append("\n"));
        }
        return sourceCode.toString().trim();
    }

    private void agregarToken(StringBuilder buffer, List<Token> tokens)
    {
        String lexema = buffer.toString();
        buffer.delete(0, buffer.length());
        TipoToken tipoToken = automata.obtenerTipo();
        automata.reiniciar();
        if (tipoToken != TipoToken.COMENTARIO && tipoToken != TipoToken.COMENTARIO_MULTILINEA)
        {
            if (tipoToken == TipoToken.IDENTIFICADOR && palabrasReservadas.stream().filter(p -> p.equals(lexema)).count() > 0)
                tipoToken = TipoToken.PALABRA_RESERVADA;
            tokens.add(new Token(tipoToken, lexema));

        }
    }

    private boolean esBlanco(char caracter)
    {
        return caracter == ' ' || caracter == '\t' || caracter == '\n';
    }

    public Respuesta analizar(String rutaArchivo) throws IOException
    {
        List<Token> tokens = new ArrayList<>();
        List<ErrorLexico> errores = new ArrayList<>();
        String codigoFuente = leerArchivo(rutaArchivo);
        StringBuilder buffer = new StringBuilder();
        int linea = 1;
        for (int index = 0; index < codigoFuente.length();)
        {
            char caracter = codigoFuente.charAt(index);
            if (automata.mover(caracter))
            {
                buffer.append(caracter);
                if (index++ == codigoFuente.length() - 1)
                    agregarToken(buffer, tokens);
            } else if (automata.esFinal())
                agregarToken(buffer, tokens);
            else if (esBlanco(caracter))
                do
                {
                    if (caracter == '\n')
                        linea++;
                    caracter = codigoFuente.charAt(++index);
                } while (esBlanco(caracter));
            else
            {
                errores.add(new ErrorLexico(buffer + "" + caracter, caracter, linea));
                index++;
            }
        }
        return new RespuestaLexica((new File(rutaArchivo)).getName(), tokens, errores);
    }

}
