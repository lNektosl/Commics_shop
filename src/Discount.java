
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Discount implements Serializable {
    ArrayList<Discount> discounts = new ArrayList<>();

    private String name;
    private int discount;

    public Discount(String name, int discount) {
        this.name = name;
        this.discount = discount;
    }
    public Discount(){}

    public String getName() {
        return name;
    }
    public int getDiscount() {
        return discount;
    }

    public void add(String name, int discount){
        discounts.add(new Discount(name, discount));
    }
public void showDiscounts( ArrayList<Discount> discounts){
        int i = 0;
        for (Discount a : discounts){
            i++;
            System.out.println(i+". "+a.toString() + "%");
        }
}
public ArrayList<Discount> setDiscounts(){
    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Discount.bin"))) {
        discounts = (ArrayList<Discount>) objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException ex) {
        ex.getMessage();
    }
    return discounts;
}
    public void save(ArrayList<Discount> discounts) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Discount.bin"));
            objectOutputStream.writeObject(discounts);
            objectOutputStream.flush();
        objectOutputStream.close();
    }
    public String toString(){
        return String.format(Locale.UK," %s, %d",this.name,this.discount);
    }
}
