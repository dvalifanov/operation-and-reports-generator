import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class OperationsGenerator {

    static final Path operationsFile = Paths.get("operations.txt");

    public static void main(String[] args) throws IOException {

        final var dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final var random = ThreadLocalRandom.current();
        final var stringBuilder = new StringBuilder();
        final var shopsId = List.of(1, 2, 3, 4, 5);
        final var date1 = new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime();
        final var date2 = new GregorianCalendar(2018, Calendar.DECEMBER, 31).getTime();

        Supplier<Integer> shopIdSupplier = () -> shopsId.get(random.nextInt(shopsId.size()));
        Supplier<Integer> operationAmountSupplier = () -> random.nextInt(10000, 100001);
        Supplier<String> dateSupplier = () -> dateFormat.format(new Date(random.nextLong(date1.getTime(), date2.getTime())));

        for (int i = 1; i < 9000; i++) {
            stringBuilder.append(dateSupplier.get());
            stringBuilder.append(" ");
            stringBuilder.append(shopIdSupplier.get());
            stringBuilder.append(" ");
            stringBuilder.append(i);
            stringBuilder.append(" ");
            stringBuilder.append(operationAmountSupplier.get());
            stringBuilder.append("\n");
            Files.writeString(operationsFile, stringBuilder, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            stringBuilder.setLength(0);
        }
    }
}
