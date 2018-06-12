<?php

require 'DBconnection.php';



executeQuery('CREATE TABLE `Qural`.`Patient` ( `patient_id` VARCHAR NOT NULL , `patient_name` VARCHAR NOT NULL , `patient_pass` VARCHAR NOT NULL , `patient_email` VARCHAR NOT NULL ) ENGINE = InnoDB;');


?>