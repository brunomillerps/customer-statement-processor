package bmps.statement.processor.application;

import bmps.statement.processor.domain.Issue;
import bmps.statement.processor.domain.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateStatementsProcessorDecorator extends MultiStatementProcessorDecorator {

    public DuplicateStatementsProcessorDecorator(CustomerStatementProcessor processor) {
        super(processor);
    }

    @Override
    public List<Statement> executeAll(List<Statement> statements) {
        Map<Integer, Statement> duplicatedIndex = new HashMap<>();

        for (Statement s : statements) {
            var foundStatement = duplicatedIndex.putIfAbsent(s.reference(), s);
            if (foundStatement != null) {
                foundStatement.addIssue(Issue.DUPLICATED);
                s.addIssue(Issue.DUPLICATED);
            }
        }
        return statements;
    }
}
