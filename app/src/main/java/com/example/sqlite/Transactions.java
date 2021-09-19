package com.example.sqlite;

import java.io.Serializable;

public class Transactions implements Serializable {

    String sender_name;
    String receiver_name;
    String sender_accno;
    String receiver_accno;
    String amount;
    String status;
    String date;

    public Transactions(String s,String r,String status){
        sender_name=s;
        receiver_name=r;
        this.status=status;
        this.sender_accno=sender_accno;
        this.receiver_accno=receiver_accno;
        this.amount=amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getReceiver_accno() {
        return receiver_accno;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getSender_accno() {
        return sender_accno;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setReceiver_accno(String receiver_accno) {
        this.receiver_accno = receiver_accno;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setSender_accno(String sender_accno) {
        this.sender_accno = sender_accno;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}


