package budgethanteraren;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
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
    @Test
    void returnssortedlistShouldSortByAmountAscending() {
        Repository<Transaction> repository = new Repository<>();
        Transaction big = new Transaction
                (LocalDate.now(),Category.SALARY,new BigDecimal(25000),TransactionType.INCOME);
        Transaction small = new Transaction
                (LocalDate.now(),Category.AMUSEMENT,new BigDecimal(200),TransactionType.EXPENSE);
        Transaction medium = new Transaction
                (LocalDate.now(),Category.TRAVEL,new BigDecimal(500),TransactionType.EXPENSE);
        repository.addTransaction(big);
        repository.addTransaction(small);
        repository.addTransaction(medium);

        List<Transaction> result = repository.returnssortedlist(Comparator.comparing(Transaction::amount));

        assertEquals(List.of(small, medium, big), result);
    }

    @Test
    void returnssortedlistShouldSortByDateDescending() {
        Repository<Transaction> repository = new Repository<>();
        Transaction oldest = new Transaction
                (LocalDate.of(2024,1,1),Category.SALARY,new BigDecimal(25000),TransactionType.INCOME);
        Transaction newest = new Transaction
                (LocalDate.of(2024,3,1),Category.TRAVEL,new BigDecimal(500),TransactionType.EXPENSE);
        Transaction middle = new Transaction
                (LocalDate.of(2024,2,1),Category.AMUSEMENT,new BigDecimal(200),TransactionType.EXPENSE);
        repository.addTransaction(oldest);
        repository.addTransaction(newest);
        repository.addTransaction(middle);

        List<Transaction> result = repository.returnssortedlist(Comparator.comparing(Transaction::date).reversed());

        assertEquals(List.of(newest, middle, oldest), result);
    }

    @Test
    void returnssortedlistShouldReturnEmptyListWhenRepositoryIsEmpty() {
        Repository<Transaction> repository = new Repository<>();

        List<Transaction> result = repository.returnssortedlist(Comparator.comparing(Transaction::amount));

        assertTrue(result.isEmpty());
    }

    @Test
    void returnssortedlistShouldNotModifyOriginalList() {
        Repository<Transaction> repository = new Repository<>();
        Transaction big = new Transaction
                (LocalDate.now(),Category.SALARY,new BigDecimal(25000),TransactionType.INCOME);
        Transaction small = new Transaction
                (LocalDate.now(),Category.TRAVEL,new BigDecimal(500),TransactionType.EXPENSE);
        repository.addTransaction(big);
        repository.addTransaction(small);

        repository.returnssortedlist(Comparator.comparing(Transaction::amount));

        assertEquals(List.of(big, small), repository.getTransactions());
    }
}