import java.time.LocalDate;

public class Tax {
	int yearDue;
	double taxDue;
	PropertyOwner propOwner;
	Property prop;
	boolean paid = false;
	double valueWhenPaid = 0;
	int yearPaid = 9999;
	
	public Tax() {}

	//when passing date to this class use LocalDate date = LocalDate.of(2020, 1, 8)
	public Tax(PropertyOwner propOwner, Property prop, double taxDue) {
		this.propOwner = propOwner;
		this.prop = prop;
		this.taxDue = taxDue;

		yearDue = LocalDate.now().getYear(); //tax must be paid this year.
	}
	
	public int getYearDue() {
		return this.yearDue;
	}
	
	public void setYearDue(int yearDue) {
		this.yearDue = yearDue;
	}
	
	public Property getProperty() {
		return this.prop;
	}

	public void taxPaid(double amount) {
		paid = true;
		valueWhenPaid = amount;
	}

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

	public String format() {
		return yearDue + ", Current tax due: " + getCurrentValue();
	}

	public String managementFormat(int year) {
		return propOwner.getName() + ", Tax value during year: " + getValue(year) + ", Property: " + prop.format();
	}


	public String toString() {
		String temp = "";
		if (paid) {
			temp = yearDue + ", Paid: " + paid + ", Amount paid: " + valueWhenPaid;
		} else {
			temp = yearDue + ", Paid: " + paid + "Amount to be paid: " + getCurrentValue();
		}
		return temp;
	}

}




