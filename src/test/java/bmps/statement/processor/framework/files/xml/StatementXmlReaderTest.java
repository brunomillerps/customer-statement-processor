package bmps.statement.processor.framework.files.xml;

import bmps.statement.processor.framework.files.FileFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementXmlReaderTest {

    @Test
    public void readFileFromTestSource() throws IOException {

        var reader = FileFactory.getFileReader(Path.of("src/test/resources/data/records.csv"));

        var streamOf = reader.read();

        assertEquals(10, streamOf.count());
    }
}
