import java.time.LocalDate;
public class Tax {
	int yearDue;
	double taxDue;
	PropertyOwner propOwner;
	Property prop;
	
	public Tax() {
		
	}
	
	
	
	//when passing date to this class use LocalDate date = LocalDate.of(2020, 1, 8)
	public Tax(PropertyOwner propOwner, Property prop, double taxDue) {
		this.propOwner = propOwner;
		this.prop = prop;
		this.taxDue = taxDue;
		
		LocalDate timeNow = LocalDate.now();
		int currentYear = timeNow.getYear();
		this.yearDue = currentYear+1;
		
		
	}
	
	public int getYearDue() {
		return this.yearDue;
	}
	
	public void setYearDue(int yearDue) {
		this.yearDue = yearDue;
	}
}
