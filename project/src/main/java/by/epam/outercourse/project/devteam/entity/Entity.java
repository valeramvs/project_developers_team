/*
 * @(#)Entity.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable, Cloneable {
    /**
     * Entity class is abstract class for all entities of program.
     * Entity has got class variable 'id'.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
