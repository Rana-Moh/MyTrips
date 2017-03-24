-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema trips
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trips
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trips` DEFAULT CHARACTER SET utf8 ;
USE `trips` ;

-- -----------------------------------------------------
-- Table `trips`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trips`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `fullName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `trips`.`trips`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trips`.`trips` (
  `id` VARCHAR(45) NOT NULL,
  `userId` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `start` VARCHAR(45) NULL DEFAULT NULL,
  `startX` DOUBLE NULL DEFAULT NULL,
  `startY` DOUBLE NULL DEFAULT NULL,
  `end` VARCHAR(45) NULL DEFAULT NULL,
  `endX` DOUBLE NULL DEFAULT NULL,
  `endY` DOUBLE NULL DEFAULT NULL,
  `date` VARCHAR(45) NULL DEFAULT NULL,
  `time` VARCHAR(45) NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  `done` TINYINT(1) NULL DEFAULT NULL,
  `image` VARCHAR(100) NULL DEFAULT NULL,
  `alarmId` INT(11) NULL DEFAULT NULL,
  `milliSeconds` DECIMAL(21,0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userFK_idx` (`userId` ASC),
  CONSTRAINT `userFK`
    FOREIGN KEY (`userId`)
    REFERENCES `trips`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `trips`.`notes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trips`.`notes` (
  `id` VARCHAR(45) NOT NULL,
  `tripId` VARCHAR(45) NULL DEFAULT NULL,
  `note` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `tripFK_idx` (`tripId` ASC),
  CONSTRAINT `tripFK`
    FOREIGN KEY (`tripId`)
    REFERENCES `trips`.`trips` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
