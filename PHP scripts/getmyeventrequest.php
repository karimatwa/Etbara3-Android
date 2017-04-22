<?php

$jsonArr = file_get_contents('php://input');
$jsonArr = json_decode($jsonArr, true);
$json = $jsonArr[0];


$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$profile_id = $json['profile_id'];

$query = "SELECT* FROM volunteer WHERE profile_id = '$profile_id'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}
//Options MYSQL_BOTH MYSQL_NUM MYSQL_ASSOC
$output = $output->fetch_all(MYSQL_ASSOC);

for ($i = 0, $len = count($output); $i < $len; ++$i) {
	$org_id = $output[$i]['org_id'];
	$query = "SELECT name FROM organization WHERE org_id = '$org_id'";
	$result = mysqli_query($conn, $query);
    $output[$i]['org_id'] = mysqli_fetch_object($result)->name;

    $event_id = $output[$i]['event_id'];
	$query = "SELECT name FROM event WHERE event_id = '$event_id'";
	$result = mysqli_query($conn, $query);
    $output[$i]['event_id'] = mysqli_fetch_object($result)->name;

    $query = "SELECT description FROM event WHERE event_id = '$event_id'";
	$result = mysqli_query($conn, $query);
    $output[$i]['description'] = mysqli_fetch_object($result)->description;

    $query = "SELECT date FROM event WHERE event_id = '$event_id'";
	$result = mysqli_query($conn, $query);
    $output[$i]['date'] = mysqli_fetch_object($result)->date;
}

$encodedResult = json_encode($output);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
