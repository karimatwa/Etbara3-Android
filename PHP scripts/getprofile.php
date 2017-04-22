<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"status" : 1}';
	exit;
}
$profile_id = $json['profile_id'];
$query = "SELECT* FROM profile WHERE profile_id = '$profile_id'";
$output = mysqli_query($conn, $query);

if (mysqli_num_rows($output) != '0') {

	$query = "SELECT city_id FROM profile WHERE profile_id = $profile_id";
	$output = mysqli_query($conn, $query);
	$city_id = mysqli_fetch_object($output)->city_id;

	$query = "SELECT name FROM city WHERE city_id = $city_id";
	$output = mysqli_query($conn, $query);
	$city = mysqli_fetch_object($output)->name;

	$query = "SELECT email FROM profile WHERE profile_id = $profile_id";
	$output = mysqli_query($conn, $query);
	$email = mysqli_fetch_object($output)->email;

	$query = "SELECT name FROM profile WHERE profile_id = $profile_id";
	$output = mysqli_query($conn, $query);
	$name = mysqli_fetch_object($output)->name;

	$query = "SELECT password FROM profile WHERE profile_id = $profile_id";
	$output = mysqli_query($conn, $query);
	$password = mysqli_fetch_object($output)->password;

	$query = "SELECT phone FROM profile WHERE profile_id = $profile_id";
	$output = mysqli_query($conn, $query);
	$phone = mysqli_fetch_object($output)->phone;

	$query = "SELECT address FROM profile WHERE profile_id = $profile_id";
	$output = mysqli_query($conn, $query);
	$address = mysqli_fetch_object($output)->address;

	
	echo '{ "status": 1, "city": "'.$city.'", "name": "'.$name.'" , "email": "'.$email.'", "password": "'.$password.'", "phone": "'.$phone.'", "address": "'.$address.'"}';
} else {
	echo '{"status" : 2}';
}

?>
