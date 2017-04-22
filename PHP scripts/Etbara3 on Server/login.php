<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

// get the post data

$email = $json['email'];
$password = $json['password'];

$query = "SELECT profile_id FROM profile WHERE email = '$email' AND password = '$password'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 2}';
}
else
{
	$profile_id = mysqli_fetch_object($output)->profile_id;
	$query = "SELECT admin FROM profile WHERE profile_id = '$profile_id'";
	$output = mysqli_query($conn, $query);
	$admin = mysqli_fetch_object($output)->admin;

	echo '{"Status" : 1, "profile_id": "'.$profile_id.'", "admin":"'.$admin.'"}';
}

mysqli_close($conn);


?>
