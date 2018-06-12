<?php

include 'Patient.php';


if(isset($_GET['doctor_id'])) echo getDoctor($_GET['doctor_id']);


if(isset($_GET['Aadhar'])) echo getAllAadhar();

if(isset($_GET['prescription'])) echo uploadPrescription($_GET['doctor_id'],$_GET['prescription']);

if(isset($_GET['AddDoctor'])){
	$json = $_GET['AddDoctor'];
	$obj = json_decode($json);
	echo addDoctor($obj->doctor_id,$obj->doctor_name,$obj->doctor_pass,$obj->patient_email,$obj->doctor_specialisation);
}

?> 