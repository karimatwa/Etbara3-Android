<?php

$jsonArr = file_get_contents('php://input');
$jsonArr = json_decode($jsonArr, true);
$profile_id = $jsonArr[0]['profile_id'];

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$query = "SELECT* FROM volunteer WHERE profile_id = '$profile_id'";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}

$result = array();
while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

for ($i = 0, $len = count($result); $i < $len; ++$i) {
	$org_id = $result[$i]['org_id'];
	$query = "SELECT name FROM organization WHERE org_id = '$org_id'";
	$output = mysqli_query($conn, $query);
    $result[$i]['org_id'] = mysqli_fetch_object($output)->name;
    
    $event_id = $result[$i]['event_id'];
	$query = "SELECT name FROM event WHERE event_id = '$event_id'";
	$output = mysqli_query($conn, $query);
	$result[$i]['event_id'] = mysqli_fetch_object($output)->name;

    $query = "SELECT description FROM event WHERE event_id = '$event_id'";
	$output = mysqli_query($conn, $query);
    $result[$i]['description'] = mysqli_fetch_object($output)->description;

    $query = "SELECT date FROM event WHERE event_id = '$event_id'";
	$output = mysqli_query($conn, $query);
    $result[$i]['date'] = mysqli_fetch_object($output)->date;
}

$encodedResult = json_encode($result);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
