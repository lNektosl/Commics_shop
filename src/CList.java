import java.io.*;
import java.util.*;

public class CList implements Serializable {
    ArrayList<Comic> comics = new ArrayList<>();
    TreeMap<String, Integer> artists = new TreeMap<>();
    TreeMap<String, Integer> authors = new TreeMap<>();

    public ArrayList<Comic> readAndAdd(String filename) throws IOException, ClassNotFoundException {
        artists = new ArList().loadAr();
        authors = new AuList().loadAu();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Comic comic;
            while ((comic = (Comic) objectInputStream.readObject()) != null) {
                comics.add((comic));
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.getMessage();
        }
        return comics;
    }

    public void showList() {
        for (int i = 0; i < comics.size(); i++) {
            System.out.println((i + 1) + "." + " Название: " + comics.get(i).getName() + " Автор/Художник: " + comics.get(i).getAuthor() + "/" + comics.get(i).getArtist() + " Серия: " + comics.get(i).getSeries() + " Год: " + comics.get(i).getAge() + " Кол-во: " + comics.get(i).getAmount());
        }
    }

    public void add(String[] holder) {
        comics.add(new Comic(holder[0].trim(), holder[1].trim(), holder[2].trim(), holder[3].trim(), Integer.parseInt(holder[4].trim()), holder[5].trim(), Integer.parseInt(holder[6].trim()), Double.valueOf(holder[7].trim()), Double.valueOf(holder[8].trim()), holder[9].trim(), Integer.parseInt(holder[10].trim())));
        artists = new ArList().put(artists, holder[2].trim());
        authors = new AuList().put(authors, holder[1].trim());
    }

    public void save() throws IOException, ClassNotFoundException {
        AuList auList = new AuList();
        ArList arList = new ArList();
        auList.save(authors);
        arList.save(artists);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Comics.bin"));
        for (Comic a : comics) {
            objectOutputStream.writeObject(a);
            objectOutputStream.flush();
        }
        objectOutputStream.close();
    }

    public void del(int a) throws IOException, ClassNotFoundException {
        System.out.println("Желаете списать все или часть комиксв?\n1. все \n2. часть \n3. выход");
        Scanner scanner = new Scanner(System.in);
        String x = scanner.nextLine();
        switch (x) {
            case "1" -> {
                comics.remove(a);
            }
            case "2" -> {
                System.out.println("Введите сколько комиксов хотите списать");
                int del = scanner.nextInt();
                comics.get(a).setAmount(del);
            }
            case "3" -> {
                return;
            }
            default -> {
                System.out.println("Поле не найдено");
            }
        }
        save();
    }

