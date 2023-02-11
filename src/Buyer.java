import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Buyer implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }


    String name;
    String phone;
    List<Comic>comics = new ArrayList<>();
    public Buyer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    Buyer(){
    }
    public void addComic(Comic comic){
        comics.add(comic);
    }

    public void showComics(){
        int i =0;
        for (Comic a : comics){
            i++;
            System.out.println(i + ". " + a.print());
        }
    }

}
