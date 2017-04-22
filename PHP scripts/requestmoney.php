<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);

$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

// get the post data

$org_id = $json['org_id'];
$cause_id = $json['cause_id'];
$profile_id = $json['profile_id'];
$amount = $json['amount'];
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

$insert_query = "INSERT INTO requestmoney (org_id, cause_id, profile_id, amount, date) VALUES ('$org_id', '$cause_id', '$profile_id', '$amount', '$date')";
mysqli_query($conn, $insert_query);

echo '{"Status" : 1}';
mysqli_close($conn);

?>
