package budgethanteraren;

import java.util.ArrayList;
import java.util.List;

public class Repository<T> {
    private List<T> transactions = new ArrayList<>();

    public List<T> getTransactions() {
        return transactions;
    }

    public void addTransaction(T transaction) {
        transactions.add(transaction);
    }



    public void clear() {
        this.transactions.clear();
    }
}
