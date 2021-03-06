package ch.hsr.slibrary.spa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class Loan extends Observable {

	private Copy copy;
	private Customer customer;
	private GregorianCalendar pickupDate, returnDate;
	private final static int DAYS_TO_RETURN_BOOK = 30;

	public Loan(Customer customer, Copy copy) {
		this.copy = copy;
		this.customer = customer;
		pickupDate = new GregorianCalendar();
	}

	public boolean isLent() {
		return returnDate == null;
	}

	public boolean returnCopy() {
		try {
			returnCopy(new GregorianCalendar());
		} catch (IllegalLoanOperationException e) {
			return false;
		}
		return true;
	}

	public void returnCopy(GregorianCalendar returnDate)
			throws IllegalLoanOperationException {
		if (returnDate.before(pickupDate)) {
			throw new IllegalLoanOperationException(
					"Return Date is before pickupDate");
		}
		this.returnDate = returnDate;
        notifyChanged();
	}

	public void setPickupDate(GregorianCalendar pickupDate)
			throws IllegalLoanOperationException {
		if (!isLent()) {
			throw new IllegalLoanOperationException("Loan is already retuned");
		}
		this.pickupDate = pickupDate;
        notifyChanged();
	}

	public GregorianCalendar getPickupDate() {
		return pickupDate;
	}

	public GregorianCalendar getReturnDate() {
		return returnDate;
	}

	public Copy getCopy() {
		return copy;
	}

    public void setCopy(Copy copy) {
        this.copy = copy;
        notifyChanged();
    }

    public Customer getCustomer() {
		return customer;
	}

    public void setCustomer(Customer customer) {
        this.customer = customer;
        notifyChanged();
    }
    
    public void notifyChanged() {
        setChanged();
        notifyObservers();
    }

    @Override
	public String toString() {
		return "Loan of: " + copy.getTitle().getName() + "\tFrom: "
				+ customer.getName() + " " + customer.getSurname()
				+ "\tPick up: " + getFormattedDate(pickupDate) + "\tReturn: "
				+ getFormattedDate(returnDate) + "\tDays: "
				+ getDaysOfLoanDuration();
	}

	private String getFormattedDate(GregorianCalendar date) {
		if (date != null) {
			DateFormat f = SimpleDateFormat.getDateInstance();
			return f.format(date.getTime());
		}
		return "00.00.00";
	}

	public int getDaysOfLoanDuration() {
		if (returnDate != null)
			return (int) (returnDate.getTimeInMillis() - pickupDate
					.getTimeInMillis())
					/ 1000 / 60 / 60 / 24;
		return -1;
	}

	public int getDaysOverdue() {
		if ( !isOverdue() )
			return 0;

		GregorianCalendar dueDate = (GregorianCalendar) pickupDate.clone();
		dueDate.add(GregorianCalendar.DAY_OF_YEAR, DAYS_TO_RETURN_BOOK);
		return (int) TimeUnit.MILLISECONDS.toDays(new GregorianCalendar().getTimeInMillis() - dueDate.getTimeInMillis());
	}

	public boolean isOverdue() {
		if ( !isLent() )
			return false;
		return ( new GregorianCalendar().after(getDueDate()));
	}

    public GregorianCalendar getDueDate() {
        GregorianCalendar dueDate = (GregorianCalendar) pickupDate.clone();
        dueDate.add(GregorianCalendar.DAY_OF_YEAR, DAYS_TO_RETURN_BOOK);
        dueDate.add(GregorianCalendar.HOUR_OF_DAY, 23);
        dueDate.add(GregorianCalendar.MINUTE, 59);
        dueDate.add(GregorianCalendar.SECOND, 59);
        return dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loan loan = (Loan) o;

        if (!copy.equals(loan.copy)) return false;
        if (!customer.equals(loan.customer)) return false;
        if (!pickupDate.equals(loan.pickupDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = copy.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + pickupDate.hashCode();
        return result;
    }
}
