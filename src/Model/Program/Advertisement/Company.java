package Model.Program.Advertisement;

import java.io.Serializable;

public class Company implements Serializable {
    private long money;
    private String name;
    private String password;

    public Company(String name,long money,String password){
        setName(name);
        setMoney(money);
        setPassword(password);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public long getMoney() {
        return money;
    }

    public void addMoney(long added){
        this.money +=added;
    }

    public void addMoney(int added){
        this.money +=(long)added;
    }

    public String getPassword() {
        return password;
    }
}
