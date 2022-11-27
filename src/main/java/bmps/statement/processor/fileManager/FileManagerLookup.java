package bmps.statement.processor.fileManager;

import bmps.statement.processor.rules.BalanceMatchRule;
import bmps.statement.processor.fileManager.csv.CsvFileDelegator;
import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.processors.TransactionProcessorInMemoryCache;
import bmps.statement.processor.fileManager.xml.XmlFileDelegator;
import bmps.statement.processor.rules.Rule;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class FileManagerLookup {

    private FileManagerLookup() {
    }

    public static final Predicate<Path> isCsvFile = path -> path.getFileName().toString().endsWith(".csv");
    public static final Predicate<Path> isXmlFile = path -> path.getFileName().toString().endsWith(".xml");

    private static final List<Rule> rules = List.of(new BalanceMatchRule());
    public static FileManager getFileManager(Path path) {
        if (isCsvFile.test(path)) {
            return new CsvFileDelegator(new TransactionProcessorInMemoryCache(rules), path);
        } else if (isXmlFile.test(path)) {
            return new XmlFileDelegator(new TransactionProcessorInMemoryCache(rules), path);
        }

        return null;
    }
}