/*
 * @(#)Manager.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.manager;

import by.epam.outercourse.project.devteam.entity.Entity;

import java.util.Objects;

public class Manager extends Entity {
    /**
     * Manager class has got id, name and email.
     * A manager creates a project, appoints developers to tasks and introduces the bill for a client.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;
    private String name;
    private String email;

    public Manager() {
    }

    public Manager(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Manager(int id, String name, String email) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return id == manager.id &&
                Objects.equals(name, manager.name) &&
                Objects.equals(email, manager.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
