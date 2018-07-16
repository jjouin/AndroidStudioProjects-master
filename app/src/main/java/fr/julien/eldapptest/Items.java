package fr.julien.eldapptest;

public class Items {

    private long id;
    private String name;
    private long use;
    private Integer in;
    private Integer strike;

    public Items (long id, String name, long use, Integer in, Integer strike )
    {
        super();
        this.id=id;
        this.name=name;
        this.use=use;
        this.in = in ;
        this.strike = strike;
    }

    public long getStrike () {return strike;}

    public void setStrike(Integer strike) {
        this.strike = strike;
    }

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

    public long getUse() {
        return use;
    }

    public void setUse(long use) {
        this.use=use;
    }

    public Integer getIn() {return in;}

    public void SetIn (Integer in) {this.in=in;}

}

