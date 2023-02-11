import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class AuList {

    TreeMap<String, Integer> authors = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public TreeMap<String, Integer> put(TreeMap<String, Integer> authors, String author) {
        if (!authors.containsKey(author)) {
            authors.put(author,1);
        }
        return authors;
    }
    public TreeMap<String, Integer> addPopularity(TreeMap<String, Integer> artists, String author) {
        for (Map.Entry<String, Integer> entry : artists.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(author)) {
                artists.put(entry.getKey(), entry.getValue()+1);
            }
        }
        return artists;
    }

    public void save(TreeMap<String, Integer> authors) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Authors.csv"));
        for (Map.Entry<String, Integer> entry : authors.entrySet()) {
            bufferedWriter.write("\n"+entry.getKey() + "," + entry.getValue());
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }


    public TreeMap<String, Integer> loadAu() throws IOException, ClassNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("Authors.csv"));
        String line;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine())!=null) {
            String holder[] = line.split(",");
            authors.put(holder[0], Integer.valueOf(holder[1]));
        }
        return authors;
    }
}
