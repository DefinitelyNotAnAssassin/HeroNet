package com.example.heronetapplication.ObjectTypes;

import java.util.HashMap;

public class Users{

    public static String firstName, lastName, email, password, occupation, id;
    public static double skillLevel;
    public Users(String firstName, String lastName, String email, String password, String occupation, Double skillLevel){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
        this.skillLevel = skillLevel;
    }

    public HashMap<String, Object> getUserInfo(){
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("FirstName", firstName);
        userInfo.put("LastName", lastName);
        userInfo.put("Email", email);
        userInfo.put("Password", password);
        userInfo.put("Occupation", occupation);
        userInfo.put("SkillLevel", skillLevel);
        return userInfo;
    }
}
