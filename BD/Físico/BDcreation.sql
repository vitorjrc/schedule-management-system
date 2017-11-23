-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema TrocaTurnos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TrocaTurnos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TrocaTurnos` DEFAULT CHARACTER SET utf8 ;
USE `TrocaTurnos` ;

-- -----------------------------------------------------
-- Table `TrocaTurnos`.`Docente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TrocaTurnos`.`Docente` (
  `Numero` INT NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `Escola` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Numero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TrocaTurnos`.`UC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TrocaTurnos`.`UC` (
  `Codigo` INT NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `Ano` INT NOT NULL,
  `ECTS` INT NOT NULL,
  PRIMARY KEY (`Codigo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = cp1250;


-- -----------------------------------------------------
-- Table `TrocaTurnos`.`Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TrocaTurnos`.`Aluno` (
  `Numero` INT NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `Curso` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Numero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TrocaTurnos`.`DocenteUC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TrocaTurnos`.`DocenteUC` (
  `Docente_Numero` INT NOT NULL,
  `UC_Codigo` INT NOT NULL,
  INDEX `fk_DocenteUC_Docente_idx` (`Docente_Numero` ASC),
  INDEX `fk_DocenteUC_UC1_idx` (`UC_Codigo` ASC),
  PRIMARY KEY (`Docente_Numero`, `UC_Codigo`),
  CONSTRAINT `fk_DocenteUC_Docente`
    FOREIGN KEY (`Docente_Numero`)
    REFERENCES `TrocaTurnos`.`Docente` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DocenteUC_UC1`
    FOREIGN KEY (`UC_Codigo`)
    REFERENCES `TrocaTurnos`.`UC` (`Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TrocaTurnos`.`UCAluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TrocaTurnos`.`UCAluno` (
  `AnoLetivo` INT NOT NULL,
  `Turno` VARCHAR(45) NOT NULL,
  `UC_Codigo` INT NOT NULL,
  `Aluno_Numero` INT NOT NULL,
  PRIMARY KEY (`AnoLetivo`, `Turno`, `UC_Codigo`, `Aluno_Numero`),
  INDEX `fk_UCAluno_UC1_idx` (`UC_Codigo` ASC),
  INDEX `fk_UCAluno_Aluno1_idx` (`Aluno_Numero` ASC),
  CONSTRAINT `fk_UCAluno_UC1`
    FOREIGN KEY (`UC_Codigo`)
    REFERENCES `TrocaTurnos`.`UC` (`Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UCAluno_Aluno1`
    FOREIGN KEY (`Aluno_Numero`)
    REFERENCES `TrocaTurnos`.`Aluno` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
