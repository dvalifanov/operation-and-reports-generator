import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {
    private final LocalDateTime operationDate;
    private final int shopId;
    private final int operationNumber;
    private final int operationAmount;

    Operation(LocalDateTime operationDate, int shopId, int operationNumber, int operationAmount) {
        this.operationDate = operationDate;
        this.shopId = shopId;
        this.operationNumber = operationNumber;
        this.operationAmount = operationAmount;
    }

    LocalDateTime getOperationDate() {
        return operationDate;
    }

    int getShopId() {
        return shopId;
    }

    int getOperationAmount() {
        return operationAmount;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationDate=" + operationDate +
                ", shopId=" + shopId +
                ", operationNumber=" + operationNumber +
                ", operationAmount=" + operationAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return operationNumber == operation.operationNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationNumber);
    }
}
