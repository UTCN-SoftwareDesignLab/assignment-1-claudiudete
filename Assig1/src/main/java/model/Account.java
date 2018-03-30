package model;

import java.util.Date;

public class Account {

    private long id;
    private long sum;
    private String type;
    private Date creationDate;

   /* public Account(long id, long sum, String type, Date creationDate) {
        this.id = id;
        this.sum = sum;
        this.type = type;
        this.creationDate = creationDate;
    }*/

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id=id;
    }

    public long getSum(){
        return this.sum;
    }

    public void setSum(long sum)
    {
        this.sum=sum;
    }

    public Date getCreationDate(){
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate){
       this.creationDate=creationDate;
    }

    public String getType() {return this.type;}

    public void setType(String type) {this.type=type;}




}
