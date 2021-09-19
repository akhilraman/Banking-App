package com.example.sqlite;

import java.io.Serializable;

public class user implements Serializable {
    String name;
    int age;
    String accountno;
    String IFCcode;
    String phoneno;
    String email;

    int balance;

    public user(String s, String accountno,int balance,String IFCcode,String phoneno,String email){
        name=s;
        this.accountno=accountno;
        this.balance=balance;
        this.email=email;
        this.IFCcode=IFCcode;
        this.phoneno=phoneno;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountno() {
        return accountno;
    }

    public String getEmail() {
        return email;
    }

    public String getIFCcode() {
        return IFCcode;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIFCcode(String IFCcode) {
        this.IFCcode = IFCcode;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
