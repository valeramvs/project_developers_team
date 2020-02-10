/*
 * @(#)Qualification.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.developer;

import by.epam.outercourse.project.devteam.entity.Entity;

import java.util.Objects;

public class Qualification extends Entity {
    /**
     * Each qualification corresponds to the skill.
     * The higher the skill, the higher developer's payment.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;
    private String qualification;
    private double skill;

    public Qualification() {
    }

    public Qualification(String qualification, double skill) {
        this.qualification = qualification;
        this.skill = skill;
    }

    public Qualification(String qualification) {
        this.qualification = qualification;
    }

    public Qualification(int id, String qualification, double skill) {
        this.id = id;
        this.qualification = qualification;
        this.skill = skill;
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

    public double getSkill() {
        return skill;
    }

    public void setSkill(double skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Qualification{" +
                "id=" + id +
                ", qualification='" + qualification + '\'' +
                ", skill=" + skill +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualification that = (Qualification) o;
        return id == that.id &&
                Double.compare(that.skill, skill) == 0 &&
                Objects.equals(qualification, that.qualification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qualification, skill);
    }
}
