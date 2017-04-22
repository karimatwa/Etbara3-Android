<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$code = $json['code'];
$city = $json['city'];
$email = $json['email'];
$password = $json['password'];
$name = $json['name'];
$phone = $json['phone'];
$address = $json['address'];

$city_id = -1;
$profile_id = -1;
$admin = 0;

$query = "SELECT * FROM verification WHERE code = '$code' AND email = '$email'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 2}';
	exit;
}
// we need to delete the verification when found
$query = "DELETE FROM verification WHERE email = '$email'";
mysqli_query($conn, $query);
// get the city
$query = "SELECT city_id FROM city WHERE name = '$city'";
$output = mysqli_query($conn, $query);

if (mysqli_num_rows($output) == '0') {
	$query = "INSERT INTO city (name) VALUES ('$city')";
	mysqli_query($conn, $query);
	$city_id = mysqli_insert_id($conn);
}
else {
	$city_id = mysqli_fetch_object($output)->city_id;
}

//Inserting Profile information to Profile Table in database.
$insert_query = "INSERT INTO profile (city_id, email, password, name, phone, address, admin) VALUES ('$city_id', '$email', '$password', '$name', '$phone', '$address', '$admin')";
mysqli_query($conn, $insert_query);

$profile_id = mysqli_insert_id($conn);

mysqli_close($conn);
echo '{"Status" : 1, "profile_id": "'.$profile_id.'", "admin": "'.$admin.'"}';



?>
