/*
 * @(#)BillService.java   1.0 2020/01/17
 *
 * Copyright (c) 2020
 * GSTU, Gomel, Republic of Belarus.
 * All Rights Reserved.
 */

package by.epam.outercourse.project.devteam.service;

import by.epam.outercourse.project.devteam.dao.QualificationDAO;
import by.epam.outercourse.project.devteam.dao.SpecificationDAO;
import by.epam.outercourse.project.devteam.entity.developer.Developer;
import by.epam.outercourse.project.devteam.entity.project.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BillService {
    /**
     * BillService class has the method for counting developer's payment.
     *
     * @version     1.0
     * @author      Valery Mazurau
     */

    private static final Logger logger = LogManager.getLogger(BillService.class);

    /**
     * SALARY_PER_HOUR is the value of payment per hour for developer.
     * Final developer's salary depends on qualification, but can't be more than value of SALARY_PER_HOUR,
     * because maximum skill's coefficient is 1.0 (for team-leader).
     */
    public static final int SALARY_PER_HOUR = 20;

    public void countBill(Developer developer) {
        SpecificationDAO specificationDAO = new SpecificationDAO();
        Specification specification = specificationDAO.findEntityById(developer.getIdSpec());
        double bill = specification.getBill();
        QualificationDAO qualificationDAO = new QualificationDAO();
        double skill = qualificationDAO.findSkillByQualification(developer.getQualification()).getSkill();
        double billDeveloper = skill * SALARY_PER_HOUR * developer.getWorkTime();
        bill += billDeveloper;
        bill = new BigDecimal(bill).setScale(2, RoundingMode.UP).doubleValue();
        specification.setBill(bill);
        specificationDAO.update(specification);
        logger.info("Developer's payment was calculated");
    }

}
