<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

// get the post data

$profile_id = $json['profile_id'];
$city = $json['city'];
$password = $json['password'];
$name = $json['name'];
$phone = $json['phone'];
$address = $json['address'];
$city_id = -1;

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
$update_query = "UPDATE profile SET city_id = '$city_id', name = '$name', phone = '$phone', address = '$address', password = '$password' WHERE profile_id = '$profile_id'";
mysqli_query($conn, $update_query);
echo '{"Status": 1}';
mysqli_close($conn);

?>
