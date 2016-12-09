package org.ulasalle.compiler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ulasalle.compiler.lexical.analizer.AnalizadorLexico;
import org.ulasalle.compiler.syntax.analizer.AnalizadorSintactico;
import org.ulasalle.compiler.syntax.analizer.Cuadruplo;
import org.ulasalle.compiler.util.Token;

public class Aplicacion
{

    public static void main(String[] args)
    {
        try
        {
            AnalizadorLexico lexico = new AnalizadorLexico();
            List<Token> tokens = lexico.analizar("examples/Ejemplo01.programa");
            AnalizadorSintactico sintactico = new AnalizadorSintactico();
            List<Cuadruplo> cuadruplos = sintactico.analizar(tokens);

        } catch (IOException ex)
        {
            Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
