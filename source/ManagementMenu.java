import java.util.ArrayList;
import java.util.Scanner;

public class ManagementMenu {
    Scanner scan = new Scanner(System.in);

    public void run() {
        PropertyOwnersList owners = new PropertyOwnersList();
        boolean more = true;


        while(more) {
            System.out.println("P)roperty tax payment data     O)verdue property tax     S)tatistics     E)xperiment with tax rates      Q)uit");
            String command = scan.nextLine().toUpperCase();


            if (command.equals("P")) {
                System.out.println("P)roperty    O)wner");
                command = scan.nextLine().toUpperCase();

                if (command.equals("P")) {
                    System.out.println("Select property: ");
                    Property choice = getPropertyChoice(owners.getAllProperties());
                    owners.getTaxPaymentData(choice);
                }
                else if (command.equals("O")) {
                    System.out.println("Select property owner: ");
                    PropertyOwner choice = getOwnerChoice(owners.getOwners());
                    owners.getTaxPaymentData(choice);
                }
            }
            else if (command.equals("O")) {

            }
            else if (command.equals("S")) {

            }
            else if (command.equals("E")) {

            }
            else if (command.equals("Q")) {
                more = false;
            }
        }

    }

    private Property getPropertyChoice(ArrayList<Property> choices)
    {
        if (choices.size() == 0) return null;
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

    private PropertyOwner getOwnerChoice(ArrayList<PropertyOwner> choices)
    {
        if (choices.size() == 0) return null;
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
}
