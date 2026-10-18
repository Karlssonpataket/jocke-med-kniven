package budgethanteraren;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BudgetManagement {
     private static Repository<Transaction> repository = new Repository<>();

    void main() {
        welcome();
        Menu.menu();

        IO.println(repository.getTransactions());

    }
    static void inserNewTransactionIncome() {
         LocalDate date = returndate();
         Category type = returnCategory();
         BigDecimal amount = returnAmount();
         TransactionType  transactionType = TransactionType.INCOME;
         repository.addTransaction( new Transaction(date,type,amount,transactionType));
    }

     static BigDecimal returnAmount() {
        while (true) {
            try {
                String amountInput = IO.readln("Ange belopp: ");
                if(Integer.parseInt(amountInput) > 0)
                    return new BigDecimal(amountInput);
                else
                    IO.println("Ogiltigt belopp. Försök igen.");
            } catch (NumberFormatException e) {
                IO.println("Ogiltigt belopp. Försök igen.");
            }
        }
    }

    static void inserNewTransactioExpense() {//TODO fixa så att man inte fastnar i en oändlig loop om man inte har nåt på kontot.
        LocalDate date = returndate();
        Category type = returnCategory();
        do{
            BigDecimal amount = returnAmount();
            if (checkIfBalanceGoesUnderZero(amount)) {//kollar så att summan inte överstiger saldot på kontot.
                repository.addTransaction(new Transaction(date, type, amount, TransactionType.EXPENSE));
                break;
            }
            else
                IO.println("Beloppet kan inte överstiga saldot på kontot.");
        }while(true);
    }
    static boolean checkIfBalanceGoesUnderZero(BigDecimal bigDecimal) {

       return bigDecimal.subtract(returnBalance()).compareTo(BigDecimal.ZERO) <= 0;
   }
   static BigDecimal returnBalance() {
        BigDecimal sumIncome = repository.getTransactions().stream()
                .filter( n -> n.type() == TransactionType.INCOME)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sumExpense = repository.getTransactions().stream()
                .filter( n -> n.type() == TransactionType.EXPENSE)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sumIncome.subtract(sumExpense) ;
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

    static LocalDate dateInputFromUserWhenNotNow() {
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
    public static void printTransactions(){
        repository.getTransactions().forEach(IO::println);
    }

    static void welcome() {
        IO.println("------------------------------------------");
        IO.println("Välkommen till Budgethanteraren!");
        IO.println("------------------------------------------");
    }
}
