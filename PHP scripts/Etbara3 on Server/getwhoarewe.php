<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$org_id = $json['org'];

$query = "SELECT name FROM organization WHERE org_id = '$org_id'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}

else
{
	$name = mysqli_fetch_object($output)->name;

	$query = "SELECT description FROM organization WHERE org_id = '$org_id'";
	$output = mysqli_query($conn, $query);
	$description = mysqli_fetch_object($output)->description;

	$query = "SELECT phone FROM organization WHERE org_id = '$org_id'";
	$output = mysqli_query($conn, $query);
	$phone = mysqli_fetch_object($output)->phone;

	echo '{ "name": "'.$name.'", "description": "'.$description.'", "phone": "'.$phone.'"}';
}

//Return the json string

mysqli_close($conn);
?>
