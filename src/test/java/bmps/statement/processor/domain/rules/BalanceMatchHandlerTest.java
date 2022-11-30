package bmps.statement.processor.domain.rules;

import bmps.statement.processor.domain.Issue;
import bmps.statement.processor.domain.Statement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

class BalanceMatchHandlerTest {

    @Test
    void statementShouldNotMatchTheEndBalance() {
        var handler = new BalanceMatchHandler();

        Statement statement = new Statement(100,
                new BigDecimal(100),
                new BigDecimal(10),
                new BigDecimal(100),
                new HashSet<>());

        statement = handler.check(statement);

        Assertions.assertEquals(statement.issues(), Set.of(Issue.BALANCE_NOT_MATCH));
    }


    @Test
    void statementShouldMatchTheEndBalance() {
        var handler = new BalanceMatchHandler();

        Statement statement = new Statement(100,
                new BigDecimal(100),
                new BigDecimal(10),
                new BigDecimal(90),
                new HashSet<>());

        statement = handler.check(statement);

        Assertions.assertEquals(statement.issues(), Set.of());
    }

}