import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * Class created to represent the Command Line Interface for the Management Menu
 *
 * Author: Jonathan Falvey
 *
 */
public class ManagementMenu {
    Scanner scan = new Scanner(System.in);
    PropertyOwnersList owners = new PropertyOwnersList();

    String pathToData = "..\\resources\\sampleData.csv";
    File sampleData = new File(pathToData);

    String pathToWorkingData = "..\\resources\\workingData.csv";
    FileWriter workingData = new FileWriter(pathToWorkingData);

    int yearTaxLastCalculated;

    public ManagementMenu() throws IOException {
    }

    /**
     *
     * Method that loops while user issues different commands
     *
     * Author: Jonathan Falvey
     *
     */
    public void run() throws IOException {
        //if sample data exists, read it in
        if (sampleData.exists()) {
            readData();
        }

        //if the year has changed, calculate all taxes
        if (LocalDate.now().getYear() > yearTaxLastCalculated) {
            owners.calculateAllTax();
            yearTaxLastCalculated = LocalDate.now().getYear();
        }

        boolean more = true;

        while(more) {
            System.out.println("P)roperty tax payment data     O)verdue property tax     S)tatistics     E)xperiment with tax rates      Q)uit");
            String command = scan.nextLine().toUpperCase();

            //user selects to view property tax payment data
            if (command.equals("P")) {
                System.out.println("P)roperty    O)wner");
                command = scan.nextLine().toUpperCase();

                //user selects to view tax on property
                if (command.equals("P")) {
                    System.out.println("Select property: ");
                    Property choice = getPropertyChoice(owners.getAllProperties());
                    owners.getTaxPaymentData(choice);
                }
                //user selects to view tax on owner
                else if (command.equals("O")) {
                    System.out.println("Select property owner: ");
                    PropertyOwner choice = getOwnerChoice(owners.getOwners());
                    owners.getTaxPaymentData(choice);
                }
            }
            //user selects to view overdue property tax data from a particular year
            else if (command.equals("O")) {
                System.out.println("Please enter the year you want data from: ");
                int year = Integer.parseInt( scan.nextLine());
                System.out.println("Get data from : A)ll property owners    P)roperty owners from particular area");
                command = scan.nextLine();

                //user selects to view data from all owners
                if (command.equalsIgnoreCase("A")) {
                    owners.getAllOverdueTax(year);
                }
                //user selects to view data from owners in a particular area
                else if(command.equalsIgnoreCase("P")) {
                    System.out.println("Enter area to check (eircode format): ");
                    String area = scan.nextLine();

                    owners.getAllOverdueTax(year, area);
                }
            }
            //user selects to view tax statistics for a particular area
            else if (command.equals("S")) {
                System.out.println("Enter area you would like to receive statistics for (eircode format): ");
                String area =  scan.nextLine();

                System.out.println(owners.getStatistics(area));
            }
            //user selects to experiment with custom tax calculation
            else if (command.equals("E")) {
                System.out.println("Enter a Value for the flat charge: ");
                double flat = Double.parseDouble(scan.nextLine());

                System.out.println("Enter a value for the rate for market values between 150,000 and 400,000 (0.xx): ");
                double rate1 = Double.parseDouble(scan.nextLine());

                System.out.println("Enter a value for the rate for market values between 400,000 and 650,000 (0.xx): ");
                double rate2 = Double.parseDouble(scan.nextLine());

                System.out.println("Enter a value for the rate for market values above 650,000 (0.xx): ");
                double rate3 = Double.parseDouble(scan.nextLine());

                System.out.println("Enter Values for location tax (city, large town, small town, village, countryside)");
                String location = scan.nextLine();

                System.out.println("Enter a value for Non principle private residence tax: ");
                double ppr = Double.parseDouble(scan.nextLine());

                owners.compareRevenue(flat, rate1, rate2, rate3, location, ppr);
            }
            //user selects to quit
            else if (command.equals("Q")) {
                more = false;
                writeData();
            }
        }

    }

