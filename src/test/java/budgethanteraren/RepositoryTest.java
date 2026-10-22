package budgethanteraren;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void addTransactionShouldbeSize1() {
        Repository<Transaction> repository = new Repository<>();
        repository.addTransaction(new Transaction
                (LocalDate.now(),Category.HOME_AND_LIVING,new BigDecimal(100),TransactionType.INCOME));

        assertEquals(1,repository.getTransactions().size());

    }

    @Test
    void returnsfilteredlistShouldOnlyReturnMatchingTransactions() {
        Repository<Transaction> repository = new Repository<>();
        Transaction income = new Transaction
                (LocalDate.now(),Category.SALARY,new BigDecimal(25000),TransactionType.INCOME);
        Transaction expense1 = new Transaction
                (LocalDate.now(),Category.TRAVEL,new BigDecimal(500),TransactionType.EXPENSE);
        Transaction expense2 = new Transaction
                (LocalDate.now(),Category.AMUSEMENT,new BigDecimal(200),TransactionType.EXPENSE);
        repository.addTransaction(income);
        repository.addTransaction(expense1);
        repository.addTransaction(expense2);

        List<Transaction> result = repository.returnsfilteredlist(t -> t.type() == TransactionType.EXPENSE);

        assertEquals(List.of(expense1, expense2), result);
    }

    @Test
    void returnsfilteredlistShouldReturnEmptyListWhenNothingMatches() {
        Repository<Transaction> repository = new Repository<>();
        repository.addTransaction(new Transaction
                (LocalDate.now(),Category.SALARY,new BigDecimal(25000),TransactionType.INCOME));

        List<Transaction> result = repository.returnsfilteredlist(t -> t.category() == Category.TRAVEL);

        assertTrue(result.isEmpty());
    }

    @Test
    void returnsfilteredlistShouldNotModifyOriginalList() {
        Repository<Transaction> repository = new Repository<>();
        repository.addTransaction(new Transaction
                (LocalDate.now(),Category.SALARY,new BigDecimal(25000),TransactionType.INCOME));
        repository.addTransaction(new Transaction
                (LocalDate.now(),Category.TRAVEL,new BigDecimal(500),TransactionType.EXPENSE));

        repository.returnsfilteredlist(t -> t.type() == TransactionType.INCOME);

        assertEquals(2, repository.getTransactions().size());
    }

}