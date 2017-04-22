<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}
$date1= $json['date'];
$date1 = date_create($date1);

$query = "SELECT* FROM requestmoney WHERE 1";
$output = mysqli_query($conn, $query);

$result = array();
while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

for ($i = 0, $len = count($result); $i < $len; ++$i) {
	$date2 = $result[$i]['date'];
	$date2 = date_create($date2);
	$diff=date_diff($date1, $date2);
	$diff = $diff->format("%R%a days");
	if ($diff < 0)
	{
		$request_id = $result[$i]['request_id'];
		$query = "DELETE FROM requestmoney WHERE request_id = '$request_id'";
		$output = mysqli_query($conn, $query);
	}
}

$query = "SELECT* FROM requesttruck WHERE 1";
$output = mysqli_query($conn, $query);
$result = array();

while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

for ($i = 0, $len = count($result); $i < $len; ++$i) {
	$date2 = $result[$i]['date'];
	$date2 = date_create($date2);
	$diff=date_diff($date1, $date2);
	$diff = $diff->format("%R%a days");
	if ($diff < 0)
	{
		$request_id = $result[$i]['request_id'];
		$query = "DELETE FROM requesttruck WHERE request_id = '$request_id'";
		$output = mysqli_query($conn, $query);
	}
}

$query = "SELECT* FROM event WHERE 1";
$output = mysqli_query($conn, $query);

$result = array();
while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

for ($i = 0, $len = count($result); $i < $len; ++$i) {
	$date2 = $result[$i]['date'];
	$date2 = date_create($date2);
	$diff=date_diff($date1, $date2);
	$diff = $diff->format("%R%a days");
	if ($diff < 0)
	{
		$event_id = $result[$i]['event_id'];
		$query = "DELETE FROM event WHERE event_id = '$event_id'";
		$output = mysqli_query($conn, $query);
		$query = "DELETE FROM volunteer WHERE event_id = '$event_id'";
		$output = mysqli_query($conn, $query);
	}
}
echo '{"Status":1}';

mysqli_close($conn);
?>