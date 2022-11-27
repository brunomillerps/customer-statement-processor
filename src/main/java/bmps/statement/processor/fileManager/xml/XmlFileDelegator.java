package bmps.statement.processor.fileManager.xml;

import bmps.statement.processor.fileManager.FileReader;
import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.fileManager.FileManager;
import bmps.statement.processor.fileManager.FileWriter;

import java.nio.file.Path;

public class XmlFileDelegator implements FileManager {

    private final FileReader xmlDomFileReader;
    private final FileWriter xmlFileWriter;

    public XmlFileDelegator(TransactionProcessor txProcessor, Path path) {
        this.xmlFileWriter = new XmlFileWriter(txProcessor, path);
        this.xmlDomFileReader = new XmlDomFileReader(txProcessor, path);
    }

    @Override
    public void execute() {
        xmlDomFileReader.readFile();
        xmlFileWriter.writeReport();
    }
}
