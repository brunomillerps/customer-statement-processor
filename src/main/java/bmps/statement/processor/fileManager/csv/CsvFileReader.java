package bmps.statement.processor.fileManager.csv;

import bmps.statement.processor.fileManager.FileReader;
import bmps.statement.processor.processors.TransactionProcessor;
import bmps.statement.processor.domain.Transaction;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class CsvFileReader implements FileReader {

    private final TransactionProcessor txProcessor;
    private final Path file;

    public CsvFileReader(TransactionProcessor txProcessor, Path path) {
        this.txProcessor = txProcessor;
        this.file = path;
    }

    @Override
    public void readFile() {
        System.out.println(Thread.currentThread().getName() + " - Reading file " + this.file);
        try (BufferedReader bf = Files.newBufferedReader(this.file, StandardCharsets.ISO_8859_1)) {
            // skip header
            bf.readLine();

            String line;
            while ((line = bf.readLine()) != null) {
                var columns = line.split(",");
                var transaction = new Transaction(
                        Integer.valueOf(columns[0]),
                        new BigDecimal(columns[3]),
                        new BigDecimal(columns[4]),
                        new BigDecimal(columns[5]),
                        new HashSet<>());

                txProcessor.process(transaction);
            }

        } catch (Exception e) {
            System.out.println("An occurred while treating file " + this.file);
            System.out.println(e);
        }
    }
}
