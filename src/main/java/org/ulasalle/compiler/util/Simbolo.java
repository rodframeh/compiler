package org.ulasalle.compiler.util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.ulasalle.compiler.syntax.analizer.Bloque;

@JsonDeserialize(using = SimboloDeserializer.class)
public interface Simbolo
{
    public int getIndiceRegla();
    public void setIndiceRegla(int indiceRegla);
    public int getIdRegla();
    public void setIdRegla(int idRegla);
    public int getIdSimbolo();
    public void setIdSimbolo(int idSimbolo);
    public Simbolo getPadre();
    public void setPadre(Simbolo padre);
    public Simbolo copiarValor();
    public Bloque getContexto();
    public void setContexto(Bloque contexto);
}
