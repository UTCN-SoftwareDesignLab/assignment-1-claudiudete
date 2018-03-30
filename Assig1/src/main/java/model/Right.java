package model;

public class Right {

    private long id;
    private String right;

    public Right(long id,String right)
    {
        this.id=id;
        this.right=right;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
