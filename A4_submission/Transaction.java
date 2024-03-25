import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class Transaction {
    private List<Operation> operations = new ArrayList<>();
    private int transactionId;

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public Transaction(int transactionId) {
        this.transactionId = transactionId;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    // Getter for the transaction ID
    public int getTransactionId() {
        return transactionId;
    }

}
