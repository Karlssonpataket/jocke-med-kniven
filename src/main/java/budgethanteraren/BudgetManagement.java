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
         BigDecimal amount = returnAmount();
         TransactionType  transactionType = TransactionType.INCOME;
         return new Transaction(date,type,amount.abs(),transactionType);
    }

    private static BigDecimal returnAmount() {
        while (true) {
            try {
                String amountInput = IO.readln("Ange belopp: ");
                return new BigDecimal(amountInput);
            } catch (NumberFormatException e) {
                IO.println("Ogiltigt belopp. Försök igen.");
            }
        }
    }

    static Transaction inserNewTransactioExpense() {
        LocalDate date = returndate();
        Category type = returnCategory();
        do{
            BigDecimal amount = returnAmount();
            if (checkIfBalcaneGoesUnderZero(amount)) //abs() ser till att det inte blir ett negativt tal.
                return new Transaction(date,type,amount.abs(),TransactionType.EXPENSE);
            else
                IO.println("Beloppet kan inte överstiga saldot på kontot.");
        }while(true);
    }

   static boolean checkIfBalcaneGoesUnderZero(BigDecimal bigDecimal) {
        BigDecimal sum = repository.getTransactions().stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
       return bigDecimal.subtract(sum).compareTo(BigDecimal.ZERO) <= 0;
   }

    static Category returnCategory() {
        while (true) {
            IO.println("1. Hem och hushåll");
            IO.println("2. Sparande");
            IO.println("3. Nöje");
            IO.println("4. Lön");
            IO.println("5. Resor");
            IO.println("6. Annat");

            String choice = IO.readln("Välj kategori: ");

             switch (choice) {
                 case "1" -> {
                     return Category.HOME_AND_LIVING;
                 }
                 case "2" -> {
                     return Category.SAVINGS;
                 }
                 case "3" -> {
                     return Category.AMUSEMENT;
                 }
                 case "4" -> {
                     return Category.SALARY;
                 }
                 case "5" -> {
                     return Category.TRAVEL;
                 }
                 case "6" -> {
                     return Category.OTHER;
                 }
                 default -> IO.println("Ogiltigt val, försök igen.");
            };
        }
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
                IO.println("Felaktigt format eller ogiltigt datum. Försök igen.");
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
