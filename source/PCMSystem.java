import java.util.Scanner;

public class PCMSystem {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Select: P)roperty owner     M)anagement     Q)uit");
        boolean more = true;

        while(more) {
            String command = scan.nextLine().toUpperCase();

            if (command.equals("P")) {
                more = false;
                PropertyOwnerMenu propmenu = new PropertyOwnerMenu();
                propmenu.run();
            } else if (command.equals("M")) {
                more = false;
                ManagementMenu manmenu = new ManagementMenu();
                manmenu.run();
            } else if (command.equals("Q")) {
                more = false;
            }
        }



    }
}
