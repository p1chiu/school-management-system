import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class LockProtocol {
    public enum LockType {
        SHARED, EXCLUSIVE
    }
    private static LockManager lockManager = new LockManager();

    // Main method that serves as the entry point for the Java application
    public static void main(String[] args) {
        LockProtocol lockProtocol = new LockProtocol();

        // Transaction T1
        Transaction t1 = new Transaction(1);
        t1.addOperation(new Operation(1, 5));
        t1.addOperation(new Operation(2));
        t1.addOperation(new Operation(2, 3));
        t1.addOperation(new Operation(1));
        t1.addOperation(new Operation());
        // Transaction T2
        Transaction t2 = new Transaction(2);
        t2.addOperation(new Operation(1));
        t2.addOperation(new Operation(1, 2));
        t2.addOperation(new Operation());


        Transaction deliverableT1 = new Transaction(3);
        deliverableT1.addOperation(new Operation(1, 5));
        deliverableT1.addOperation(new Operation());
        Transaction deliverableT2 = new Transaction(4);
        deliverableT2.addOperation(new Operation(9));
        deliverableT2.addOperation(new Operation(7));
        deliverableT2.addOperation(new Operation());
        Transaction deliverableT3 = new Transaction(5);
        deliverableT3.addOperation(new Operation(1));
        deliverableT3.addOperation(new Operation());

        Transaction[] transactions = {t1, t2};
        Transaction[] deliverableTransactions = {deliverableT1, deliverableT2, deliverableT3};

//      lockProtocol.acquireLock(recordId, transactionId, requestedLockType);

        traverseTransactions(transactions, 5);
        System.out.println("========== Completed example Schedule ==========");

        traverseTransactions(deliverableTransactions, 3);
        System.out.println("========== Completed deliverable Schedule ==========");

    }

    public static void traverseTransactions(Transaction[] transactions, int rounds){
        // iterate through Schedules
        for(int roundRobinIndex = 0; roundRobinIndex < rounds; roundRobinIndex++){
            for(Transaction i : transactions){
                try{
                    Operation roundRobinOperation = i.getOperations().get(roundRobinIndex);
                    if (roundRobinOperation.getType() == Operation.OperationType.COMMIT) {
                        lockManager.releaseLocks(i.getTransactionId()); // Release locks when committing
                    } else {
                        // Attempt to acquire lock before operation execution
                        boolean lockAcquired = lockManager.acquireLock(roundRobinOperation.getRecordId(), i.getTransactionId(),
                                roundRobinOperation.getType() == Operation.OperationType.READ ? LockEntry.LockType.SHARED : LockEntry.LockType.EXCLUSIVE);
                        if (lockAcquired) {
                            // Execute the operation here, if needed
                        }
                    }
                }
                catch (IndexOutOfBoundsException e){
                }
            };
            System.out.println("Completed round robin: " + (roundRobinIndex + 1));
        };
    };
}