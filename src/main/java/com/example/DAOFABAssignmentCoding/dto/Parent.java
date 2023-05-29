package com.example.DAOFABAssignmentCoding.dto;
import com.example.DAOFABAssignmentCoding.dto.Child;

import java.util.List;

public class Parent {
    private int id;
    private String sender;
    private String receiver;
    private List<Child> childs;
    private int totalAmount;

    public int getId() {
        return id;
    }

    public void setChild(List<Child> child) {
        this.childs = child;
    }

    public List<Child> getChilds() {
        return childs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}