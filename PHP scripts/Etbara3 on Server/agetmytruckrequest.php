<?php

$jsonArr = file_get_contents('php://input');
$jsonArr = json_decode($jsonArr, true);


$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$query = "SELECT* FROM requesttruck WHERE 1";
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


    $profile_id = $result[$i]['profile_id'];

	$query = "SELECT name FROM profile WHERE profile_id = '$profile_id'";
	$output = mysqli_query($conn, $query);
	$name = mysqli_fetch_object($output)->name;

	$query = "SELECT phone FROM profile WHERE profile_id = '$profile_id'";
	$output = mysqli_query($conn, $query);
	$phone = mysqli_fetch_object($output)->phone;

	$query = "SELECT address FROM profile WHERE profile_id = '$profile_id'";
	$output = mysqli_query($conn, $query);
	$address = mysqli_fetch_object($output)->address;

	$query = "SELECT city_id FROM profile WHERE profile_id = '$profile_id'";
	$output = mysqli_query($conn, $query);
	$city_id = mysqli_fetch_object($output)->city_id;

	$query = "SELECT name FROM city WHERE city_id = '$city_id'";
	$output = mysqli_query($conn, $query);
	$city = mysqli_fetch_object($output)->name;

    $result[$i]['profile_id'] = "Name: ".$name."\nPhone: ".$phone."\nAddress: ".$address ."\nCity: ".$city;
}

$encodedResult = json_encode($result);

//Return the json string
echo $encodedResult;
mysqli_close($conn);
?>
