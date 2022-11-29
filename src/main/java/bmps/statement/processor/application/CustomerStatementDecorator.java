package bmps.statement.processor.application;

import bmps.statement.processor.domain.Statement;

import java.util.List;

public abstract class CustomerStatementDecorator implements CustomerStatementProcessor {

    private final CustomerStatementProcessor defaultProcessor;

    public CustomerStatementDecorator(CustomerStatementProcessor processor) {
        defaultProcessor = processor;
    }

    @Override
    public List<Statement> execute() {
        return defaultProcessor.execute();
    }
}
