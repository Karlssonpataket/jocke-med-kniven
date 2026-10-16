package budgethanteraren;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(LocalDate date, Category category, BigDecimal amount,TransactionType type) {
}
