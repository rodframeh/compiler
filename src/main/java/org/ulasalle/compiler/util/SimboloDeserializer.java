/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.compiler.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import org.ulasalle.compiler.syntax.analizer.NoTerminal;
import org.ulasalle.compiler.syntax.analizer.Simbolo;
import org.ulasalle.compiler.syntax.analizer.Terminal;

/**
 *
 * @author francisco
 */
public class SimboloDeserializer extends JsonDeserializer<Simbolo>
{

    @Override
    public Simbolo deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException
    {
        ObjectMapper mapper=(ObjectMapper) jp.getCodec();
        ObjectNode root=mapper.readTree(jp);
        if((root.has("tipoToken") && root.get("tipoToken").asText().length()>0) || (root.has("lexema") && root.get("lexema").asText().length()>0))
            return mapper.readValue(root.toString(), Terminal.class);
        else
            return mapper.readValue(root.toString(), NoTerminal.class); 
    }
    
}
