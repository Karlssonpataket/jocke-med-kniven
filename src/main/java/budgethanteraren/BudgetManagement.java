package budgethanteraren;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class BudgetManagement {
    private static Repository<Transaction> repository = new Repository<>();
    private static Logger logger = Logger.getLogger(BudgetManagement.class.getName());
    private static Path filePath;
    void main() throws IOException {

        String homeFolder = System.getProperty("user.home");
        Path homePath = Path.of(homeFolder);
        filePath = homePath.resolve("Budgethanteraren", "Transaktioner.csv");

        logger.setLevel(Level.INFO);
        FileHandler fileHandler = new FileHandler("application.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);

        if (!Files.exists(filePath))
            createFile();

        readFromFile();

        welcome();
        Menu.menu();



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
                    logger.info("Parsing succesfull to BigDecimal > 0: " + test);
                    return new BigDecimal(test);
                }
            } catch (NotABigDecimalAboveZero e) {
                logger.warning("Parsing failed NotABigDecimalAboveZero: " + test);
                IO.println("Ogiltigt belopp. Talet måste vara större än 0 (Noll).");
                test = inputFromUser();

            }catch (NumberFormatException e) {
                logger.warning("Parsing failed NumberFormatException: " + test);
                IO.println("Ogiltigt belopp. Endast siffror, inga bokstäver eller specialtecken.");
                test = inputFromUser();
            }
        }
    }
    public static String inputFromUser() {// I added this class so I could test the methods that uses user input.
        return IO.readln("Ange belopp: ");
    }

    static Boolean isValidBigDecimal(String inputFromUser) {
        if (Double.parseDouble(inputFromUser) <= 0){
            throw new NotABigDecimalAboveZero("Beloppet måste vara större än 0 (noll).");
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

    public static void printTransactions(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("Inga transaktioner att visa.");
            return;
        }
        String line = "----------------------------------------------------";

        IO.println(line);
        IO.println(" Datum\t\tKategori \t\t Typ \t\t Belopp");
        IO.println(line);

        String text = transactions.stream()
                .map(t -> t.date() + "  " + t.category().getDisplayName()+ "  " + t.type().getDisplayName()
                + " \t " + t.amount() + " kr")
                .collect(Collectors.joining("\n"));
        IO.println(text);
        IO.println(line);
    }

    static void printAllTransactions() {
        printTransactions(repository.getTransactions());
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

    public static void sortByIncome(){
        printTransactions( repository.returnsfilteredlist((n -> n.type().equals(TransactionType.INCOME))));

    }
    public static void sortByExpense(){
        printTransactions( repository.returnsfilteredlist((n -> n.type().equals(TransactionType.EXPENSE))));
    }
    public static void sortByCategory(Category category){
        printTransactions(repository.returnsfilteredlist((n -> n.category().equals(category))));
    }
    public static void sortBydate(){
        printTransactions(repository.returnssortedlist(Comparator.comparing(Transaction::date)).reversed());
    }
    static void createFile() throws IOException {
        logger.info("Trying to create file");


        try {
            Files.createFile(filePath);

        } catch (FileAlreadyExistsException e) {
            logger.info("File " + filePath + " already exists");
        } catch (IOException e) {
            logger.warning("Error creating file: " + e.getMessage());
            IO.println("Error creating file: " + e.getMessage());
        }


    }
    static void readFromFile(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        try {
            Files.lines(filePath)
                    .map(line -> line.trim().split(","))
                    .map(transactioninfo -> new Transaction(
                            LocalDate.parse(transactioninfo[0],formatter),
                            Category.valueOf(transactioninfo[1]),
                            new BigDecimal(transactioninfo[2]),TransactionType.valueOf(transactioninfo[3]))).forEach(repository::addTransaction);

        } catch (IOException e) {
            logger.warning("Error while trying to read file: " + e.getMessage() );
            IO.println("Error reading file: ");
            optionCreateNewFile();
            return;
        }
        logger.info("Reading from file successful");
    }

    static void optionCreateNewFile() {
        String choice = IO.readln("Do you want to create a new file? OBS: IF YES ALL DATA WILL BE LOST! (y/n)");
       do{
           switch (choice) {
               case "y", "Y" -> {
                   try {

                       Files.write(filePath, Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
                   } catch (IOException e) {
                       logger.warning("Error while trying to erase file: " + e.getMessage());
                   }
               }
               case "n", "N" -> {
                   return;
               }
               default -> IO.println("Invalid choice");
           }
       }while(true);
    }


    static void saveToFile() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            List<Transaction> transactionsToSave = repository.getTransactions();

            List<String> lines = transactionsToSave.stream()
                    .map(transactioninfo -> String.format("%s,%s,%s,%s",
                            transactioninfo.date().format(formatter),
                            transactioninfo.category().name(),
                            transactioninfo.amount().toString(),
                            transactioninfo.type().name()))
                    .collect(Collectors.toList());


            Files.write(filePath, lines);

        } catch (IOException e) {
           logger.warning("Error while trying to write to file: " + e.getMessage() + e.getStackTrace());

        }
        logger.info("The file was saved successfully");

    }
}


