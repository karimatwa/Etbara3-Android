<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}
$request_id = $json['request_id'];

$query = "DELETE FROM requesttruck WHERE request_id = '$request_id'";
$output = mysqli_query($conn, $query);

echo '{"Status":1}';

mysqli_close($conn);
?>