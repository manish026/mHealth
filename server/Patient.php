<?php

include 'DBconnection.php';


//  Returns JSON of Patient 

	function getPatient($patient){
		
		$sql = "SELECT * FROM ".$GLOBALS['patientTable']." WHERE patient_id = ".$patient;
		$result = executeQuery($sql);

		return getJSON($result);

	}

	//  Insert a patient 

	function addPatient($id,$name,$pass,$email){
		
		$json = getPatient($id);
		$obj = json_decode($json);
		if(count($obj)>0){

			$res = new Result();
			$res->status = false;
			$res->message = "User already exists";

			return json_encode($res);
		}else{
			$sql = "INSERT INTO ".$GLOBALS['patientTable'].
			" (".$GLOBALS['patient_id'].", ".$GLOBALS['patient_name'].", ".$GLOBALS['patient_pass'].", ".$GLOBALS['patient_email']." 
			) VALUES ('".$id."', '".$name."', '".$pass."', '".$email."') ";
			$result = executeQuery($sql);
			$res = new Result();
			$res->status = true;
			$res->message = "Registration Succesfull";
			return json_encode($res);
		}

	}

	//  Returns JSON of aadhar id 

	function getAllAadhar(){
		
		$sql = "SELECT patient_id FROM ".$GLOBALS['patientTable'];
		$result = executeQuery($sql);

		return getJSON($result);

	}
	
	
	
	//  Returns JSON of Doctor 

	function getDoctor($patient){
		$sql = "SELECT * FROM ".$GLOBALS['doctorTable']." WHERE doctor_id = ".$patient;
		$result = executeQuery($sql);

		return getJSON($result);

	}
	
	//  Insert a doctor 

	function addDoctor($id,$name,$pass,$email,$specialisation){
		
		$json = getDoctor($id);
		$obj = json_decode($json);
		if(count($obj)>0){

			$res = new Result();
			$res->status = false;
			$res->message = "User already exists";

			return json_encode($res);
		}else{
			$sql = "INSERT INTO ".$GLOBALS['doctorTable'].
			" (".$GLOBALS['doctor_id'].", ".$GLOBALS['doctor_name'].", ".$GLOBALS['doctor_pass'].", ".$GLOBALS['doctor_email'].", ".$GLOBALS['doctor_specialisation']." 
			) VALUES ('".$id."', '".$name."', '".$pass."', '".$email."', '".$specialisation."') ";
			$result = executeQuery($sql);
			$res = new Result();
			$res->status = true;
			$res->message = "Registration Succesfull";
			return json_encode($res);
		}

	}
	
	
	//  Returns JSON of Patient 

	function getPharmacy($patient){
		$sql = "SELECT * FROM ".$GLOBALS['pharmacyTable']." WHERE pharmacy_id = ".$patient;
		$result = executeQuery($sql);

		return getJSON($result);

	}
	
	//  Insert a doctor 

	function addPharmacy($id,$name,$pass,$email,$address){
		
		$json = getPharmacy($id);
		$obj = json_decode($json);
		if(count($obj)>0){

			$res = new Result();
			$res->status = false;
			$res->message = "User already exists";

			return json_encode($res);
		}else{
			$sql = "INSERT INTO ".$GLOBALS['pharmacyTable'].
			" (".$GLOBALS['pharmacy_id'].", ".$GLOBALS['pharmacy_name'].", ".$GLOBALS['pharmacy_pass'].", ".$GLOBALS['pharmacy_email'].", ".$GLOBALS['pharmacy_address']." 
			) VALUES ('".$id."', '".$name."', '".$pass."', '".$email."', '".$address."') ";
			$result = executeQuery($sql);
			$res = new Result();
			$res->status = true;
			$res->message = "Registration Succesfull";
			return json_encode($res);
		}

	}
	
	function updateDoctorLicese($id,$url){
		$sql = "UPDATE ".$GLOBALS['doctorTable']." SET ".$GLOBALS['doctor_license']."='".$url."' WHERE ".$GLOBALS['doctor_id']."=".$id;
			$result = executeQuery($sql);
			
	}
	
	function updatePharmacyLicese($id,$url){
		$sql = "UPDATE ".$GLOBALS['pharmacyTable']." SET ".$GLOBALS['pharmacy_license']."='".$url."' WHERE ".$GLOBALS['pharmacy_id']."=".$id;
			$result = executeQuery($sql);
			
	}
	
	function patientUpload($id,$url){
		$sql = "INSERT INTO ".$GLOBALS['patientuploadTable'].
			" (".$GLOBALS['patient_id'].", ".$GLOBALS['patient_url']." 
			) VALUES ('".$id."', '".$url."') ";
			$result = executeQuery($sql);
			
	}
	
	function uploadPrescription($id,$prescription){
		$sql = "INSERT INTO ".$GLOBALS['prescriptionTable'].
			" (".$GLOBALS['patient_id'].", ".$GLOBALS['patient_presciption']." 
			) VALUES ('".$id."', '".$prescription."') ";
			$result = executeQuery($sql);
			
	}
	
	function getAllPrescription($id){
		$sql = "SELECT * from ".$GLOBALS['prescriptionTable']." WHERE ".$GLOBALS['patient_id']."=".$id." ORDER BY prescription_date DESC";
		$result = executeQuery($sql);
		return getJSON($result);
	}
	
	function getAllUploads($id){
		$sql = "SELECT * from ".$GLOBALS['patientuploadTable']." WHERE ".$GLOBALS['patient_id']."=".$id." ORDER BY time DESC";
		$result = executeQuery($sql);
		return getJSON($result);
	}

?>