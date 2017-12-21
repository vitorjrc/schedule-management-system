-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`student` (
  `id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `regimen` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`teacher` (
  `id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`course` (
  `id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `teacher_id` VARCHAR(45) NOT NULL,
  INDEX `fk_Courses_Teachers1_idx` (`teacher_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Courses_Teachers1`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `mydb`.`teacher` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`shift` (
  `id` VARCHAR(45) NOT NULL,
  `occupation_limit` INT NOT NULL,
  `teacher` VARCHAR(45) NULL,
  `classroom` VARCHAR(45) NULL,
  `course_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Shift_Course1_idx` (`course_id` ASC),
  CONSTRAINT `fk_Shift_Course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `mydb`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`swap`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`swap` (
  `id` VARCHAR(45) NOT NULL,
  `bidder_id` VARCHAR(45) NOT NULL,
  `taker_id` VARCHAR(45) NULL,
  `shift_offered_id` VARCHAR(45) NOT NULL,
  `shift_wanted_id` VARCHAR(45) NOT NULL,
  `date_created` VARCHAR(45) NOT NULL,
  `date_taken` VARCHAR(45) NULL,
  `is_closed` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `shift_offered_id_idx` (`shift_offered_id` ASC),
  INDEX `shift_wanted_id_idx` (`shift_wanted_id` ASC),
  INDEX `fk_swap_student1_idx` (`bidder_id` ASC),
  INDEX `taker_id_idx` (`taker_id` ASC),
  CONSTRAINT `shift_offered_id`
    FOREIGN KEY (`shift_offered_id`)
    REFERENCES `mydb`.`shift` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `shift_wanted_id`
    FOREIGN KEY (`shift_wanted_id`)
    REFERENCES `mydb`.`shift` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_swap_student`
    FOREIGN KEY (`bidder_id`)
    REFERENCES `mydb`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `taker_id`
    FOREIGN KEY (`taker_id`)
    REFERENCES `mydb`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`student_shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`student_shift` (
  `student_id` VARCHAR(45) NOT NULL,
  `shift_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`student_id`, `shift_id`),
  INDEX `fk_Student_has_Shift_Shift1_idx` (`shift_id` ASC),
  INDEX `fk_Student_has_Shift_Student_idx` (`student_id` ASC),
  CONSTRAINT `fk_Student_has_Shift_Student`
    FOREIGN KEY (`student_id`)
    REFERENCES `mydb`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Student_has_Shift_Shift1`
    FOREIGN KEY (`shift_id`)
    REFERENCES `mydb`.`shift` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
