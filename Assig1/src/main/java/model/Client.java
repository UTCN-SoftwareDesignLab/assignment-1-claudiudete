package model;

import java.util.List;

public class Client {

     private long id;

     private String name;
     private String idCardNumber;
     private long persNumCode;
     private String address;
     private List<Account> accounts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public long getPersNumCode() {
        return persNumCode;
    }

    public void setPersNumCode(long persNumCode) {
        this.persNumCode = persNumCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
