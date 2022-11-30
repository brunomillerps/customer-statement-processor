package bmps.statement.processor.framework.files;

import bmps.statement.processor.domain.StatementReader;
import bmps.statement.processor.domain.StatementWriter;
import bmps.statement.processor.framework.files.csv.StatementCsvReader;
import bmps.statement.processor.framework.files.csv.StatementCsvWriter;
import bmps.statement.processor.framework.files.xml.StatementXmlReader;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FileFactoryTest {

    @Test
    public void shouldCreateCsvReaderBasedOnPath() {

        StatementReader reader = FileFactory.getFileReader(Path.of("/data/record.csv"));

        assertInstanceOf(StatementCsvReader.class, reader, "Factory not returning CSV reader type");
    }

    @Test
    public void shouldCreateXmlReaderBasedOnPath() {

        StatementReader reader = FileFactory.getFileReader(Path.of("/data/record.xml"));

        assertInstanceOf(StatementXmlReader.class, reader, "Factory not returning XML reader type");
    }

    @Test
    public void shouldCreateCsvWriterBasedOnPath() {

        StatementWriter reader = FileFactory.getFileWriter(Path.of("/data/record.csv"));

        assertInstanceOf(StatementCsvWriter.class, reader, "Factory not returning CSV writer type");
    }

    @Test
    public void shouldCreateXmlWriterBasedOnPath() {

        StatementWriter reader = FileFactory.getFileWriter(Path.of("/data/record.xml"));

        assertInstanceOf(StatementWriter.class, reader, "Factory not returning XML writer type");
    }
}