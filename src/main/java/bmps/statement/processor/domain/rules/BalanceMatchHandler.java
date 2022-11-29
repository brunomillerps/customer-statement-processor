package bmps.statement.processor.domain.rules;

import bmps.statement.processor.domain.Issue;
import bmps.statement.processor.domain.Statement;

import java.util.function.Predicate;

public class BalanceMatchHandler extends RuleHandler {
    private final Predicate<Statement> doesBalanceMatches = tx -> tx.startBalance().add(tx.mutation()).compareTo(tx.endBalance()) == 0;

    @Override
    public Statement check(Statement statement) {
        if (doesBalanceMatches.negate().test(statement)) {
            statement.addIssue(Issue.BALANCE_NOT_MATCH);
        }
        return checkNext(statement);
    }
}
