<?php

include 'Patient.php';


if(isset($_GET['prescription'])) { echo getAllPrescription($_GET['patient_id']); die(); }

if(isset($_GET['uploads'])) { echo getAllUploads($_GET['patient_id']); die(); }

if(isset($_GET['patient_id'])) echo getPatient($_GET['patient_id']);


if(isset($_GET['Aadhar'])) echo getAllAadhar();



if(isset($_GET['AddPatient'])){
	$json = $_GET['AddPatient'];
	$obj = json_decode($json);
	echo addPatient($obj->patient_id,$obj->patient_name,$obj->patient_pass,$obj->patient_email);
}

?>