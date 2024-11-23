import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BitcoinDataRetrieverManager {
    private static final long SCHEDULE_PERIOD_IN_MILLIS = 60 * 1000; // 1 Minute
    private static final int BITCOIN_VALUES_NUMBER = 10;
    private static final List<Double> prices = new LinkedList<>();

    public static void main(String[] args) {
        initScheduler();
    }

    private static void initScheduler() {
        Timer timer = new Timer();
        AtomicInteger counter = new AtomicInteger(1);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    double price = BitcoinDataRetrieverService.fetchBitcoinPrice();
                    System.out.println("Current Bitcoin Price: $" + price);
                    synchronized (prices) {
                        if (prices.size() == BITCOIN_VALUES_NUMBER) {
                            prices.remove(0);
                        }
                        prices.add(price);
                    }
                    if (counter.getAndIncrement() >= BITCOIN_VALUES_NUMBER) {
                        printPricesAverage();
                        counter.set(1);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching Bitcoin price: " + e.getMessage());
                }
            }
        }, 0, SCHEDULE_PERIOD_IN_MILLIS);
    }

    private static void printPricesAverage() {
        double average = prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("10-Minute Average Price: $" + average);
    }
}
