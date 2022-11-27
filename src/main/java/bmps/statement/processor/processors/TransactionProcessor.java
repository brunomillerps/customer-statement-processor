package bmps.statement.processor.processors;

import bmps.statement.processor.domain.Transaction;

import java.util.List;

public interface TransactionProcessor {

    void process(Transaction transaction);
    List<Transaction> getViolations();

}
