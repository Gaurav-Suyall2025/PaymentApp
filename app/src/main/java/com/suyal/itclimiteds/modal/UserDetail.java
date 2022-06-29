package com.suyal.itclimiteds.modal;

public class UserDetail {
    String name,city,state, phone,aadharcard,message;


    public UserDetail(){}

    public UserDetail(String name,String city,String state, String phone, String aadharcard) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.aadharcard = aadharcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadharcard() {
        return aadharcard;
    }

    public void setAadharcard(String aadharcard) {
        this.aadharcard = aadharcard;
    }
}
