<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

// get the post data

$org_id = $json['org_id'];
$name = $json['name'];
$description = $json['description'];
$date = $json['date'];
$currdate = $json['currdate'];

$date1 = date_create($currdate);
$date2 = date_create($date);
$diff=date_diff($date1, $date2);
$diff = $diff->format("%R%a days");

if ($diff < 0)
	{
		echo '{"Status" : 2}';
		exit;
	}

$insert_query = "INSERT INTO event (org_id, name, description, date) VALUES ('$org_id', '$name', '$description', '$date')";
mysqli_query($conn, $insert_query);

echo '{"Status" : 1}';


mysqli_close($conn);

?>
