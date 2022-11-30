package bmps.statement.processor.framework.files.csv;

import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.domain.StatementReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.stream.Stream;

public class StatementCsvReader implements StatementReader {

    private final Path path;

    public StatementCsvReader(Path from) {
        this.path = from;
    }

    @Override
    public Stream<Statement> read() throws IOException {

        System.out.println(Thread.currentThread().getName() + " - Reading file " + path);

        return Files.lines(path, StandardCharsets.ISO_8859_1)
                .skip(1)
                .map(StatementCsvReader::mapToTransaction);
    }

    private static Statement mapToTransaction(String line) {
        var columns = line.split(",");
        return new Statement(
                Integer.valueOf(columns[0]),
                new BigDecimal(columns[3]),
                new BigDecimal(columns[4]),
                new BigDecimal(columns[5]),
                new HashSet<>());
    }
}