    /**
     *
     * Method for user to choose a property from a list of available options
     *
     * Author: Jonathan Falvey
     *
     * @param choices the arraylist to fetch options from
     * @return returns the property the user has chosen
     */
    private Property getPropertyChoice(ArrayList<Property> choices)
    {
        if (choices.size() == 0) {
            System.out.println("No Options");
            return null;
        }
        while (true)
        {
            char c = 'A';
            for (Property choice : choices)
            {
                System.out.println(c + ") " + choice.format());
                c++;
            }
            String input = scan.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < choices.size())
                return choices.get(n);
        }
    }

    /**
     *
     * Method for user to choose a Property Owner from a list of available options
     *
     * Author: Jonathan Falvey
     *
     * @param choices an arraylist containing all the options
     * @return returns the owner chosen by the user
     */
    private PropertyOwner getOwnerChoice(ArrayList<PropertyOwner> choices)
    {
        if (choices.size() == 0) {
            System.out.println("No Options");
            return null;
        }
        while (true)
        {
            char c = 'A';
            for (PropertyOwner choice : choices)
            {
                System.out.println(c + ") " + choice.format());
                c++;
            }
            String input = scan.nextLine();
            int n = input.toUpperCase().charAt(0) - 'A';
            if (0 <= n && n < choices.size())
                return choices.get(n);
        }
    }

    /**
     *
     * method that reads in data from a csv file for use by program
     *
     * @throws IOException potential exception if no file is found
     */
    private void readData() throws IOException {
        Scanner reader = new Scanner(sampleData);
        String row;
        PropertyOwner tempOwner = null;
        Property tempProperty = null;
        Tax tempTax;
        Payment tempPayment;

        while (reader.hasNext()) {
            row = reader.nextLine();
            String[] values = row.split(",");

            if (values[0].equalsIgnoreCase("Owner")) {
                tempOwner = new PropertyOwner(values[1], new Address(values[2], values[3], values[4]), values[5], Double.parseDouble(values[6]), Double.parseDouble(values[7]), values[8]);
                owners.registerOwner(tempOwner);
            }
            else if(values[0].equalsIgnoreCase("Property")) {
                tempProperty = new Property(tempOwner, new Address(values[1], values[2], values[3]), values[4], Double.parseDouble(values[5]), values[6], Boolean.parseBoolean(values[7]));
                tempOwner.registerProperty(tempProperty);
            }
            else if (values[0].equalsIgnoreCase("Tax")) {
                if (values.length < 4) {
                    tempTax = new Tax(tempOwner, tempProperty, Double.parseDouble(values[1]), Integer.parseInt(values[2]));
                } else {
                    tempTax = new Tax(tempOwner, tempProperty, Double.parseDouble(values[1]), Integer.parseInt(values[2]), Boolean.parseBoolean(values[3]), Double.parseDouble(values[4]), Integer.parseInt(values[5]));
                }
                tempProperty.addTax(tempTax);
            }
            else if (values[0].equalsIgnoreCase("Payment")) {
                tempPayment = new Payment(tempOwner,tempProperty,Integer.parseInt(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]));
                tempOwner.addPayment(tempProperty, tempPayment);
                tempProperty.addPayment(tempPayment);
            }
            else if (values[0].equalsIgnoreCase("Year")) {
                yearTaxLastCalculated = Integer.parseInt(values[1]);
            }


        }
        reader.close();
    }

    /**
     *
     * Method that writes important records to a csv file for later use by program
     *
     * @throws FileNotFoundException potential exception if file not found
     */
    private void writeData() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(workingData);

        out.write("Year," + yearTaxLastCalculated + "\n");

        for (PropertyOwner owner : owners.getOwners()) {
            out.write(owner.writeFormat());

            for (Property property : owner.getProperties()) {
                out.write(property.writeFormat());

                for (Tax tax : property.getTaxes()) {
                    out.write(tax.writeFormat());
                }
                for (Payment payment : property.getPayments()) {
                    out.write(payment.writeFormat());
                }
            }

        }
        out.close();
        System.out.println("Data Saved! Now exiting");
    }

}
