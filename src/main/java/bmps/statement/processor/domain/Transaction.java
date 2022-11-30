package bmps.statement.processor.domain;

import java.math.BigDecimal;
import java.util.Set;

public record Transaction(Integer reference, BigDecimal startBalance, BigDecimal mutation, BigDecimal endBalance,
                   Set<Issue> issues) {

    public void addIssue(Issue issue) {
        this.issues.add(issue);
    }

    @Override
    public String toString() {
        return reference + "," + startBalance + "," + mutation + "," + endBalance + "," + issues.toString().replaceAll(",", ";");
    }

    @Override
    public int hashCode() {
        return reference();
    }
}