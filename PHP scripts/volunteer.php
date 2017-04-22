<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);

$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

// get the post data

$profile_id = $json['profile_id'];
$org_id = $json['org_id'];
$event_id = $json['event_id'];


$insert_query = "SELECT* FROM volunteer WHERE org_id = '$org_id' AND event_id = '$event_id' AND profile_id = '$profile_id'";
$output = mysqli_query($conn, $insert_query);

if (mysqli_num_rows($output) == '0') {
	$insert_query = "INSERT INTO volunteer (org_id, event_id, profile_id) VALUES ('$org_id', '$event_id', '$profile_id')";
	mysqli_query($conn, $insert_query);
	echo '{"Status" : 1}';
}
else
{
	echo '{"Status" : 2}';
}
mysqli_close($conn);

?>
