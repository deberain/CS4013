import java.time.LocalDate;

/**
 *
 * Tax class created by Bryan Carty to represent a tax due for a property and track key information
 *
 */
public class Tax {
	int yearDue;
	double taxDue;
	PropertyOwner propOwner;
	Property prop;
	boolean paid = false;
	double valueWhenPaid = 0;
	int yearPaid = 9999;

	/**
	 *
	 * No-arg constructor for Tax class
	 *
	 */
	public Tax() {}

	/**
	 *
	 * Constructor used when creating a new tax
	 *
	 * Author: Bryan Carty
	 *
	 * @param propOwner owner of the property
	 * @param prop property for which tax is due
	 * @param taxDue amount that must be paid
	 */
	public Tax(PropertyOwner propOwner, Property prop, double taxDue) {
		this.propOwner = propOwner;
		this.prop = prop;
		this.taxDue = taxDue;

		yearDue = LocalDate.now().getYear(); //tax must be paid this year.
	}

	/**
	 *
	 * Constructor to be used when reading in sample data
	 *
	 * Author: Jonathan Falvey
	 *
	 * @param owner Owner of the property
	 * @param property property that tax is due on
	 * @param taxDue amount that is due to be paid
	 * @param yearDue the year in which this tax must be paid
	 */
	public Tax(PropertyOwner owner, Property property, double taxDue, int yearDue) {
		propOwner = owner;
		prop = property;
		this.taxDue = taxDue;
		this.yearDue = yearDue;
	}

	/**
	 *
	 * Alternate Constructor to be used when reading in sample data. Used for taxes that have been paid
	 *
	 * Author: Jonathan Falvey
	 *
	 * @param owner Owner of the property
	 * @param property property that tax is due on
	 * @param taxDue amount that is due to be paid
	 * @param yearDue the year in which this tax must be paid
	 * @param paid indicates if this tax has been paid
	 * @param valueWhenPaid the amount that was paid (incl. penalty rate if applicable) when the owner paid this tax
	 * @param yearPaid the year in which this tax was paid
	 */
	public Tax(PropertyOwner owner, Property property, double taxDue, int yearDue, boolean paid, double valueWhenPaid, int yearPaid) {
		propOwner = owner;
		prop = property;
		this.taxDue = taxDue;
		this.yearDue = yearDue;
		this.paid = paid;
		this.valueWhenPaid = valueWhenPaid;
		this.yearPaid = yearPaid;
	}


	/**
	 * Author: Bryan Carty
	 *
	 * @return returns the year that this tax should be paid within
	 */
	public int getYearDue() {
		return this.yearDue;
	}

	/**
	 *
	 * Method called when a tax is paid. This updates the tax, indicating that it has been paid and how much was paid
	 *
	 * Author: Jonathan Falvey
	 *
	 * @param amount amount that was paid
	 */
	public void taxPaid(double amount) {
		paid = true;
		valueWhenPaid = amount;
	}

	/**
	 *
	 * Used when the tax is overdue to be paid. It gets the current value of the tax including the penalty rates
	 *
	 * Author: Jonathan Falvey
	 *
	 * @return returns the current value of the tax
	 */
	public double getCurrentValue() {
		double tempTax = taxDue;
		int yearsNotPaid = LocalDate.now().getYear() - yearDue;

		if (yearsNotPaid > 0) { //avoids recalculating tax from current year
			for (int year = 0; year < yearsNotPaid; year++) {
				tempTax *= 1.07;
			}
		}
		return tempTax;
	}

	/**
	 *
	 * Gets the value of a tax during a specific year
	 *
	 * Author: Jonathan Falvey
	 *
	 * @param year the year we would like to find the value of the tax in
	 * @return returns the value of the tax during the specified year
	 */
	public double getValue(int year) {
		double tempTax = taxDue;
		int yearsNotPaid = year - yearDue;

		if (yearsNotPaid > 0) { //avoids recalculating tax from current year
			for (int y = 0; y < yearsNotPaid; y++) {
				tempTax *= 1.07;
			}
		}
		return tempTax;
	}

	/**
	 *
	 * For presentation to property owner
	 *
	 * Author: Bryan Carty
	 *
	 * @return returns a convenient string format representation with a few details.
	 */
	public String format() {
		return yearDue + ", Current tax due: \u20AC" + String.format("%.2f",getCurrentValue());
	}


	/**
	 *
	 * For presentation to the Department of Environment
	 *
	 * Author: Bryan Carty
	 *
	 * @param year the year we would like to see tax values for
	 * @return returns a conveneient string format representation of the tax with some key details
	 */
	public String managementFormat(int year) {
		return propOwner.getName() + ", Tax value during year: \u20AC" + getValue(year) + ", Property: " + prop.format();
	}

	/**
	 *
	 * General String format of a Tax.
	 *
	 * Author: Jonathan Falvey
	 *
	 * @return returns an apporpriate string format representation depending on whether the tax has been paid or not.
	 */
	public String toString() {
		String temp = "";
		if (paid) {
			temp = yearDue + ", Paid: " + paid + ", Amount paid: \u20AC" + String.format("%.2f",valueWhenPaid);
		} else {
			temp = yearDue + ", Paid: " + paid + ", Amount to be paid: \u20AC" + String.format("%.2f",getCurrentValue());
		}
		return temp;
	}

	/**
	 *
	 * summarises key information about the Tax for writing to a csv file
	 *
	 * @return csv line string
	 */
	public String writeFormat() {
		if (paid) {
			return "Tax," + taxDue + "," + yearDue + "," + paid + "," + valueWhenPaid + "," + yearPaid +"\n";
		}
		return "Tax," + taxDue + "," + yearDue +"\n";
	}
}
