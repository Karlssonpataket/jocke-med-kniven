package budgethanteraren;

class Menu extends BudgetManagement{
      static void menu() {
        boolean run = true;
        do {
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
                case "1" -> subMenuWichTypeOfTransaction();
                case "2" -> printTransactions();
                case "3" -> IO.println(returnBalance());
                case "4" -> IO.println("Not yet implemented");
                case "5" -> IO.println("Not yet implemented");
                case "E", "e" -> run = false;
                default -> IO.println("Ogiltigt val, försök igen.");

            }

        } while (run);
    }
     static void subMenuWichTypeOfTransaction() {
        Boolean run = true;
        do{
            IO.println("1. för att lägga till en inkomst");
            IO.println("2. för att lägga till en utgift");

            String choice = IO.readln("välj ett av alternativen: ");

            switch (choice) {
                case "1" -> {
                    inserNewTransactionIncome();
                    run = false;
                }
                case "2" -> {
                    inserNewTransactioExpense();
                    run = false;
                }
                default -> IO.println("Ogiltigt val, försök igen.");
            }
        }while(run);
    }
}