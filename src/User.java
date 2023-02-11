import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    String name;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public List add(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите логин");
        name = scanner.nextLine().trim();
        if (name == "") {
            System.out.println("Логин не может быть пустым");
            return null;
        }
        System.out.println("Введите пароль");
        scanner = new Scanner(System.in);
        password = scanner.nextLine().trim();
        if (password == "") {
            System.out.println("Пароль не может быть пустым");
            return null;
        }
        users.add(new User(name, password));
        return users;
    }

    public List setUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Users.bin"))) {
            users.add((User) objectInputStream.readObject());
        } catch (Exception ex) {
            ex.getMessage();
        }
        return users;
    }

    public void save(List<User> users) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Users.bin"));
        for (User a : users) {
            objectOutputStream.writeObject(a);
            objectOutputStream.flush();


        }
    }
    public boolean login(User user) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Users.bin"))) {
            User checker;
            while ((checker = (User) objectInputStream.readObject()) != null) {
                if (Objects.equals(user.getName(), checker.getPassword())&&Objects.equals(user.getPassword(),checker.getPassword())) {
                    return true;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.getMessage();
        }
        return false;
    }
}
