import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LockManager {
    private final Map<Integer, LockEntry> lockTable = new HashMap<>();

    public synchronized boolean acquireLock(int recordId, int transactionId, LockEntry.LockType requestedLockType) {
        LockEntry currentLock = lockTable.get(recordId);

        // If there's no current lock on the record, or if the lock is shared and the requested lock is also shared,
        // the lock can be granted.
        if (currentLock == null || (currentLock.getLockType() == LockEntry.LockType.SHARED && requestedLockType == LockEntry.LockType.SHARED && currentLock.getTransactionIds().contains(transactionId))) {
            // Update the lock table with the new lock.
            if(currentLock == null) currentLock = new LockEntry(requestedLockType, recordId);
            currentLock.getTransactionIds().add(transactionId); // Add transactionId to current lock
            lockTable.put(recordId, currentLock);
            System.out.println("Lock granted: " + requestedLockType + " for recordId: " + recordId + " to transactionId: " + transactionId);
            return true;
        }
        // If the requested lock cannot be granted immediately, return false.
        System.out.println("Lock wait: " + requestedLockType + " for recordId: " + recordId + " by transactionId: " + transactionId);
        return false;
    }

    // Method to release locks held by a transaction at the end of its execution
    public synchronized void releaseLocks(int transactionId) {
        lockTable.entrySet().removeIf(entry -> entry.getValue().getTransactionIds().remove(transactionId));
        System.out.println("Locks released by transactionId: " + transactionId);
    }
}
