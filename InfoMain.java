import java.io.File;
import java.time.LocalDateTime;

public class InfoMain {
    protected String name;
    private LocalDateTime date;
    protected String formatDate;
    protected String productName;
    protected String price;
    protected File fileDir = new File("Money.csv");
    protected File fileRegis = new File("Username.csv");
    protected File fileType = new File("Type.csv");
    protected int primaryKey;
    protected double goalDays;
    protected double goalWeek;
    protected double goalMonths;
    protected String type;


    public InfoMain(){}
    public void setGoalDays(double goalDays) {
        this.goalDays = goalDays;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public double getGoalDays() {
        return goalDays;
    }
    public void setGoalMonths(double goalMonths) {
        this.goalMonths = goalMonths;
    }
    public double getGoalMonths() {
        return goalMonths;
    }
    public void setGoalWeek(double goalWeek) {
        this.goalWeek = goalWeek;
    }
    public double getGoalWeek() {
        return goalWeek;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }
    public int getPrimaryKey() {
        return primaryKey;
    }
    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public String getProductName(){
        return productName;
    }
    public void setPrice(double price){
        String priceFormat = String.valueOf(price);
        this.price = priceFormat;
    }
    public String getPrice(){
        return price;
    }
    public void setDate(){
        date = LocalDateTime.now();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        String formatDay = null;
        if(day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9){
            formatDay = "0"+day;
        }else{
            formatDay = ""+day;
        }
            formatDate = (formatDay+"/"+month+"/"+year);
        }
    public String getDate(){
        return formatDate;
    }
    
}