package Model.Person;

import java.io.Serializable;

public class Presenter extends Person implements Serializable {
    private String nickname;

    public Presenter(String name,String lastName,String usernme,String password){
        super(name,lastName,usernme,password);
    }

    public Presenter(String name,String lastname,String username,String password,String nickname){
        super(name, lastname, username, password);
        setNickname(nickname);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}