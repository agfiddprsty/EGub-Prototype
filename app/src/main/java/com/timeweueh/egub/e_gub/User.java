package com.timeweueh.egub.e_gub;

import javax.xml.transform.sax.SAXResult;

/**
 * Created by ZAQI on 5/4/2018.
 */

public class User {
    private String  email, status,level,password;
   private  Integer id;

    public User(int id,  String email,String password, String status,String level) {
        this.id = id;
       // this.username = username;
        this.email = email;
        this.status = status;
        this.password = password;
        this.level = level;
    }
public  Integer getId(){
        return  id;
}

    public String getEmail() {
        return email;
    }
public String getPassword(){
    return  password;
}
    public String getStatus() {
        return status;
    }
    public String getLevel(){
        return level;
    }
}
