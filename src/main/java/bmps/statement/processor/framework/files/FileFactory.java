package bmps.statement.processor.framework.files;

import bmps.statement.processor.domain.StatementReader;
import bmps.statement.processor.domain.StatementWriter;
import bmps.statement.processor.framework.files.csv.StatementCsvReader;
import bmps.statement.processor.framework.files.csv.StatementCsvWriter;
import bmps.statement.processor.framework.files.xml.StatementXmlReader;
import bmps.statement.processor.framework.files.xml.StatementXmlWriter;

import java.nio.file.Path;

public class FileFactory {

    private FileFactory() {
    }

    public static StatementReader getFileReader(Path path) {
        return switch (getFileExtension(path)) {
            case "csv" -> new StatementCsvReader(path);
            case "xml" -> new StatementXmlReader(path);
            default -> null;
        };
    }

    public static StatementWriter getFileWriter(Path path) {
        return switch (getFileExtension(path)) {
            case "csv" -> new StatementCsvWriter(path);
            case "xml" -> new StatementXmlWriter(path);
            default -> null;
        };
    }

    private static String getFileExtension(Path path) {
        return path.getFileName().toString().split("\\.")[1];
    }
}
