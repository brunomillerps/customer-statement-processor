package bmps.statement.processor.domain.rules;

import bmps.statement.processor.domain.Issue;
import bmps.statement.processor.domain.Transaction;

import java.util.function.Predicate;

public class BalanceMatchRule implements Rule {
    private final Predicate<Transaction> doesBalanceMatches = tx -> tx.startBalance().add(tx.mutation()).compareTo(tx.endBalance()) == 0;

    @Override
    public void apply(Transaction transaction) {
        if (doesBalanceMatches.negate().test(transaction)) {
            transaction.addIssue(Issue.BALANCE_NOT_MATCH);
        }
    }
}
