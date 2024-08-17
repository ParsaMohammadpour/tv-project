package Model.Person;

import java.io.Serializable;

public class Person implements Serializable {
    protected String name;
    protected String lastname;
    protected String username;
    protected String password;

    public Person(String name,String lastname,String username,String password){
        setLastname(lastname);
        setName(name);
        setPassword(password);
        setUsername(username);
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void chaneName(String name){
        if (name != null)
            this.setName(name);
    }

    public void changeLastname(String lastname){
        if(lastname != null)
            this.setLastname(lastname);
    }

    public void changePassword(String password){
        if(password != null){
            if(password.length() >= 5){
                this.setPassword(password);
            }
        }
    }
}
