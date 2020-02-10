-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema devteam
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema devteam
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `devteam` DEFAULT CHARACTER SET utf8 ;
USE `devteam` ;

-- -----------------------------------------------------
-- Table `devteam`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devteam`.`clients` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 83
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `devteam`.`managers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devteam`.`managers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 58
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `devteam`.`specs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devteam`.`specs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `bill` DOUBLE NULL DEFAULT NULL,
  `client_id` INT(11) NOT NULL,
  `manager_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `client_id_idx` (`client_id` ASC) VISIBLE,
  INDEX `manager_id_idx` (`manager_id` ASC) VISIBLE,
  CONSTRAINT `client_id`
    FOREIGN KEY (`client_id`)
    REFERENCES `devteam`.`clients` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `manager_id`
    FOREIGN KEY (`manager_id`)
    REFERENCES `devteam`.`managers` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 115
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `devteam`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devteam`.`tasks` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `team_leaders` INT(11) NULL DEFAULT NULL,
  `seniors` INT(11) NULL DEFAULT NULL,
  `middles` INT(11) NULL DEFAULT NULL,
  `juniors` INT(11) NULL DEFAULT NULL,
  `spec_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `spec_id_idx` (`spec_id` ASC) VISIBLE,
  CONSTRAINT `spec_id`
    FOREIGN KEY (`spec_id`)
    REFERENCES `devteam`.`specs` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 134
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `devteam`.`developers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devteam`.`developers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `qualification` VARCHAR(45) NOT NULL,
  `worktime` DOUBLE NULL DEFAULT NULL,
  `spec_id` INT(11) NULL DEFAULT NULL,
  `task_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `task_id_idx` (`task_id` ASC) VISIBLE,
  INDEX `spec_id_idx` (`spec_id` ASC) VISIBLE,
  INDEX `worktime_idx` (`worktime` ASC) VISIBLE,
  CONSTRAINT `spec_to_task_id`
    FOREIGN KEY (`spec_id`)
    REFERENCES `devteam`.`specs` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `task_id`
    FOREIGN KEY (`task_id`)
    REFERENCES `devteam`.`tasks` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 605
DEFAULT CHARACTER SET = utf8;

--
-- Dumping data for table `developers`
--

LOCK TABLES `developers` WRITE;
/*!40000 ALTER TABLE `developers` DISABLE KEYS */;
INSERT INTO `developers` VALUES (1,'TEAM_LEAD',0,NULL,NULL),(2,'TEAM_LEAD',0,NULL,NULL),(3,'TEAM_LEAD',0,NULL,NULL),(4,'TEAM_LEAD',0,NULL,NULL),(5,'TEAM_LEAD',0,NULL,NULL),(6,'SENIOR',0,NULL,NULL),(7,'SENIOR',0,NULL,NULL),(8,'SENIOR',0,NULL,NULL),(9,'SENIOR',0,NULL,NULL),(10,'SENIOR',0,NULL,NULL),(11,'MIDDLE',0,NULL,NULL),(12,'MIDDLE',0,NULL,NULL),(13,'MIDDLE',0,NULL,NULL),(14,'MIDDLE',0,NULL,NULL),(15,'MIDDLE',0,NULL,NULL),(16,'JUNIOR',0,NULL,NULL),(17,'JUNIOR',0,NULL,NULL),(18,'JUNIOR',0,NULL,NULL),(19,'JUNIOR',0,NULL,NULL),(20,'JUNIOR',0,NULL,NULL),(49,'TEAM_LEAD',0,NULL,NULL),(50,'SENIOR',0,NULL,NULL),(51,'MIDDLE',0,NULL,NULL),(52,'JUNIOR',0,NULL,NULL),(53,'TEAM_LEAD',0,NULL,NULL),(54,'SENIOR',0,NULL,NULL),(55,'MIDDLE',0,NULL,NULL),(56,'JUNIOR',0,NULL,NULL),(57,'TEAM_LEAD',0,NULL,NULL),(58,'SENIOR',0,NULL,NULL),(59,'MIDDLE',0,NULL,NULL),(60,'JUNIOR',0,NULL,NULL),(61,'TEAM_LEAD',0,NULL,NULL),(62,'SENIOR',0,NULL,NULL),(63,'MIDDLE',0,NULL,NULL),(64,'JUNIOR',0,NULL,NULL),(65,'TEAM_LEAD',0,NULL,NULL),(66,'SENIOR',0,NULL,NULL),(67,'MIDDLE',0,NULL,NULL),(68,'JUNIOR',0,NULL,NULL),(69,'TEAM_LEAD',0,NULL,NULL),(70,'SENIOR',0,NULL,NULL),(71,'MIDDLE',0,NULL,NULL),(72,'JUNIOR',0,NULL,NULL),(73,'TEAM_LEAD',0,NULL,NULL),(74,'SENIOR',0,NULL,NULL),(75,'MIDDLE',0,NULL,NULL),(76,'JUNIOR',0,NULL,NULL),(77,'TEAM_LEAD',0,NULL,NULL),(78,'SENIOR',0,NULL,NULL),(79,'MIDDLE',0,NULL,NULL),(80,'JUNIOR',0,NULL,NULL),(81,'TEAM_LEAD',0,NULL,NULL),(82,'SENIOR',0,NULL,NULL),(83,'MIDDLE',0,NULL,NULL),(84,'JUNIOR',0,NULL,NULL),(85,'TEAM_LEAD',0,NULL,NULL),(86,'SENIOR',0,NULL,NULL),(87,'MIDDLE',0,NULL,NULL),(88,'JUNIOR',0,NULL,NULL),(89,'TEAM_LEAD',0,NULL,NULL),(90,'SENIOR',0,NULL,NULL),(91,'MIDDLE',0,NULL,NULL),(92,'JUNIOR',0,NULL,NULL),(93,'TEAM_LEAD',0,NULL,NULL),(94,'SENIOR',0,NULL,NULL),(95,'MIDDLE',0,NULL,NULL),(96,'JUNIOR',0,NULL,NULL),(97,'TEAM_LEAD',0,NULL,NULL),(98,'SENIOR',0,NULL,NULL),(99,'MIDDLE',0,NULL,NULL),(100,'JUNIOR',0,NULL,NULL);
/*!40000 ALTER TABLE `developers` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `devteam`.`qualification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `devteam`.`qualification` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `qualification` VARCHAR(45) NOT NULL,
  `skill` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;