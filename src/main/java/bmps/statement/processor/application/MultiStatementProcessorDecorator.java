package bmps.statement.processor.application;

import bmps.statement.processor.domain.Statement;

import java.util.List;

public abstract class MultiStatementProcessorDecorator extends CustomerStatementDecorator {

    public MultiStatementProcessorDecorator(CustomerStatementProcessor processor) {
        super(processor);
    }

    public abstract List<Statement> executeAll(List<Statement> statements);

    @Override
    public List<Statement> execute() {
        return executeAll(super.execute());
    }
}
