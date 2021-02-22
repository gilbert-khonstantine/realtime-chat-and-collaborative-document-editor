package com.gideon.realtimetextcall.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String text;

    public Room() {}

    public Room(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Room(long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText(){ return text; }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + '}';
    }
    // standard constructors / setters / getters / toString
}
