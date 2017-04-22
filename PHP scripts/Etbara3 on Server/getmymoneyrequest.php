<?php

$jsonArr = file_get_contents('php://input');
$jsonArr = json_decode($jsonArr, true);
$profile_id = $jsonArr[0]['profile_id'];

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$query = "SELECT* FROM requestmoney WHERE profile_id = '$profile_id'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}

$result = array();
while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

for ($i = 0, $len = count($result); $i < $len; $++i) {
	$org_id = $result[$i]['org_id'];
	$query = "SELECT name FROM organization WHERE org_id = '$org_id'";
	$output = mysqli_query($conn, $query);
    $result[$i]['org_id'] = mysqli_fetch_object($output)->name;

    $cause_id = $result[$i]['cause_id'];
	$query = "SELECT name FROM cause WHERE cause_id = '$cause_id'";
	$output = mysqli_query($conn, $query);
    $result[$i]['cause_id'] = mysqli_fetch_object($output)->name;
}

$encodedResult = json_encode($result);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
