/*
 * @(#)Bill.java   1.0 2019/11/18
 *
 * Copyright (c) 2019
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.entity.project;

public class Bill {
    /**
     * Bill class has got the total project's bill.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private double projectBill;

    public Bill() {
    }

    public Bill(double projectBill) {
        this.projectBill = projectBill;
    }

    public double getProjectBill() {
        return projectBill;
    }

    public void setProjectBill(double projectBill) {
        this.projectBill = projectBill;
    }

}
