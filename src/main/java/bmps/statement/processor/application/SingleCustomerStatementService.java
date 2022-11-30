package bmps.statement.processor.application;

import bmps.statement.processor.domain.Statement;
import bmps.statement.processor.domain.StatementReader;
import bmps.statement.processor.domain.rules.RuleHandler;

import java.util.List;

public class SingleCustomerStatementService implements CustomerStatementProcessor {

    private final StatementReader reader;

    private final RuleHandler processorHandler;

    public SingleCustomerStatementService(StatementReader reader, RuleHandler processorHandler) {

        this.processorHandler = processorHandler;

        this.reader = reader;
    }

    public List<Statement> execute() {
        try {
            var statementStream = this.reader.read();

            // stream auto closes
            return statementStream
                    .map(processorHandler::check)
                    .toList();

        } catch (Exception e) {
            System.out.println(e);
        }

        return List.of();
    }
}