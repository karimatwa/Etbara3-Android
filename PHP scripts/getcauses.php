<?php

$jsonArr = file_get_contents('php://input');
$jsonArr = json_decode($jsonArr, true);
$json = $jsonArr[0];


$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$org_id = $json['org'];

$query = "SELECT* FROM cause WHERE org_id = '$org_id'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}
//Options MYSQL_BOTH MYSQL_NUM MYSQL_ASSOC
$output = $output->fetch_all(MYSQL_ASSOC);

//Encode it
$encodedResult = json_encode($output);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
