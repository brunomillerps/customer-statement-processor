package bmps.statement.processor.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class TransactionTest {

    @Test
    public void testToString() {
        Transaction transaction = new Transaction(10, new BigDecimal(10), new BigDecimal(1), new BigDecimal(9), Set.of(Issue.BALANCE_NOT_MATCH));

        Assertions.assertEquals(transaction.toString(), "10,10,1,9,[BALANCE_NOT_MATCH]");
    }
}