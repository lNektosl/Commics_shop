import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static synchronized void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            System.out.println("Введите логин");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            System.out.println("Введите пароль");
            scanner = new Scanner(System.in);
            String password = scanner.nextLine();
            User user = new User(name,password);
           if (user.login(user)) {
                Menu menu = new Menu(user);
                menu.start();
               } else System.out.println("Логин/пароль неверный");
        }
        System.out.println("Во входе отказано");
        System.exit(0);


    }
}
