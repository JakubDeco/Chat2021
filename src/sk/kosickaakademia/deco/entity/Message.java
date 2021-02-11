package sk.kosickaakademia.deco.entity;

import java.util.Date;

public class Message {
    private int id;
    private String from;
    private String to;
    private Date date;
    private String content;

    public Message(int id, String from, String to, Date date, String content) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