    public void search() {
        System.out.println("Выберите критерий поиска \n1. Название \n2. Художник\n3. Автор \n4. Жанр \n5. Список новинок \n6. Список самых продоваемых комиксов " +
                "\n7. Список самых популярных художников \n8. Список самых популярных авторов \n9. Список самых популярных жанров");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        String holder;
        switch (x) {
            case 1 -> {
                int id = 0;
                System.out.println("Введите название");
                scanner = new Scanner(System.in);
                holder = scanner.nextLine().toLowerCase(Locale.ROOT);
                for (Comic a : comics) {
                    id++;
                    if (a.getName().toLowerCase(Locale.ROOT).contains(holder)) {
                        System.out.println(id + ". " + a.print());
                    }
                }
            }
            case 2 -> {
                int id = 0;
                System.out.println("Введите имя художника");
                scanner = new Scanner(System.in);
                holder = scanner.nextLine();

                for (Comic a : comics) {
                    id++;
                    if (a.getArtist().toLowerCase(Locale.ROOT).contains(holder)) {
                        System.out.println(id + ". " + a.print());
                    }
                }
            }
            case 3 -> {
                int id = 0;
                System.out.println("Введите имя автора");
                scanner = new Scanner(System.in);
                holder = scanner.nextLine();
                for (Comic a : comics) {
                    id++;
                    if (a.getAuthor().toLowerCase(Locale.ROOT).contains(holder)) {
                        System.out.println(id + ". " + a.print());
                    }
                }
            }
            case 4 -> {
                int id = 0;
                System.out.println("Введите Жанр");
                scanner = new Scanner(System.in);
                holder = scanner.nextLine();
                for (Comic a : comics) {
                    id++;
                    if (a.getGenre().toLowerCase(Locale.ROOT).contains(holder)) {
                        System.out.println(id + ". " + a.print());
                    }
                }
            }
            case 5 -> {
                comics.sort(Comparator.comparing(Comic::getDATE));
                System.out.println("Новые поступления");
                for (int i = 0; i < comics.size() && i < 10; ++i) {
                    System.out.println((i + 1) + ". " + comics.get(i).print());
                }
            }
            case 6 -> {
                comics.sort(Comparator.comparing(Comic::getSold).reversed());
                System.out.println("Самые продоваемые");
                for (int i = 0; i < comics.size() && i < 10; ++i) {
                    System.out.println((i + 1) + ". " + comics.get(i).print());
                }
            }
            case 7 -> {
                List<Artist> artistsSorted = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : artists.entrySet()) {
                    artistsSorted.add(new Artist(entry.getKey(), entry.getValue()));
                }
                artistsSorted.sort(Comparator.comparing(Artist::getPoular).reversed());
                for (int i = 0; i < artistsSorted.size() && i < 10; ++i)
                    System.out.println((i + 1) + ". " + artistsSorted.get(i).getName());
            }
            case 8 -> {
                ArrayList<Author> authorsSorted = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : artists.entrySet()) {
                    authorsSorted.add(new Author(entry.getKey(), entry.getValue()));
                }
                authorsSorted.sort(Comparator.comparing(Author::getPoular).reversed());
                for (int i = 0; i < authorsSorted.size() && i < 10; ++i)
                    System.out.println((i + 1) + ". " + authorsSorted.get(i).getName());
            }
            case 9 -> {

            }
        }
    }


    public void sale() throws Exception {
        HashMap<Comic, Integer> saleHolder = new HashMap<>();
        boolean saleChecker = true;
        boolean amountChecker;
        double total = 0;
        while (saleChecker) {
            System.out.println("Сумма покупки: " + total);
            System.out.println("Введите:" +
                    "\n1. Для добавления комикса" +
                    "\n2. Для вывода листа всех комиксов" +
                    "\n3. Для отмены продажа комикса" +
                    "\n4. Подтвердить продажу и выйти" +
                    "\n5. Для выхода");
            Scanner scanner = new Scanner(System.in);
            int x = scanner.nextInt();
            switch (x) {
                case 1 -> {
                    int a = 0;
                    System.out.println("Выберите комиксы на продажу");
                    scanner = new Scanner(System.in);
                    int i = scanner.nextInt() - 1;
                    System.out.println("Введите кол-во");
                    scanner = new Scanner(System.in);
                    a = scanner.nextInt();
                    amountChecker = comics.get(i).setAmount(a);
                    if (amountChecker) {
                        comics.get(i).setSold(a);
                        if (saleHolder.containsKey(comics.get(i))) {
                            int b = saleHolder.get(comics.get(i));
                            a = a + b;
                            saleHolder.put(comics.get(i), a);
                        }
                        saleHolder.put(comics.get(i), a);
                    } else
                        System.out.println("Недостаточно на складе. Всего в наличии " + comics.get(i).getAmount());
                    if (!saleHolder.isEmpty()) {
                        total = 0;
                        for (Map.Entry<Comic, Integer> entry : saleHolder.entrySet()) {
                            total = total + (entry.getKey().getPrice() * entry.getValue());
                        }
                    }
                }
                case 2 -> {
                    showList();
                }
                case 3 -> {
                    for (Map.Entry<Comic, Integer> entry : saleHolder.entrySet()) {
                        System.out.println(entry.getKey().getName() + " - " + entry.getValue());
                    }
                    System.out.println("Введите название комикса...");
                    scanner = new Scanner(System.in);
                    String name = scanner.nextLine().trim();
                    for (Map.Entry<Comic, Integer> entry : saleHolder.entrySet()) {
                        if (entry.getKey().getName().toLowerCase(Locale.ROOT).trim().equals(name.toLowerCase(Locale.ROOT))) {
                            int i = comics.indexOf(entry.getKey());
                            comics.get(i).setAmountBack(entry.getValue());
                            comics.get(i).setSoldBack(entry.getValue());
                            saleHolder.remove(entry.getKey());
                        }
                        if (!saleHolder.isEmpty()) {
                            total = 0;
                            total = total + (entry.getKey().getPrice() * entry.getValue());
                        } else total = 0;
                    }
                }
                case 4 -> {
                    ArList arList = new ArList();
                    AuList auList = new AuList();
                    for (Map.Entry<Comic, Integer> entry : saleHolder.entrySet()) {
                        artists = arList.addPopularity(artists, entry.getKey().getArtist());
                        authors = auList.addPopularity(authors, entry.getKey().getAuthor());


                    }
                    save();
                    acceptSale(saleHolder);
                    saleChecker = false;
                }
                case 5 -> {
                    for (Map.Entry<Comic, Integer> entry : saleHolder.entrySet()) {
                        comics.get(comics.indexOf(entry.getKey())).setAmountBack(entry.getValue());
                        saleHolder.remove(entry.getKey());
                    }
                    saleChecker = false;
                }
                default -> {
                    System.out.println("Пункт меню отсутствует");
                }
            }
        }
    }

    public void acceptSale(HashMap<Comic, Integer> map) throws Exception {
        Profit profit = new Profit();
        profit.save(map);
    }

    public void change(int a) throws IOException, ClassNotFoundException {
        boolean chChecker = true;
        while (chChecker) {
            System.out.println("Что хотите изменить?");
            System.out.println("1. Название: " + comics.get(a).getName() +
                    "\n2. Автор: " + comics.get(a).getAuthor() +
                    "\n3. Хужожник: " + comics.get(a).getArtist() +
                    "\n4. Издатель: " + comics.get(a).getPublisher() +
                    "\n5. Кол-во страниц: " + comics.get(a).getPages() +
                    "\n6. Жанр: " + comics.get(a).getGenre() +
                    "\n7. Год издания:" + comics.get(a).getAge() +
                    "\n8. Себестоимость: " + comics.get(a).getCost() +
                    "\n9. Цена: " + comics.get(a).getPrice() +
                    "\n0. Серия: " + comics.get(a).getSeries() +
                    "\na. Чтобы сохранить и выйти");
            Scanner scanner = new Scanner(System.in);
            String b = scanner.nextLine();
            String holder = null;
            switch (b) {
                case "1" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setName(holder);
                }
                case "2" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setAuthor(holder);
                    if (!authors.containsValue(comics.get(a).getAuthor()))
                        authors.put(holder, 1);

                }
                case "3" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setArtist(holder);
                    if (!artists.containsValue(comics.get(a).getArtist())) {
                        artists.put(holder, 1);
                    }
                }
                case "4" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setPublisher(holder);
                }
                case "5" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setPages(Integer.parseInt(holder));
                }
                case "6" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setGenre(holder);
                }
                case "7" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setAge(Integer.parseInt(holder));
                }
                case "8" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setCost(Double.valueOf(holder));
                }
                case "9" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setPrice(Double.valueOf(holder));
                }
                case "0" -> {
                    System.out.println("Введите изменение...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    holder = br.readLine();
                    comics.get(a).setSeries(holder);
                }
                case "a", "а" -> {
                    save();
                    chChecker = false;
                }
                default -> System.out.println("поле отсутствует в списке");
            }

        }

    }

    public void workWithDiscounts() throws IOException {
        boolean dsChecker = true;
        Discount discountO = new Discount();
        String x;
        ArrayList<Discount> discounts = discountO.setDiscounts();
        while (dsChecker) {
            System.out.println("Выберите пункт меню\n1. Создать акцию\n2. Добавить комикс к ации\n3. Удалить комикс из акций\n4. Удалить акцию\n5. для выхода");
            Scanner scanner = new Scanner(System.in);
            x = scanner.nextLine();
            switch (x) {
                case "1" -> {
                    System.out.println("Введите название акции");
                    scanner = new Scanner(System.in);
                    String name = scanner.nextLine();
                    System.out.println("Введите скидку");
                    int i = scanner.nextInt();
                    discounts.add(new Discount(name, i));
                    discountO.save(discounts);
                }
                case "2" -> {
                    showList();
                    System.out.println("Выберите номер комикса");
                    scanner = new Scanner(System.in);
                    int i = (scanner.nextInt() - 1);
                    discountO.showDiscounts(discounts);
                    System.out.println("Выберите номер акции");
                    int a = (scanner.nextInt() - 1);
                    comics.get(i).setDiscount(discounts.get(a));
                }
                case "3" -> {
                    for (Comic a : comics)
                        if (a.dis) {
                            System.out.println(a.print() + ", Акция " + a.getDiscount());
                        }
                    System.out.println("Выберите комикс");
                    scanner = new Scanner(System.in);
                    int i = scanner.nextInt()-1;
                    if (comics.get(i).dis) {
                        comics.get(i).removeDiscount();
                    } else System.out.println("Комикс не обозначен в какой либо акции");
                }
                case "4" -> {
                    int i = 0;
                    discountO.showDiscounts(discounts);
                    System.out.println("Выберите номер акции");
                    i = (scanner.nextInt() - 1);
                    for (Comic a : comics) {
                        if (a.dis) {
                            if (Objects.equals(a.getDiscount(), discounts.get(i).getName())) {
                                comics.get(i).removeDiscount();
                            }
                        }
                    }
                    discounts.remove(i);
                    discountO.save(discounts);

                }
                case "5" -> {
                    dsChecker = false;
                }

            }
        }
    }
}
