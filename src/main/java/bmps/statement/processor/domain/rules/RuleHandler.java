package bmps.statement.processor.domain.rules;

import bmps.statement.processor.domain.Statement;

public abstract class RuleHandler {
    private RuleHandler next;

    /**
     * Builds chains of processors.
     */
    public static RuleHandler link(RuleHandler first, RuleHandler... chain) {
        RuleHandler head = first;
        for (RuleHandler nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     */
    protected Statement checkNext(Statement statement) {
        if (next == null) {
            return statement;
        }
        return next.check(statement);
    }

    public abstract Statement check(Statement statement);

}
