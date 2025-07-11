package com.one.bean;

/**
 * @author one  一对一: 一个身份证号对应唯一的person
 */
public class Card {
    private Integer id;
    /**
     * 身份证号
     */
    private String number;

    private User user;

    public Card() {
    }

    public Card(Integer id, String number, User user) {
        this.id = id;
        this.number = number;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getPerson() {
        return user;
    }

    public void setPerson(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", person=" + user +
                '}';
    }
}
