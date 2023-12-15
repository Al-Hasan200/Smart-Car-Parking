package com.example.smartcarparking;

public class UserModel {
    private String name, phone, email, address, carNumber, rfidNumber, password;

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, String address, String carNumber, String rfidNumber, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.carNumber = carNumber;
        this.rfidNumber = rfidNumber;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getRfidNumber() {
        return rfidNumber;
    }

    public void setRfidNumber(String rfidNumber) {
        this.rfidNumber = rfidNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
