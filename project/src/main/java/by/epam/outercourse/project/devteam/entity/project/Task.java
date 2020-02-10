/*
 * @(#)Task.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.project;

import by.epam.outercourse.project.devteam.entity.Entity;

import java.util.Objects;

public class Task extends Entity {
    /**
     * Task class has got id, idSpec class variables and number of each qualified developer for the task.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;
    private int numberOfTeamLeads;
    private int numberOfSeniors;
    private int numberOfMiddles;
    private int numberOfJuniors;
    private int idSpec;

    public Task() {
    }

    public Task(int idSpec) {
        this.idSpec = idSpec;
    }

    public Task(int numberOfTeamLeads, int numberOfSeniors, int numberOfMiddles, int numberOfJuniors, int idSpec) {
        this.numberOfTeamLeads = numberOfTeamLeads;
        this.numberOfSeniors = numberOfSeniors;
        this.numberOfMiddles = numberOfMiddles;
        this.numberOfJuniors = numberOfJuniors;
        this.idSpec = idSpec;
    }

    public Task(int id, int numberOfTeamLeads, int numberOfSeniors, int numberOfMiddles, int numberOfJuniors, int idSpec) {
        this.id = id;
        this.numberOfTeamLeads = numberOfTeamLeads;
        this.numberOfSeniors = numberOfSeniors;
        this.numberOfMiddles = numberOfMiddles;
        this.numberOfJuniors = numberOfJuniors;
        this.idSpec = idSpec;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfTeamLeads() {
        return numberOfTeamLeads;
    }

    public void setNumberOfTeamLeads(int numberOfTeamLeads) {
        this.numberOfTeamLeads = numberOfTeamLeads;
    }

    public int getNumberOfSeniors() {
        return numberOfSeniors;
    }

    public void setNumberOfSeniors(int numberOfSeniors) {
        this.numberOfSeniors = numberOfSeniors;
    }

    public int getNumberOfMiddles() {
        return numberOfMiddles;
    }

    public void setNumberOfMiddles(int numberOfMiddles) {
        this.numberOfMiddles = numberOfMiddles;
    }

    public int getNumberOfJuniors() {
        return numberOfJuniors;
    }

    public void setNumberOfJuniors(int numberOfJuniors) {
        this.numberOfJuniors = numberOfJuniors;
    }

    public int getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                numberOfTeamLeads == task.numberOfTeamLeads &&
                numberOfSeniors == task.numberOfSeniors &&
                numberOfMiddles == task.numberOfMiddles &&
                numberOfJuniors == task.numberOfJuniors &&
                idSpec == task.idSpec;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfTeamLeads, numberOfSeniors, numberOfMiddles, numberOfJuniors, idSpec);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", numberOfTeamLeads=" + numberOfTeamLeads +
                ", numberOfSeniors=" + numberOfSeniors +
                ", numberOfMiddles=" + numberOfMiddles +
                ", numberOfJuniors=" + numberOfJuniors +
                ", idSpec=" + idSpec +
                '}';
    }
}
