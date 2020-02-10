/*
 * @(#)Developer.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.developer;

import by.epam.outercourse.project.devteam.entity.Entity;

import java.util.Objects;

public class Developer extends Entity {
    /**
     * Developer class has got id, qualification, work time, idSpec and idTask.
     * Qualification of developer may be different, for example 'team-leader', 'senior' etc.
     * Task's bill depends on qualification of developer. Qualification is coefficient for payment.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;
    private String qualification;
    private double workTime;
    private int idSpec;
    private int idTask;

    public Developer() {
    }

    public Developer(int id) {
        this.id = id;
    }

    public Developer(String qualification) {
        this.qualification = qualification;
    }

    public Developer(int id, String qualification) {
        this.id = id;
        this.qualification = qualification;
    }

    public Developer(int id, String qualification, double workTime, int idSpec, int idTask) {
        this.id = id;
        this.qualification = qualification;
        this.workTime = workTime;
        this.idSpec = idSpec;
        this.idTask = idTask;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }

    public int getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", qualification=" + qualification +
                ", workTime=" + workTime +
                ", idSpec=" + idSpec +
                ", idTask=" + idTask +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id &&
                Double.compare(developer.workTime, workTime) == 0 &&
                idSpec == developer.idSpec &&
                idTask == developer.idTask &&
                qualification == developer.qualification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qualification, workTime, idSpec, idTask);
    }
}
