import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BList extends Buyer implements Serializable {
    public void menu(ArrayList<Comic> comics) throws IOException, ClassNotFoundException {
        boolean bChecker = true;
        load();
        while (bChecker) {
            System.out.println("Выберите пункт меню " +
                    "\n1. Создать нового покупателя" +
                    "\n2. Отложить комикс для существующего покупателя" +
                    "\n3. Удалить покупателя" +
                    "\n4. Посмотреть список покупателей и комиксов отложенных для них " +
                    "\n5. Сохранить" +
                    "\n6. Выйти");
            Scanner scanner = new Scanner(System.in);
            String x = scanner.nextLine();
            switch (x) {
                case "1"->{
                    System.out.println("Введите имя покупателя");
                    scanner = new Scanner(System.in);
                    String name = scanner.nextLine();
                    System.out.println("Введите номер");
                    scanner = new Scanner(System.in);
                    String phone = scanner.nextLine();
                    add(new Buyer(name,phone));
                }
                case "2"->{
                    showList();
                    System.out.println("Введите номер покупателя");
                    scanner = new Scanner(System.in);
                    int i = (scanner.nextInt()-1);
                    showComicsList(comics);
                    System.out.println("Введите номер комикса");
                    scanner = new Scanner(System.in);
                    int a = (scanner.nextInt()-1);
                    boolean amountChecker = comics.get(i).setAmount(a);
                    if (amountChecker) {

                    } else System.out.println("Недостаточно на складе. Всего в наличии " + comics.get(i).getAmount());
                    buyers.get(i).addComic(comics.get(a));
                }
                case "3"->{
                    showList();
                    System.out.println("Введите номер покупателя");
                    scanner = new Scanner(System.in);
                    int i = scanner.nextInt();
                    del(i);
                }
                case "4"->{
                    showList();
                }
                case "5"->{
                    CList cList = new CList();
                    cList.save();
                    save();
                }
                case "6" -> {
                    bChecker = false;
                }
            }
        }
    }

    List<Buyer> buyers = new ArrayList<>();

    public void add(Buyer buyer) {
        buyers.add(buyer);
    }
    public void showComicsList(ArrayList<Comic> comics) {
        for (int i = 0; i < comics.size(); i++) {
            System.out.println((i + 1) + "." + " Название: " + comics.get(i).getName() + " Автор/Художник: " + comics.get(i).getAuthor() + "/" + comics.get(i).getArtist() + " Серия: " + comics.get(i).getSeries() + " Год: " + comics.get(i).getAge() + " Кол-во: " + comics.get(i).getAmount());
        }
    }
    public void showList() {
        int i = 0;
        for (Buyer a : buyers) {
            i++;
            System.out.println(i+". Имя: " + a.getName() + " Телефон: " + a.getPhone() + " Отложенные комиксы: ");
            a.showComics();
        }
    }

    public void del(int i) {
        buyers.remove(i);
    }

    public void load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Buyers.bin"))) {
            Buyer buyer;
            while ((buyer = (Buyer) objectInputStream.readObject()) != null) {
                buyers.add((buyer));
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.getMessage();
        }
    }
    public void save() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Buyers.bin"));
        for (Buyer a : buyers) {
            objectOutputStream.writeObject(a);
            objectOutputStream.flush();
        }
        objectOutputStream.close();
    }
}
