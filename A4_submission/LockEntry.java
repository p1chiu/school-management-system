import java.util.Set;
import java.util.HashSet;


public class LockEntry {
    private final LockType lockType;
    private final int recordId;
    private final Set<Integer> transactionIds = new HashSet<>();

    private Integer value = null;

    public enum LockType {
        SHARED, EXCLUSIVE
    }

    public LockEntry(LockType lockType, int recordId, Integer value) {
        this.lockType = lockType;
        this.recordId = recordId;
        this.value = value;
    }

    public LockEntry(LockType lockType, int recordId) {
        this.lockType = lockType;
        this.recordId = recordId;
    }

    // Getters
    public LockType getLockType() {
        return this.lockType;
    }

    public int getRecordId() {
        return this.recordId;
    }

    public Integer value(){
        return this.value;
    }

    public static LockEntry read(int recordId) {
        return new LockEntry(LockType.SHARED, recordId, null);
    }

    public static LockEntry write(int recordId, int value) {
        return new LockEntry(LockType.EXCLUSIVE, recordId, value);
    }
    public Set<Integer> getTransactionIds() {
        return transactionIds;
    }
}
