package bmps.statement.processor.fileManager.csv;

import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.domain.Transaction;
import bmps.statement.processor.fileManager.FileWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileWriter implements FileWriter {

    private static final String REPORT_HEADER = "reference,startBalance,mutation,endBalance,issues";
    private final TransactionProcessor txProcessor;

    private final Path file;

    public CsvFileWriter(TransactionProcessor txProcessor, Path path) {
        this.txProcessor = txProcessor;
        this.file = path;
    }

    @Override
    public void writeReport() {
        if (txProcessor.getViolations().size() == 0) {
            return;
        }

        System.out.println(Thread.currentThread().getName() + " - Writing report for file " + this.file);
        try {
            Path reportPath = Path.of(this.file.getParent().toString(), "/report");
            Files.createDirectories(reportPath);

            try (BufferedWriter bw = Files.newBufferedWriter(Path.of(reportPath + "/records.csv"))) {
                bw.write(REPORT_HEADER);
                for (Transaction t : txProcessor.getViolations()) {
                    bw.newLine();
                    bw.write(t.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing report for file " + file + " at /report");
            System.out.println(e);
        }
    }
}
