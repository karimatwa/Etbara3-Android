<?php

$conn = mysqli_connect("localhost", "etbara3", "etbara3@auc", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$query = "SELECT* FROM organization WHERE 1";
$output = mysqli_query($conn, $query);
if (mysqli_num_rows($output) == '0') {
	echo '{"Status" : 1}';
	exit;
}
//$output = $output->fetch_all(MYSQL_ASSOC);
$result = array();
while($row = $output->fetch_assoc()) {
    $result[] = $row;
}

$encodedResult = json_encode($result);

echo $encodedResult;
mysqli_close($conn);

?>
