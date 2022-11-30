package bmps.statement.processor.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class StatementTest {

    @Test
    public void toStringShouldReturnSeparatedByComma() {
        var statement = new Statement(10, BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), Set.of());

        Assertions.assertEquals("10,1,2,3,[]", statement.toString());
    }

    @Test
    public void shouldAddIssue() {
        var statement = new Statement(10, BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                new HashSet<>());

        statement.addIssue(Issue.DUPLICATED);
        Assertions.assertEquals("10,1,2,3,[DUPLICATED]", statement.toString());
    }
}
