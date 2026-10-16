package budgethanteraren;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BudgetManagement {
    static Repository<Transaction> repository = new Repository<>();

    void main() {
        welcome();
        Menu.menu();

        IO.println(repository.getTransactions());

    }
    static Transaction inserNewTransactionIncome() {
         LocalDate date = returndate();
         Category type = returnCategory();
         BigDecimal amount = new BigDecimal("1000");
         TransactionType  transactionType = TransactionType.INCOME;
         return new Transaction(date,type,amount,transactionType);
    }
    static Transaction inserNewTransactioExpense() {
        LocalDate date = returndate();
        Category type = returnCategory();
        BigDecimal amount = new BigDecimal("1000");
        TransactionType  transactionType = TransactionType.EXPENSE;
        return new Transaction(date,type,amount,transactionType);
    }

    private static Category returnCategory() {
        return Category.HOME_AND_LIVING;
    }

    static LocalDate returndate(){

        while (true){
            IO.println("1. För dagens datum");
            IO.println("2. För att ange annat datum");

            String choice = IO.readln("Välj ett av alternativen: ");

            switch (choice){
                case "1" -> {
                    return LocalDate.now();
                }
                case "2" -> {
                    return dateInputFromUserWhenNotNow();
                }
                default -> IO.println("Ogiltigt val, försök igen.");
            }
        }
    }

    private static LocalDate dateInputFromUserWhenNotNow() {
        LocalDate date = null;

        while (date == null) {
           String input = IO.readln("Ange datum (ÅÅÅÅ-MM-DD): ");

            try {
                // Försöker tolka texten som ett datum
                date = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                // Fångar upp felaktiga format eller ogiltiga datum
                System.out.println("Felaktigt format eller ogiltigt datum. Försök igen.");
            }
        }
        return date;
    }

    static void welcome() {
        IO.println("------------------------------------------");
        IO.println("Välkommen till Budgethanteraren!");
        IO.println("------------------------------------------");
    }
}
