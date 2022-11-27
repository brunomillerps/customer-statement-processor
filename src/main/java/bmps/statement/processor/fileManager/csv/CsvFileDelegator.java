package bmps.statement.processor.fileManager.csv;

import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.fileManager.FileManager;
import bmps.statement.processor.fileManager.FileReader;
import bmps.statement.processor.fileManager.FileWriter;

import java.nio.file.Path;

public class CsvFileDelegator implements FileManager {

    private final FileReader csvFileReader;
    private final FileWriter csvFileWriter;

    public CsvFileDelegator(TransactionProcessor txProcessor, Path path) {
        this.csvFileReader = new CsvFileReader(txProcessor, path);
        this.csvFileWriter = new CsvFileWriter(txProcessor, path);
    }

    @Override
    public void execute() {
        this.csvFileReader.readFile();
        this.csvFileWriter.writeReport();
    }
}
