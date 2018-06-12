<?php

include 'Patient.php';

if(isset($_GET['Aadhar'])) echo getAllAadhar();

if(isset($_GET['prescription'])) { echo getAllPrescription($_GET['pharmacy_id']); die(); }

if(isset($_GET['uploads'])) { echo getAllUploads($_GET['pharmacy_id']); die(); }

if(isset($_GET['pharmacy_id'])) echo getPharmacy($_GET['pharmacy_id']);

if(isset($_GET['AddPharmacy'])){
	$json = $_GET['AddPharmacy'];
	$obj = json_decode($json);
	echo addPharmacy($obj->pharmacy_id,$obj->pharmacy_name,$obj->pharmacy_pass,$obj->patient_email,$obj->pharmacy_address);
}

?> 