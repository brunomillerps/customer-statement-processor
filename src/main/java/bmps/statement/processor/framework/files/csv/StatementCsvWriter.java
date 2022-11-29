package bmps.statement.processor.framework.files.csv;

import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.domain.StatementWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StatementCsvWriter implements StatementWriter {
    private final Path file;

    private static final String REPORT_HEADER = "reference,startBalance,mutation,endBalance,issues";

    public StatementCsvWriter(Path file) {
        this.file = file;
    }

    @Override
    public void write(List<Statement> statements) {
        System.out.println(Thread.currentThread().getName() + " - Writing report for file " + this.file);
        try {
            Path reportPath = Path.of(this.file.getParent().toString(), "/report");
            Files.createDirectories(reportPath);

            try (BufferedWriter bw = Files.newBufferedWriter(Path.of(reportPath + "/records.csv"))) {
                bw.write(REPORT_HEADER);
                for (Statement t : statements) {
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
