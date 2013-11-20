package ch.hsr.slibrary.spa;

import java.util.Observable;

public class Customer extends Observable{
	
	private String name, surname, street, city;
	private int zip;

	public Customer(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
	
	public void setAdress(String street, int zip, String city) {
		this.street = street;
		this.zip = zip;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
	
	@Override
	public String toString() {
		return name + " " + surname + " , " + street + " , " + zip + " " + city;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (zip != customer.zip) return false;
        if (city != null ? !city.equals(customer.city) : customer.city != null) return false;
        if (!name.equals(customer.name)) return false;
        if (!street.equals(customer.street)) return false;
        if (!surname.equals(customer.surname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + zip;
        return result;
    }
}
