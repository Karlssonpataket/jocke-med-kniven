package budgethanteraren;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void addTransactionShouldbeSize1() {
        Repository<Transaction> repository = new Repository<>();
        repository.addTransaction(new Transaction
                (LocalDate.now(),Category.HOME_AND_LIVING,new BigDecimal(100),TransactionType.INCOME));

        assertEquals(1,repository.getTransactions().size());

    }

}