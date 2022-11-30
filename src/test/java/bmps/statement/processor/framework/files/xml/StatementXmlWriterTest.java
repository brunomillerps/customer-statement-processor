package bmps.statement.processor.framework.files.xml;

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

public class StatementXmlWriterTest {

    @Test
    public void writeFileFromTestSource() throws IOException {

        var path = Path.of("src/test/resources/data/records.xml");

        var writer = FileFactory.getFileWriter(path);

        var statement = new Statement(10, BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                Set.of(Issue.DUPLICATED));

        writer.write(List.of(statement));

        try (Stream<String> linesStream = Files.lines(Path.of(path.getParent().toString() + "/report/records.xml"))) {

            StringBuilder sb = new StringBuilder();
            linesStream.forEach(line -> {
                sb.append(line);
                sb.append(System.lineSeparator());
            });

            var expect = """
                    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                    <records>
                        <record reference="10">
                            <startBalance>1</startBalance>
                            <mutation>2</mutation>
                            <endBalance>3</endBalance>
                            <issues>
                                <issue>DUPLICATED</issue>
                            </issues>
                        </record>
                    </records>
                    """;

            Assertions.assertEquals(expect, sb.toString());

        } finally {
            Files.deleteIfExists(Path.of("src/test/resources/data/report/records.xml"));
            Files.deleteIfExists(Path.of("src/test/resources/data/report"));
        }
    }
}