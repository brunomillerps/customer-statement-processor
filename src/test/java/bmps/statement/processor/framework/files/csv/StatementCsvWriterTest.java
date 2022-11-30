package bmps.statement.processor.framework.files.csv;

import bmps.statement.processor.domain.Issue;
import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.framework.files.FileFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class StatementCsvWriterTest {

    @Test
    public void writeFileFromTestSource() throws IOException {

        var path = Path.of("src/test/resources/data/records.csv");

        var writer = FileFactory.getFileWriter(path);

        var statement = new Statement(10, BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                Set.of(Issue.DUPLICATED));

        writer.write(List.of(statement));

        try (Stream<String> linesStream = Files.lines(Path.of(path.getParent() + "/report/records.csv"))) {

            var lines = linesStream.toList();

            Assertions.assertEquals(2, lines.size());

            Assertions.assertEquals("reference,startBalance,mutation,endBalance,issues", lines.get(0));
            Assertions.assertEquals("10,1,2,3,[DUPLICATED]", lines.get(1));

        } finally {
            Files.deleteIfExists(Path.of(path.getParent() + "/report/" + path.getFileName()));
            Files.deleteIfExists(Path.of(path.getParent() + "/report"));
        }
    }
}
