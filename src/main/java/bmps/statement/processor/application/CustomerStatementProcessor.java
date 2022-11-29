package bmps.statement.processor.application;

import bmps.statement.processor.domain.Statement;

import java.util.List;

public interface CustomerStatementProcessor {
    List<Statement> execute();
}
