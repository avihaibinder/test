import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class BitcoinDataRetrieverService {

    private static final String API_URL = "https://api.coindesk.com/v1/bpi/currentprice/USD.json";

    static double fetchBitcoinPrice() throws IOException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(API_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpRequestConsts.Methods.GET);
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP response code: " + conn.getResponseCode());
            }
            StringBuilder jsonResponse = new StringBuilder();
            try (Scanner scanner = new Scanner(url.openStream())) {
                while (scanner.hasNext()) {
                    jsonResponse.append(scanner.nextLine());
                }
            }
            String json = jsonResponse.toString();
            String priceString = json.split("\"rate\":\"")[1].split("\",")[0].replace(",", "");
            return Double.parseDouble(priceString);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
