package org.ulasalle.compiler.syntax.analizer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.ulasalle.compiler.util.SimboloDeserializer;

/**
 *
 * @author francisco
 */
@JsonDeserialize(using = SimboloDeserializer.class)
public interface Simbolo
{

}
