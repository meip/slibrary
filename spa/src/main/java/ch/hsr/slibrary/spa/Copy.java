package ch.hsr.slibrary.spa;

public class Copy {
	
	public enum Condition {NEW, GOOD, DAMAGED, WASTE, LOST }
	
	public static long nextInventoryNumber = 1;
	
	private final long inventoryNumber;
	private final Book book;
	private Condition condition;
	
	public Copy(Book title) {
		this.book = title;
		inventoryNumber = nextInventoryNumber++;
		condition = Condition.NEW;
	}

	public Book getTitle() {
		return book;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public long getInventoryNumber() {
		return inventoryNumber;
	}

    @Override
    public String toString() {
        return String.valueOf(inventoryNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Copy copy = (Copy) o;

        if (inventoryNumber != copy.inventoryNumber) return false;
        if (!book.equals(copy.book)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (inventoryNumber ^ (inventoryNumber >>> 32));
        result = 31 * result + book.hashCode();
        return result;
    }
}
