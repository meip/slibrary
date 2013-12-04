package ch.hsr.slibrary.spa;

import com.apple.jobjc.foundation.NSNotificationCenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class Library extends Observable {

	private List<Copy> copies;
	private List<Customer> customers;
	private List<Loan> loans;
	private List<Book> books;

	public Library() {
		copies = new ArrayList<Copy>();
		customers = new ArrayList<Customer>();
		loans = new ArrayList<Loan>();
		books = new ArrayList<Book>();
	}

	public Loan createAndAddLoan(Customer customer, Copy copy) {
		if (!isCopyLent(copy)) {
			Loan l = new Loan(customer, copy);
			loans.add(l);
            setChanged();
            notifyObservers("loanAdded");
			return l;
		} else {
			return null;
		}
	}

	public Customer createAndAddCustomer(String name, String surname) {
		Customer c = new Customer(name, surname);
		customers.add(c);
        setChanged();
        notifyObservers("customerAdded");
		return c;
	}

	public Book createAndAddBook(String name) {
		Book b = new Book(name);
		addBook(b);
		return b;
	}

	public Copy createAndAddCopy(Book title) {
		Copy c = new Copy(title);
        title.notifyChanged();
		copies.add(c);
        setChanged();
        notifyObservers("copyAdded");
		return c;
	}

    public void removeBook(Book book) {
        Iterator<Copy> it = copies.iterator();
        while(it.hasNext()) {
            Copy copy = it.next();
            if(copy.getTitle().equals(book)) {
                it.remove();
                setChanged();
                notifyObservers("copyRemoved");
            }
        }
        if(books.remove(book)) {
            setChanged();
            notifyObservers("bookRemoved");
        }
    }

    public boolean removeCopy(Copy copy) {
        boolean result = copies.remove(copy);
        if(result) {
            setChanged();
            notifyObservers("copyRemoved");
        }
        return result;
    }

    public void addBook(Book book) {
        books.add(book);
        setChanged();
        notifyObservers("bookAdded");
    }

	public Book findByBookTitle(String title) {
		for (Book b : books) {
			if (b.getName().equals(title)) {
				return b;
			}
		}
		return null;
	}


	public boolean isCopyLent(Copy copy) {
		for (Loan l : loans) {
			if (l.getCopy().equals(copy) && l.isLent()) {
				return true;
			}
		}
		return false;
	}

	public List<Copy> getCopiesOfBook(Book book) {
		List<Copy> res = new ArrayList<Copy>();
		for (Copy c : copies) {
			if (c.getTitle().equals(book)) {
				res.add(c);
			}
		}

		return res;
	}

	public List<Loan> getLentCopiesOfBook(Book book) {
		List<Loan> lentCopies = new ArrayList<Loan>();
		for (Loan l : loans) {
			if (l.getCopy().getTitle().equals(book) && l.isLent()) {
				lentCopies.add(l);
			}
		}
		return lentCopies;
	}

	public List<Loan> getCustomerLoans(Customer customer) {
		return getCustomerLoans(customer, false);
	}

    public List<Loan> getCustomerLoans(Customer customer, boolean onlyLent) {
        List<Loan> lentCopies = new ArrayList<Loan>();
        for (Loan l : loans) {
            if (l.getCustomer().equals(customer) && ((onlyLent) ? l.isLent() : true)) {
                lentCopies.add(l);
            }
        }
        return lentCopies;
    }

	public List<Loan> getOverdueLoans() {
		List<Loan> overdueLoans = new ArrayList<Loan>();
		for ( Loan l : getLoans() ) {
			if (l.isOverdue())
				overdueLoans.add(l);
		}
		return overdueLoans;
	}

    public List<Loan> getOverdueLoansForCustomer(Customer c) {
        List<Loan> overdueLoans = new ArrayList<Loan>();
        for ( Loan l : getLoans() ) {
            if (l.isOverdue() && l.getCustomer().equals(c))
                overdueLoans.add(l);
        }
        return overdueLoans;
    }

	public List<Copy> getAvailableCopies(){
		return getCopies(false);
	}

    public boolean isCopyAvailableByInventoryId(long inventoryId) {
        for (Copy copy : getAvailableCopies()) {
            if (copy.getInventoryNumber() == inventoryId) {
                return true;
            }
        }
        return false;
    }

	public List<Copy> getLentOutBooks(){
		return getCopies(true);
	}

	private List<Copy> getCopies(boolean isLent) {
		List<Copy> retCopies = new ArrayList<Copy>();
		for (Copy c : copies) {
			if (isLent == isCopyLent(c) && !c.getCondition().equals(Copy.Condition.LOST) && !c.getCondition().equals(Copy.Condition.WASTE)) {
				retCopies.add(c);
			}
		}
		return retCopies;
	}

	public List<Copy> getCopies() {
		return copies;
	}

    public Copy getCopyByInventoryNumber(long inventoryNumber) {
        for(Copy c: copies) {
            if(c.getInventoryNumber() == inventoryNumber) { return c; }
        }
        return null;
    }

	public List<Loan> getLoans() {
		return loans;
	}

	public List<Book> getBooks() {
		return books;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}
