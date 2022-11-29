package bmps.statement.processor.domain;

import java.io.IOException;
import java.util.stream.Stream;

public interface StatementReader {

    Stream<Statement> read() throws IOException;
}
