import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Profit {

    public Map set(HashMap<Comic, Integer> map) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Profits.bin"));
        HashMap<Comic, Integer> map1 = (HashMap<Comic, Integer>) objectInputStream.readObject();
        Map<Comic, Integer> map2 = Stream.of(map1, map)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        Integer::sum));
        return map2;
    }

    public void save(HashMap<Comic, Integer> map) throws IOException, ClassNotFoundException {
        HashMap<Comic, Integer> map2 = (HashMap<Comic, Integer>) set(map);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Profits.bin"));
        objectOutputStream.writeObject(map2);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void showProfit() throws IOException, ClassNotFoundException {
        double total = 0;
        HashMap<Comic, Integer> map = new HashMap<>();
        map = (HashMap<Comic, Integer>) set(map);
        for (Map.Entry<Comic, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey().getName() + " - " + entry.getValue());
            total = total + (entry.getKey().getPrice() * entry.getValue());
        }
        System.out.println("Общая сумма продаж - " + total);
    }
}
