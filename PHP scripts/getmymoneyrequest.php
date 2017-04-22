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

$query = "SELECT* FROM requestmoney WHERE profile_id = '$profile_id'";
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

    $cause_id = $output[$i]['cause_id'];
	$query = "SELECT name FROM cause WHERE cause_id = '$cause_id'";
	$result = mysqli_query($conn, $query);
    $output[$i]['cause_id'] = mysqli_fetch_object($result)->name;
}

$encodedResult = json_encode($output);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
