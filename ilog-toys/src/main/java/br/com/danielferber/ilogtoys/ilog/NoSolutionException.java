package br.com.danielferber.ilogtoys.ilog;

/**
 * Signals that the solver was not able to determine any solution within allowed
 * time or number of iterations. Though, the model might be feasible. The reason
 * for not being able to find the solution is given by the {@link #reason}
 * attribute.
 */
public class NoSolutionException extends Exception {

    private static final long serialVersionUID = 1L;

    public static enum Reason {

        /**
         * The problem is unbounded. The objective function may increase or
         * decrease infinitely as there are no constraints.
         */
        UNBOUNDED,
        /**
         * The problem is infeasible. There is no solution that might satisfy
         * all constraints.
         */
        INFEASIBLE,
        /**
         * The problem does not have a solution. But it was not possible to
         * determine if the problem is unbound or infeasible.
         */
        UNBOUNDED_INFEASIBLE,
        /**
         * There was not yet enough time to find a solution.
         */
        INCOMPLETE,
        /**
         * The execution was interrupted programmatically from another thread.
         */
        INTERRUPTED;
    }
    /**
     * The reason why the execution failed.
     */
    public final Reason reason;

    public NoSolutionException(Reason reason) {
        super("No solution found.");
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
