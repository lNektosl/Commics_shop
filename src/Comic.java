import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

public class Comic implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String name;
    private String author;
    private String artist;
    private String publisher;
    private int pages;
    private String genre;
    private int age;
    private double cost;
    private double price;
    private String series;
    private Discount sale;
    private Double oldPrice;
    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold =this.sold + sold;
    }
public void setSoldBack(int sold){
        this.sold=this.sold-sold;
}
    int sold;

    public boolean isDis() {
        return dis;
    }

    boolean dis = false;

    public Date getDATE() {
        return DATE;
    }

    private final Date DATE;

    public int getAmount() {
        return amount;
    }

    public boolean setAmount(int amount) {
        if (this.amount >= amount) {
            this.amount = this.amount - amount ;
            return true;
        }else return false;
    }

    public void setAmountBack(int amount){
        this.amount = this.amount + amount;
    }

    private int amount = 0;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }


    public Comic(String name, String author, String artist, String publisher, int pages, String genre, int age, Double cost, Double price, String series, int amount) {
        this.name = name;
        this.author = author;
        this.artist = artist;
        this.publisher = publisher;
        this.pages = pages;
        this.genre = genre;
        this.age = age;
        this.cost = cost;
        this.price = price;
        this.series = series;
        this.amount = amount;
        DATE = new Date();
    }

    public void setDiscount(Discount sale) {
        this.sale = sale;
        this.oldPrice = price;
        this.price = price -(price*sale.getDiscount()/100);
    }

    public void removeDiscount(){
        this.sale=null;
        System.gc();
        this.price = oldPrice;
        dis = !dis;
    }
    public String getDiscount(){
        return this.sale.getName();
    }

    public String print(){
        return String.format(Locale.UK,"Название: %s, Автор: %s, Художник: %s, Издатель: %s, Жанр: %s Год: %d, Серия: %s, Цена: %.2f",
                this.name,
                this.author,
                this.artist,
                this.publisher,
                this.genre,
                this.age,
                this.series,
                this.price);
    }
}
