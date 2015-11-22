SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `t2sid` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `t2sid` ;

-- -----------------------------------------------------
-- Table `t2sid`.`Person`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `t2sid`.`Person` (
  `idPerson` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idPerson`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `t2sid`.`Championship`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `t2sid`.`Championship` (
  `idChampionship` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `finished` TINYINT(1) NOT NULL ,
  `fkPerson` INT NOT NULL ,
  PRIMARY KEY (`idChampionship`) ,
  CONSTRAINT `fk_Championship_Person`
    FOREIGN KEY (`fkPerson` )
    REFERENCES `t2sid`.`Person` (`idPerson` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Championship_Person_idx` ON `t2sid`.`Championship` (`fkPerson` ASC) ;


-- -----------------------------------------------------
-- Table `t2sid`.`Stage`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `t2sid`.`Stage` (
  `idStage` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `nTeams` INT NULL ,
  `fkChampionship` INT NULL ,
  PRIMARY KEY (`idStage`) ,
  CONSTRAINT `fk_Stage_Championship1`
    FOREIGN KEY (`fkChampionship` )
    REFERENCES `t2sid`.`Championship` (`idChampionship` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Stage_Championship1_idx` ON `t2sid`.`Stage` (`fkChampionship` ASC) ;


-- -----------------------------------------------------
-- Table `t2sid`.`Team`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `t2sid`.`Team` (
  `idTeam` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `fkChampionship` INT NULL ,
  PRIMARY KEY (`idTeam`) ,
  CONSTRAINT `fk_Team_Championship1`
    FOREIGN KEY (`fkChampionship` )
    REFERENCES `t2sid`.`Championship` (`idChampionship` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Team_Championship1_idx` ON `t2sid`.`Team` (`fkChampionship` ASC) ;


-- -----------------------------------------------------
-- Table `t2sid`.`Game`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `t2sid`.`Game` (
  `idGame` INT NOT NULL AUTO_INCREMENT ,
  `number` INT NOT NULL ,
  `homeScore` INT NULL ,
  `visitScore` INT NULL ,
  `fkStage` INT NULL ,
  `fkHome` INT NULL ,
  `fkVisitor` INT NULL ,
  PRIMARY KEY (`idGame`) ,
  CONSTRAINT `fk_Game_Stage1`
    FOREIGN KEY (`fkStage` )
    REFERENCES `t2sid`.`Stage` (`idStage` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Game_Team1`
    FOREIGN KEY (`fkHome` )
    REFERENCES `t2sid`.`Team` (`idTeam` )
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Game_Team2`
    FOREIGN KEY (`fkVisitor` )
    REFERENCES `t2sid`.`Team` (`idTeam` )
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Game_Stage1_idx` ON `t2sid`.`Game` (`fkStage` ASC) ;

CREATE INDEX `fk_Game_Team1_idx` ON `t2sid`.`Game` (`fkHome` ASC) ;

CREATE INDEX `fk_Game_Team2_idx` ON `t2sid`.`Game` (`fkVisitor` ASC) ;

USE `t2sid` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
