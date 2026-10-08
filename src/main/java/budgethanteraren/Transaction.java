package budgethanteraren;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(LocalDate date, String  Category, BigDecimal amount,TransactionType type) {
}
