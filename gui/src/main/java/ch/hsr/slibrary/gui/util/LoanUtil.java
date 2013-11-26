package ch.hsr.slibrary.gui.util;

import ch.hsr.slibrary.spa.Loan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class LoanUtil {

    public final static String LOAN_IS_OVERDUE = "fällig";
    public final static String LOAN_IS_VALID = "ok";

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public static String loanStatus(int daysOverdue) {
        return (daysOverdue > 0) ? LoanUtil.LOAN_IS_OVERDUE : LoanUtil.LOAN_IS_VALID;
    }

    public static String getPickupDate(Loan loan) {
        return sdf.format(new Long(loan.getPickupDate().getTimeInMillis()));
    }

    public static String getDueDateFormatted(Loan loan) {
        if(loan.getDaysOverdue() > 0) {
            return sdf.format(new Long(loan.getDueDate().getTimeInMillis())) + " (seit " + loan.getDaysOverdue() + " Tage überfällig)";
        }  else if (loan.isLent()) {
            long daysOverDue = TimeUnit.MILLISECONDS.toDays(loan.getDueDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
            return sdf.format(new Long(loan.getDueDate().getTimeInMillis())) + " (in " + daysOverDue + " Tagen)";
         } else {
            return sdf.format(new Long(loan.getDueDate().getTimeInMillis()));
        }
    }

    public static String getReturnDate(Loan loan) {
        return getReturnDate(loan, true);
    }

    public static String getDueDate(Loan loan) {
        return sdf.format(loan.getDueDate());
    }

    public static String getReturnDate(Loan loan, boolean stringAppendix) {
        if (loan.getDaysOverdue() > 0) {
            return sdf.format(new Long(loan.getDueDate().getTimeInMillis())) + ((stringAppendix) ? " (seit " + loan.getDaysOverdue() + " Tage überfällig)" : "");
        } else if (loan.isLent()) {
            long daysOverDue = TimeUnit.MILLISECONDS.toDays(loan.getDueDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
            return sdf.format(new Long(loan.getDueDate().getTimeInMillis())) + ((stringAppendix) ? " (in " + daysOverDue + " Tagen)" : "");
        } else {
            return sdf.format(new Long(loan.getReturnDate().getTimeInMillis()));
        }
    }

}
