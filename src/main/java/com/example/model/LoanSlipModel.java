package com.example.model;

import java.sql.Timestamp;

public class LoanSlipModel extends AbstractModel{
    private int idAccount, idBook;
    private String userName, numberPhone, title, code;

    public LoanSlipModel() {
    }

    public LoanSlipModel(int id, Timestamp created_at, Timestamp updated_at, int idAccount, int idBook, String userName, String numberPhone, String title, String code) {
        super(id, created_at, updated_at);
        this.idAccount = idAccount;
        this.idBook = idBook;
        this.userName = userName;
        this.numberPhone = numberPhone;
        this.title = title;
        this.code = code;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
