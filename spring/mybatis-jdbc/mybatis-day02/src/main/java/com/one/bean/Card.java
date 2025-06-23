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
    private Person person;

    public Card() {
    }

    public Card(Integer id, String number, Person person) {
        this.id = id;
        this.number = number;
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", person=" + person +
                '}';
    }
}
