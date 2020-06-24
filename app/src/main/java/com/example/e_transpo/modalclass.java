package com.example.e_transpo;

public class modalclass {
    private String User_ID_of_Patient;
    private String User_ID_of_user;
    private String Name_of_Patient;
    private String Heath_Issue;

    private modalclass(){}

    private modalclass(String User_ID_of_Patient ,String User_ID_of_user,String Name_of_Patient,String Heath_Issue){
        this.User_ID_of_Patient=User_ID_of_Patient;
        this.User_ID_of_user=User_ID_of_user;
        this.Name_of_Patient=Name_of_Patient;
        this.Heath_Issue=Heath_Issue;
    }
    public String getUser_ID_of_Patient() {
        return User_ID_of_Patient;
    }

    public void setUser_ID_of_Patient(String user_ID_of_Patient) {
        this.User_ID_of_Patient = user_ID_of_Patient;
    }

    public String getUser_ID_of_user() {
        return User_ID_of_user;
    }

    public void setUser_ID_of_user(String user_ID_of_user) {
        this.User_ID_of_user = user_ID_of_user;
    }

    public String getName_of_Patient() {
        return Name_of_Patient;
    }

    public void setName_of_Patient(String name_of_Patient) {
        this.Name_of_Patient = name_of_Patient;
    }

    public String getHeath_Issue() {
        return Heath_Issue;
    }

    public void setHeath_Issue(String heath_Issue) {
        this.Heath_Issue = heath_Issue;
    }



}
