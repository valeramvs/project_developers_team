/*
 * @(#)Specification.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.project;

import by.epam.outercourse.project.devteam.entity.Entity;

import java.util.ArrayList;
import java.util.Objects;

public class Specification extends Entity {
    /**
     * Specification class has got id, bill, idClient a idManager class variables.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;
    private double bill;
    private int idClient;
    private int idManager;
    private ArrayList<Task> tasks;

    public Specification() {
    }

    public Specification(int idClient) {
        this.idClient = idClient;
    }

    public Specification(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Specification(int id, double bill, int idClient, int idManager) {
        this.id = id;
        this.bill = bill;
        this.idClient = idClient;
        this.idManager = idManager;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specification that = (Specification) o;
        return id == that.id &&
                Double.compare(that.bill, bill) == 0 &&
                idClient == that.idClient &&
                idManager == that.idManager &&
                Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bill, idClient, idManager, tasks);
    }

    @Override
    public String toString() {
        return "Specification{" +
                "id=" + id +
                ", bill=" + bill +
                ", idClient=" + idClient +
                ", idManager=" + idManager +
                '}';
    }
}
