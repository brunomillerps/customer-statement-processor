package bmps.statement.processor.framework.files.csv;

import bmps.statement.processor.framework.files.FileFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementCsvReaderTest {

    @Test
    public void readFileFromTestSource() throws IOException {

        var reader = FileFactory.getFileReader(Path.of("src/test/resources/data/records.xml"));

        var streamOf = reader.read();

        assertEquals(10, streamOf.count());
    }
}