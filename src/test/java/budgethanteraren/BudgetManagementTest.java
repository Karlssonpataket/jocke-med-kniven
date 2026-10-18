package budgethanteraren;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetManagementTest {

    @Test
    void returnBalanceShouldReturnCorrectBalance() {
         BudgetManagement.addTransaction(new Transaction
                    (LocalDate.now(),Category.HOME_AND_LIVING,new BigDecimal(100),TransactionType.INCOME));
         BudgetManagement.addTransaction(new Transaction
                    (LocalDate.now(),Category.HOME_AND_LIVING,new BigDecimal(50),TransactionType.EXPENSE));

            assertEquals(new BigDecimal(50),BudgetManagement.returnBalance());
        }


    @Test
    void inserNewTransactioExpense1() {
    }

    @Test
    void checkIfBalanceGoesUnderZero1() {
    }

    @Test
    void returnBalance1() {

    }
}