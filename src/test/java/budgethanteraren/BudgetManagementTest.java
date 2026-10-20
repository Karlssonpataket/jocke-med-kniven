package budgethanteraren;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetManagementTest {

    @Test
    void returnBalanceShouldReturnCorrectBalance() {
        BudgetManagement.clearTransactions();

        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(100), TransactionType.INCOME));
        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(50), TransactionType.EXPENSE));

        assertEquals(new BigDecimal(50), BudgetManagement.returnBalance());
    }


    @Test
    void checkisBalanceAboveOrEqualToZeroReturnFalse() {
        BudgetManagement.clearTransactions();

        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(50), TransactionType.INCOME));

        assertFalse(BudgetManagement.isBalanceAboveOrEqualToZero(BigDecimal.valueOf(50.1)));

    }
    @Test
    void testWhenlistIsEmptyReturnZero() {
        BudgetManagement.clearTransactions();

        assertEquals(BigDecimal.ZERO, BudgetManagement.returnBalance());

    }

    @Test
    void checkifBalanceAboveorEqualtoZeroReturnTrueWhenZero() {
        BudgetManagement.clearTransactions();

        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(50.1), TransactionType.INCOME));

        assertTrue(BudgetManagement.isBalanceAboveOrEqualToZero(BigDecimal.valueOf(50.1)));

    }

    @Test
    void FiltertransactionsShouldReturntheRightCategoryofTransactions() {
        BudgetManagement.clearTransactions();
        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(100), TransactionType.INCOME));
        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.OTHER, new BigDecimal(50), TransactionType.EXPENSE));
        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(50), TransactionType.EXPENSE));

        assertEquals(2, BudgetManagement.filterTransactions(Category.HOME_AND_LIVING).size());

    }

    @Test
    void checkIfFilteredbalanceCountsAccurate() {
        BudgetManagement.clearTransactions();
        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(10.5), TransactionType.INCOME));
        BudgetManagement.addTransaction(new Transaction
                (LocalDate.now(), Category.HOME_AND_LIVING, new BigDecimal(50), TransactionType.EXPENSE));

        assertEquals(new BigDecimal(-39.5), BudgetManagement
                .returnBalancePerCategory(BudgetManagement
                        .filterTransactions(Category.HOME_AND_LIVING)));
    }

    @Test
    public void testifIsvalidBigDecimalTrowsNotPostitivBigDecimalException() {
        String userInput = "-1";

        assertThrows(NotABigDecimalHigherOrEqualToZero.class, () -> {
            BudgetManagement.isValidBigDecimal(userInput);
        });
    }

    @Test
    void testifIsvalidBigDecimalTrowsNumberformatException() {
        String userInput = "this is not a number";

        assertThrows(NumberFormatException.class, () -> {
            BudgetManagement.isValidBigDecimal(userInput);
        });
    }

    @Test
    void testifIsvalidBigDecimalreturnstrueWhenZero() {//Discovered an edge case with this test!
        String userInput = "0";

        assertTrue(BudgetManagement.isValidBigDecimal(userInput));
    }

    @Test
    void testReturnAmountWhenPositive() {
        String userInput = "1";

        assertTrue(BudgetManagement.isValidBigDecimal(userInput));
    }

}