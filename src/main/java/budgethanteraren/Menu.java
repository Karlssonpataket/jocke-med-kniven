package budgethanteraren;

import java.io.IOException;

class Menu extends BudgetManagement{ //extends Budgetmanagement so it can access its methods without
    //typing "BudgetManagement.".
      static void menu() throws IOException {
        boolean run = true;
        do {
            IO.println("------------------------------------------------");
            IO.println("1. Lägg till transaktion");
            IO.println("2. Visa alla transaktioner");
            IO.println("3. Visa saldo och sammanstallning per kategori");
            IO.println("4. Filtrera transaktioner");
            IO.println("5. Spara till fil");
            IO.println("e. Avsluta");
            IO.println("------------------------------------------------");

            String choice = IO.readln("Vänligen välj ett av alternativen: ");

            switch (choice) {
                case "1" -> subMenuWichTypeOfTransaction();
                case "2" -> printAllTransactions();
                case "3" -> printBalanceAndTransactions();
                case "4" -> submenuWhichKinOfFilter();
                case "5" -> saveToFile();
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
    private static void submenuWhichKinOfFilter() {
        boolean run = true;
        do{
            IO.println("1. Visa Transaktioner sorterat efter datum");
            IO.println("2. visa enbart utgifter");
            IO.println("3. visa enbart inkomster");
            IO.println("4. visa transaktioner efter kategori");

            String choice = IO.readln("välj ett av alternativen: ");

            switch (choice) {
                case "1" -> {
                    sortBydate();
                    run = false;
                }
                case "2" -> {
                    sortByExpense();
                    run = false;
                }case "3" -> {
                    sortByIncome();
                    run = false;
                }
                case "4" -> {
                    Category categorychoice = returnCategory();
                    sortByCategory(categorychoice);
                    run = false;
                }
                default -> IO.println("Ogiltigt val, försök igen.");
            }
        }while(run);
    }

}