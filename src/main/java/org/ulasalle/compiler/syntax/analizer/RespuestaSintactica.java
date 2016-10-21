package org.ulasalle.compiler.syntax.analizer;

import java.util.List;
import org.ulasalle.compiler.util.Respuesta;

/**
 *
 * @author francisco
 */
public class RespuestaSintactica extends Respuesta
{
    
    public RespuestaSintactica(String nombreArchivo, List<Cuadruplo> resultados, List<ErrorSintactico> errores)
    {
        super(nombreArchivo, resultados, errores);
    }
    
}
