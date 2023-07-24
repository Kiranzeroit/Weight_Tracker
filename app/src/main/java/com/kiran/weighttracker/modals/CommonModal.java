package com.kiran.weighttracker.modals;

public class CommonModal {

    public String name = "";
    public String age = "";
    public String email = "";
    public String password = "";
    public String mobile = "";
    public String targetWeight = "";
    public int id;

    public CommonModal(String name, String age, String email, String password, String mobile, String targetWeight, int id) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.targetWeight = targetWeight;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
