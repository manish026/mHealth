<?php

include 'Patient.php';

$file_path = getcwd()."/uploads/";
$server_ip = gethostbyname($_SERVER['HTTP_HOST']);
$upload_url = 'http://'.$server_ip.'/mhealth/uploads/'.basename( $_FILES['uploaded_file']['name']);

if(isset($_POST['type'])){
	if(isset($_POST['doctor_id'])){
		$file_path  = getcwd()."/".$_POST['type']."/".$_POST['doctor_id']."/";
		$upload_url = 'http://'.$server_ip.'/mhealth/'.$_POST['type'].'/'.$_POST['doctor_id'].'/'.basename( $_FILES['uploaded_file']['name']);
	}
	if(isset($_POST['pharmacy_id'])){
		$file_path  = getcwd()."/".$_POST['type']."/".$_POST['pharmacy_id']."/";
		$upload_url = 'http://'.$server_ip.'/mhealth/'.$_POST['type'].'/'.$_POST['pharmacy_id'].'/'.basename( $_FILES['uploaded_file']['name']);
	}
	if(isset($_POST['patient_id'])){
		$file_path  = getcwd()."/".$_POST['type']."/".$_POST['patient_id']."/";
		$upload_url = 'http://'.$server_ip.'/mhealth/'.$_POST['type'].'/'.$_POST['patient_id'].'/'.basename( $_FILES['uploaded_file']['name']);
		echo $upload_url;
	}
	
	
 }

if (!file_exists($file_path)) {
    mkdir($file_path, 0777, true);
}

$file_path = $file_path . basename( $_FILES['uploaded_file']['name']);
if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
	
	if(strcmp($_POST['type'], 'doctorlicense') == 0){
		updateDoctorLicese($_POST['doctor_id'],$upload_url);
	}else if(strcmp($_POST['type'], 'pharmacylicense') == 0){
		updatePharmacyLicese($_POST['pharmacy_id'],$upload_url);
	}else{
		patientUpload($_POST['patient_id'],$upload_url);
	}
    
} else{
    echo "fail";
}?>