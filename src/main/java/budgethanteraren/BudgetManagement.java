package budgethanteraren;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
        BigDecimal amount = returnAmount(inputFromUser());
        TransactionType transactionType = TransactionType.INCOME;
        repository.addTransaction(new Transaction(date, type, amount, transactionType));
    }

    static BigDecimal returnAmount(String inputFromUser) {
        String test = inputFromUser;

        while (true) {
            try {
                if (isValidBigDecimal(test)) {
                    return new BigDecimal(test);
                }
            } catch (NotAPositivBigDecimal e) {
                IO.println("Ogiltigt belopp. Talet måste vara större än 0 (Noll).");
                test = inputFromUser();
            }catch (NumberFormatException e) {
                IO.println("Ogiltigt belopp. Endast siffror, inga bokstäver eller specialtecken.");
                test = inputFromUser();
            }
        }
    }
    public static String inputFromUser() {// I added this class so I could test the methods that uses user input.
        return IO.readln("Ange belopp: ");
    }

    static Boolean isValidBigDecimal(String inputFromUser) {
        if (Double.parseDouble(inputFromUser) < 0){
            throw new NotAPositivBigDecimal("Ogiltigt belopp. Försök igen.");
        } else if (Double.parseDouble(inputFromUser) > 0)
            return true;


        return false;
    }
    static void inserNewTransactioExpense() {
        if (returnBalance().compareTo(BigDecimal.ZERO) == 0) {
            IO.println("Du har inget saldo på kontot. Försök igen.");
            return;
        }
        LocalDate date = returndate();
        Category type = returnCategory();
        do {
            BigDecimal amount = returnAmount(inputFromUser());
            if (isBalanceAboveOrEqualToZero(amount)) {//kollar så att summan inte överstiger saldot på kontot.
                repository.addTransaction(new Transaction(date, type, amount, TransactionType.EXPENSE));
                break;
            } else
                IO.println("Beloppet kan inte överstiga saldot på kontot.");
        } while (true);
    }

    static boolean isBalanceAboveOrEqualToZero(BigDecimal bigDecimal) {

        return bigDecimal.subtract(returnBalance()).compareTo(BigDecimal.ZERO) <= 0;
    }

    static BigDecimal returnBalance() {
        BigDecimal sumIncome = repository.getTransactions().stream()
                .filter(n -> n.type() == TransactionType.INCOME)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sumExpense = repository.getTransactions().stream()
                .filter(n -> n.type() == TransactionType.EXPENSE)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sumIncome.subtract(sumExpense);
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
            }
            ;
        }
    }

    static LocalDate returndate() {

        while (true) {
            IO.println("1. För dagens datum");
            IO.println("2. För att ange annat datum");

            String choice = IO.readln("Välj ett av alternativen: ");

            switch (choice) {
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

    public static void printTransactions() {
        repository.getTransactions().forEach(IO::println);
    }

    static void welcome() {
        IO.println("------------------------------------------");
        IO.println("Välkommen till Budgethanteraren!");
        IO.println("------------------------------------------");
    }

    public static void addTransaction(Transaction transaction) {
        repository.addTransaction(transaction);
    }

    public static void clearTransactions() {
        repository.clear();
    }

    public static List<Transaction> filterTransactions(Category cat) {

        return repository.getTransactions().stream()
                .filter(transaction -> transaction.category().equals(cat))
                .toList();
    }

    public static void printBalanceAndTransactions() {
        IO.println("------------------------------------------------");
        IO.println("Hem och hushåll: " + returnBalancePerCategory(filterTransactions(Category.HOME_AND_LIVING)));
        IO.println("Nöje: " + returnBalancePerCategory(filterTransactions(Category.AMUSEMENT)));
        IO.println("Resa: " + returnBalancePerCategory(filterTransactions(Category.TRAVEL)));
        IO.println("Övrigt: " + returnBalancePerCategory(filterTransactions(Category.OTHER)));
        IO.println("Lön: " + returnBalancePerCategory(filterTransactions(Category.SALARY)));
        IO.println("Sparande: " + returnBalancePerCategory(filterTransactions(Category.SAVINGS)));
        IO.println("-------------------------------------------------");
        IO.println("Total Saldo: " + returnBalance());
        IO.println("-------------------------------------------------");
    }

    public static BigDecimal returnBalancePerCategory(List<Transaction> list) {
        BigDecimal sumIncome = list.stream()
                .filter(n -> n.type() == TransactionType.INCOME)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sumExpense = list.stream()
                .filter(n -> n.type() == TransactionType.EXPENSE)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sumIncome.subtract(sumExpense);
    }
}