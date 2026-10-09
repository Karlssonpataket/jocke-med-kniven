package budgethanteraren;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetManagement {
    static Repository<Transaction> repository = new Repository<>();

    void main() {
        welcome();
        Menu.menu();

        IO.println(repository.getTransactions());

    }
    static Transaction inserNewTransaction() {
         LocalDate date = LocalDate.now();
         String type =IO.readln("Ange kategori: ");
         BigDecimal amount = new BigDecimal("1000");
         TransactionType  transactionType = TransactionType.INCOME;
         return new Transaction(date,type,amount,transactionType);
    }

    static void welcome() {
        IO.println("------------------------------------------");
        IO.println("Välkommen till Budgethanteraren!");
        IO.println("------------------------------------------");
    }
}
