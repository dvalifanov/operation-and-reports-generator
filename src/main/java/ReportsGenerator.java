import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;


public class ReportsGenerator {
    public static void main(String[] args) throws IOException {

        var amountByShopsFile = Paths.get("amountByShops.txt");
        var amountByDateFile = Paths.get("amountByDate.txt");

        List<Operation> operations = Files.readAllLines(OperationsGenerator.operationsFile).stream()
                .map(ReportsGenerator::convertStringToOperation)
                .collect(toList());

        Map<Integer, Integer> amountByShops = operations.parallelStream()
                .collect(groupingBy(Operation::getShopId, summingInt(Operation::getOperationAmount)));

        amountByShops.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry ->
                {
                    try {
                        Files.writeString(
                                amountByShopsFile,
                                String.format("%d %d\n", entry.getKey(), entry.getValue()),
                                StandardOpenOption.APPEND,
                                StandardOpenOption.CREATE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        operations.parallelStream()
                .collect(groupingBy(
                        o -> o.getOperationDate().truncatedTo(ChronoUnit.DAYS),
                        TreeMap::new, // for sorting
                        summingInt(Operation::getOperationAmount)))
                .forEach((date, amount) -> {
                    try {
                        Files.writeString(
                                amountByDateFile,
                                String.format("%s %d\n", date, amount),
                                StandardOpenOption.APPEND,
                                StandardOpenOption.CREATE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static Operation convertStringToOperation(String s) {
        String[] strings = s.split(" ");
        return new Operation(
                LocalDateTime.parse(strings[0]),
                Integer.parseInt(strings[1]),
                Integer.parseInt(strings[2]),
                Integer.parseInt(strings[3])
        );
    }
}
