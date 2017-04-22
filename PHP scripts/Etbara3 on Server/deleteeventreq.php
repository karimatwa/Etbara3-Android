<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}
$volunteer_id = $json['volunteer_id'];

$query = "DELETE FROM volunteer WHERE volunteer_id = '$volunteer_id'";
$output = mysqli_query($conn, $query);

echo '{"Status":1}';

mysqli_close($conn);
?>