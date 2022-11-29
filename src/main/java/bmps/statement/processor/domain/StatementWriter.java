package bmps.statement.processor.domain;

import java.util.List;
public interface StatementWriter {
    void write(List<Statement> statements);
}
