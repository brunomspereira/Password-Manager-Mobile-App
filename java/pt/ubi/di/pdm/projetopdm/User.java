package pt.ubi.di.pdm.projetopdm;

import java.util.Date;

public class User {
    String UserName, PassWord;

    public String getUserName (){
        return UserName;
    }
    public void setUsername(String username){
        this.UserName = username;
    }
    public void setPassWord(String Password){
        this.PassWord = Password;
    }
    public User (String UserName, String Password){
        this.UserName = UserName;
        this.PassWord = Password;
    }
}
