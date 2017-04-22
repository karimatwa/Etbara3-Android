<?php

$jsonArr = file_get_contents('php://input');
$jsonArr = json_decode($jsonArr, true);
$org_id = $jsonArr[0]['org'];


$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}


$query = "SELECT* FROM event WHERE org_id = '$org_id'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}

$result = array();
while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

//Encode it
$encodedResult = json_encode($result);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
