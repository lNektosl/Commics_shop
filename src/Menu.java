import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public Menu(User user) {
        this.user = user;
    }

    User user;

    public void start() throws Exception {
        System.out.println("Введите путь файла...");
        String fName;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        fName = br.readLine();
        CList cList = new CList();
        ArrayList<Comic> comics = cList.readAndAdd(fName);
        while (true) {
            System.out.println("\nВыберите пункт меню:" +
                    "\n1. Добавить комикс" +
                    "\n2. Списать комиксы" +
                    "\n3. Редактировать параметры комикса" +
                    "\n4. Продать комиксы" +
                    "\n5. Создать акцию" +
                    "\n6. Отложить комиксы для конкретного покупателя" +
                    "\n7. Поиск" +
                    "\n8. Вывод всего списка комиксов" +
                    "\n9. Сохранить" +
                    "\n0. Добвление пользователя" +
                    "\nНажмите <Enter> для выхода");
            Scanner scanner = new Scanner(System.in);
            String x = scanner.nextLine().trim();
            switch (x) {
                case "1" -> {
                    System.out.println("Введите: Название, Автора, Художника, Издателя, Кол-во страниц, Жанр, Год, Себестоимость, Цену, Серию, Количество");
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String[] line = br1.readLine().split(",");
                    cList.add(line);
                }
                case "2" -> {
                    cList.showList();
                    System.out.println("Введите номер комикса");
                    scanner = new Scanner(System.in);
                    int a = scanner.nextInt() - 1;
                    cList.del(a);
                }
                case "3" -> {
                    System.out.println("Введите номер комикса");
                    scanner = new Scanner(System.in);
                    int a = scanner.nextInt() - 1;
                    cList.change(a);
                }
                case "4" -> {
                    cList.sale();
                }
                case "5" -> {
                    cList.workWithDiscounts();
                }
                case "6" -> {
                    BList bList = new BList();
                    bList.menu(comics);
                }
                case "7" -> {
                    cList.search();
                }
                case "8" -> cList.showList();
                case "9" -> {
                    cList.save();
                }
                case "0" -> {
                    user.save(user.add(user.setUsers()));
                }
                case "" -> System.exit(0);
            }
        }
    }
}