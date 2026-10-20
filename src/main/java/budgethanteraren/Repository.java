package budgethanteraren;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Repository<T> {
    private List<T> transactions = new ArrayList<>();

    public List<T> getTransactions() {
        return transactions;
    }

    public void addTransaction(T transaction) {
        transactions.add(transaction);
    }
    public List<T> returnsfilteredlist(Predicate<T> filter) {
        return transactions.stream()
                .filter(filter)
                .toList();
    }
    public List<T> returnssortedlist(Comparator<T> comparator) {
        return transactions.stream()
                .sorted(comparator)
                .toList();
    }

    public void clear() {
        this.transactions.clear();
    }
}
