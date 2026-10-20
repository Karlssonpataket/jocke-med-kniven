package budgethanteraren;

public class NotAPositivBigDecimal extends RuntimeException {

    public NotAPositivBigDecimal(String message) {
        super(message);
    }
}
