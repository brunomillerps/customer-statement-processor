package bmps.statement.processor.domain.rules;

import bmps.statement.processor.domain.Transaction;

public interface Rule {
    void apply(Transaction transaction);
}
