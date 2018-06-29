package com.xiao.tron.restcontroller;

public class Posting {
    private long id;
    private String desc;
    
    public long getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public Posting() {}
    public Posting(long id, String desc) {
    
        this.id = id;
        this.desc = desc;
    }


}
