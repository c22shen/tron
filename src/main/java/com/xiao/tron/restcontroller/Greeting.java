package com.xiao.tron.restcontroller;

public class Greeting {
    
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Greeting() { }
    
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
