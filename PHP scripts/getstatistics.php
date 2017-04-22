<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$query = "SELECT data FROM statistics WHERE 1";
$output = mysqli_query($conn, $query);

if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}

$data = mysqli_fetch_object($output)->data;
echo '{"data": "'.$data.'"}';

mysqli_close($conn);
?>