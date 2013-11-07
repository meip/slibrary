package ch.hsr.slibrary.gui.util;

public class LoanUtil {

    public final static String LOAN_IS_OVERDUE = "fällig";
    public final static String LOAN_IS_VALID = "ok";

    public static String loanStatus(int daysOverdue) {
        return (daysOverdue > 0) ? LoanUtil.LOAN_IS_OVERDUE : LoanUtil.LOAN_IS_VALID;
    }
}
