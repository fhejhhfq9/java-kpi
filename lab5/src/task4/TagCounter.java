package task4;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagCounter {
    private static final Pattern OPEN_TAG = Pattern.compile("<([a-zA-Z][a-zA-Z0-9-]*)");

    static String download(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Lab3 TagCounter)")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Сервер повернув код " + response.statusCode());
        }
        return response.body();
    }

    static Map<String, Integer> countTags(String html) {
        String cleaned = html.replaceAll("(?s)<!--.*?-->", "")
                .replaceAll("(?is)(<(script|style)\\b[^>]*>).*?(</\\2\\s*>)", "$1$3");

        Map<String, Integer> counts = new HashMap<>();
        Matcher matcher = OPEN_TAG.matcher(cleaned);
        while (matcher.find()) {
            counts.merge(matcher.group(1).toLowerCase(), 1, Integer::sum);
        }
        return counts;
    }


    static void printByName(Map<String, Integer> counts) {
        new TreeMap<>(counts).forEach((tag, n) -> System.out.printf("%-12s %d%n", tag, n));
    }


    static void printByFrequency(Map<String, Integer> counts) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()));
        for (Map.Entry<String, Integer> e : list) {
            System.out.printf("%-12s %d%n", e.getKey(), e.getValue());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть URL : ");
        String url = scanner.nextLine().trim();

        try {
            Map<String, Integer> counts = countTags(download(url));
            if (counts.isEmpty()) {
                System.out.println("Тегів не знайдено.");
                return;
            }
            System.out.println("\n--- У лексикографічному порядку ---");
            printByName(counts);
            System.out.println("\n--- За зростанням частоти ---");
            printByFrequency(counts);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: некоректний URL.");
        } catch (IOException e) {
            System.out.println("Помилка завантаження сторінки: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Завантаження перервано.");
        }
    }
}