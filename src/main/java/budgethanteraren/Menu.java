package budgethanteraren;

public class Menu {
    static void main() {
        welcome();
        boolean run = true;
        do{
            IO.println("------------------------------------------------");
            IO.println("1. Lagg till transaktion");
            IO.println("2. Visa alla transaktioner");
            IO.println("3. Visa saldo och sammanstallning per kategori");
            IO.println("4. Filtrera transaktioner");
            IO.println("5. Spara till fil");
            IO.println("e. Avsluta");
            IO.println("------------------------------------------------");

            String choice = IO.readln("Vänligen välj ett av alternativen: ");

            switch (choice) {
                case "1" -> IO.println("Not yet implemented");
                case "2" -> IO.println("Not yet implemented");
                case "3" -> IO.println("Not yet implemented");
                case "4" -> IO.println("Not yet implemented");
                case "5" -> IO.println("Not yet implemented");
                case "E","e" -> run = false;
                default -> IO.println("Ogiltigt val, försök igen.");

            }
        }while(run);

    }
    static void welcome() {
        IO.println("------------------------------------------");
        IO.println("Välkommen till Budgethanteraren!");
        IO.println("------------------------------------------");
    }
}

