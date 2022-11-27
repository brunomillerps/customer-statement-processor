package bmps.statement.processor.rules;

import bmps.statement.processor.domain.Transaction;

public interface Rule {
    void apply(Transaction transaction);
}
