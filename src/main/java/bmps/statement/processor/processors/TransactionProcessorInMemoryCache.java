package bmps.statement.processor.processors;

import bmps.statement.processor.domain.Issue;
import bmps.statement.processor.rules.Rule;
import bmps.statement.processor.domain.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionProcessorInMemoryCache implements TransactionProcessor {

    private final List<Transaction> transactions = new ArrayList<>();
    private final Map<Integer, Integer> duplicatedTxs = new HashMap<>();
    private final List<Rule> rules;
    private int index = 0;

    public TransactionProcessorInMemoryCache(List<Rule> rules) {
        this.rules = rules;
    }

    public void process(Transaction transaction) {

        rules.forEach( rule -> rule.apply(transaction));

        validateDuplicated(transaction);

        addTransaction(transaction);
    }

    public List<Transaction> getViolations() {
        return this.transactions.stream().filter(t -> t.issues().size() > 0).collect(Collectors.toList());
    }

    private void addTransaction(Transaction transaction) {
        this.index++;
        this.transactions.add(transaction);
    }

    private void validateDuplicated(Transaction transaction) {
        var foundDuplicatedTxIndex = duplicatedTxs.putIfAbsent(transaction.reference(), index);
        if (foundDuplicatedTxIndex != null) {
            transaction.addIssue(Issue.DUPLICATED);
            transactions.get(foundDuplicatedTxIndex).addIssue(Issue.DUPLICATED);
        }
    }
}
