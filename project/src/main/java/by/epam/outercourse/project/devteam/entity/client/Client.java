/*
 * @(#)Client.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.client;

import by.epam.outercourse.project.devteam.entity.Entity;

import java.util.Objects;

public class Client extends Entity {
    /**
     * Client class has got id, name and email.
     * A client introduces the specification with tasks to a manager.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;
    private String name;
    private String email;

    public Client() {
    }

    public Client(int id) {
        this.id = id;
    }

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String str = "11";
        str.hashCode();
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                name.equals(client.name) &&
                email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}

