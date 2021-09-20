package com.example.sqlite;

import java.io.Serializable;

public class Transactions implements Serializable {
    String transaction_id;
    String sender_name;
    String receiver_name;
    String sender_accno;
    String receiver_accno;
    int amount;
    String status;
    String date;

    public Transactions(String s,String r,String status,int amount,String date,String transaction_id,String sender_accno,String receiver_accno){
        sender_name=s;
        receiver_name=r;
        this.status=status;
        this.date=date;
        this.amount=amount;
        this.transaction_id=transaction_id;
        this.sender_accno=sender_accno;
        this.receiver_accno=receiver_accno;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
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

    public void setAmount(int amount) {
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


