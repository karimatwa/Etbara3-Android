<?php

$json = file_get_contents('php://input');
$json = json_decode($json, true);


$conn = mysqli_connect("localhost", "root", "", "etbara3");
if (!$conn) {
	echo '{"Status" : 0}';
	exit;
}

$query = "SELECT count(profile_id) as count FROM profile";
$output = mysqli_query($conn, $query);
$profilecount = mysqli_fetch_object($output)->count;

$query = "SELECT count(request_id) as count FROM requestmoney";
$output = mysqli_query($conn, $query);
$reqmoneycount = mysqli_fetch_object($output)->count;

$query = "SELECT count(request_id) as count FROM requesttruck";
$output = mysqli_query($conn, $query);
$reqtruckcount = mysqli_fetch_object($output)->count;

$query = "SELECT count(volunteer_id) as count FROM volunteer";
$output = mysqli_query($conn, $query);
$volunteercount = mysqli_fetch_object($output)->count;




echo '{"data": "Number of Profiles: '.$profilecount.' \n\nNumber of Money Donations: '.$reqmoneycount.' \n\nNumber of Truck Requests: '.$reqtruckcount.' \n\nNumber of Volunteers: '.$volunteercount.'"}';

mysqli_close($conn);
?>