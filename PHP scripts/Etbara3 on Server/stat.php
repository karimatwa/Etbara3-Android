<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);
$method = $_SERVER['REQUEST_METHOD'];

$data = $json['data'];
$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

if ($method == 'GET')
{
	$query = "SELECT data FROM statistics WHERE 1";
	$output = mysqli_query($conn, $query);

	if (mysqli_num_rows($output) == '0') {
		echo '{"Status" : 0}';
		exit;
	}

	$data = mysqli_fetch_object($output)->data;
	echo '{"Status": 1, "data": "'.$data.'"}';
}
else
{
	$query = "UPDATE statistics SET data = '$data' WHERE 1";
	$output = mysqli_query($conn, $query);
	echo '{"Status": 2}';
}

mysqli_close($conn);
?>