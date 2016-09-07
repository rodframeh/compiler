package org.ulasalle.source.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class SourceFileReader {

    private File file;
    private List lines;

    public SourceFileReader() {
    }

    public void setFile(File file) throws FileNotFoundException {
        this.file = file;
    }

    public List<String> getLines() throws IOException {
        if (lines == null) {
            lines = new LinkedList();
            try (Stream<String> sourceFileStream = Files.lines(file.toPath())) {
                sourceFileStream.forEach(lineStream -> lines.add(lineStream));
            }
        }
        return lines;
    }

}
