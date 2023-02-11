import java.io.*;
import java.util.*;


public class ArList {
    TreeMap<String, Integer> artists = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public TreeMap<String, Integer> put(TreeMap<String,Integer>artists,String artist) {
        if (!artists.containsKey(artist)) {
            artists.put(artist,1);
        }
        return artists;
    }

    public TreeMap<String, Integer> getArtists() {
        return artists;
    }

    public TreeMap<String, Integer> addPopularity(TreeMap<String,Integer>artists,String artist) {
        for (Map.Entry<String, Integer> entry : artists.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(artist)) {
                artists.put(entry.getKey(), entry.getValue()+1);
            }
        }
        return artists;
    }

    public void save(TreeMap<String, Integer> artists) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Artists.csv"));
        for (Map.Entry<String, Integer> entry : artists.entrySet()) {
            bufferedWriter.write("\n"+entry.getKey() + "," + entry.getValue());
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }


    public TreeMap<String, Integer> loadAr() throws IOException, ClassNotFoundException {
       BufferedReader bufferedReader = new BufferedReader(new FileReader("Artists.csv"));
       String line;
       bufferedReader.readLine();
        while ((line = bufferedReader.readLine())!=null) {
            System.out.println("hi");
            String holder[] = line.split(",");
            artists.put(holder[0], Integer.valueOf(holder[1]));
        }
        return artists;
    }

}
