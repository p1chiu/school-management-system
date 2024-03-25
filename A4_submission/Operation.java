public class Operation {
    public enum OperationType {
        READ, WRITE, COMMIT
    }

    private final Integer recordId;
    private final Integer value;
    private final OperationType type;

    public Operation(int recordId, int value) {
        this.recordId = recordId;
        this.value = value;
        this.type = OperationType.WRITE;
    }

    // Constructor for read operations
    public Operation(int recordId) {
        this.recordId = recordId;
        this.value = null;
        this.type = OperationType.READ;
    }

    public Operation() {
        this.recordId = null;
        this.value = null;
        this.type = OperationType.COMMIT;
    }


    public OperationType getType() {
        return type;
    }

    public int getRecordId() {
        return recordId;
    }

    public Integer getValue() {
        return value;
    }
}

