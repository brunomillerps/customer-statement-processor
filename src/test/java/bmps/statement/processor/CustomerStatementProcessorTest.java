package bmps.statement.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

class CustomerStatementProcessorTest {

    String xmlOutput = """
            <?xml version="1.0" encoding="UTF-8" standalone="no"?>
            <records>
                <record reference="176186">
                    <startBalance>5429</startBalance>
                    <mutation>-939</mutation>
                    <endBalance>6368</endBalance>
                    <issues>
                        <issue>BALANCE_NOT_MATCH</issue>
                    </issues>
                </record>
                <record reference="145501">
                    <startBalance>3980</startBalance>
                    <mutation>1000</mutation>
                    <endBalance>4981</endBalance>
                    <issues>
                        <issue>BALANCE_NOT_MATCH</issue>
                    </issues>
                </record>
            </records>
            """;

    String csvOutput = """
            reference,startBalance,mutation,endBalance,issues
            112806,30.53,13.45,43.98,[DUPLICATED]
            112806,75.38,-42.58,32.8,[DUPLICATED]
            112806,10.78,9.85,21.63,[DUPLICATED, BALANCE_NOT_MATCH]
            """;

    @Test
    public void start() throws IOException {
        var args = new String[]{"filePath=src/test/resources/data"};

        CustomerStatementProcessor.main(args);

        var pathString = args[0].split("=")[1];
        Path pathCsv = Path.of(pathString + "/report/records.csv");
        Path pathXml = Path.of(pathString + "/report/records.xml");

        assertFileOutput(pathCsv, csvOutput);
        assertFileOutput(pathXml, xmlOutput);

        Files.deleteIfExists(pathCsv);
        Files.deleteIfExists(pathXml);
        Files.deleteIfExists(pathXml.getParent());
    }

    private void assertFileOutput(Path path, String expect) throws IOException {

        try (Stream<String> linesStream = Files.lines(path)) {

            StringBuilder sb = new StringBuilder();
            linesStream.forEach(line -> {
                sb.append(line);
                sb.append(System.lineSeparator());
            });

            Assertions.assertEquals(expect, sb.toString());
        }
    }
}