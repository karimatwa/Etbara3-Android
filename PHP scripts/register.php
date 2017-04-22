<?php
$method = $_SERVER['REQUEST_METHOD'];

if ($method == 'POST') {
	//require "/var/www/html/auc/twilio/twilio-php-master/Services/Twilio.php";
	$AccountSid = "ACba0ecda33134f699bfb2216fb001ca63"; // Your Account SID from www.twilio.com/console
	$AuthToken = "cbfdf021c61e33bcff657046707dbf22";   // Your Auth Token from www.twilio.com/console
	//$client = new Services_Twilio($AccountSid, $AuthToken);

	$json = file_get_contents('php://input');
	$json = json_decode($json, true);
	$conn = mysqli_connect("localhost", "root", "", "etbara3");
	if ($conn) {
		$email = $json['email'];
		$random = rand(1000, 9999);
		$query = "INSERT INTO Verification VALUES ('$email', '$random')";
		mysqli_query($conn, $query);
		echo '{"Status" : 1}';
		mysqli_close($conn);
/*		$message = $client->account->messages->create(array(
		"From" => "+14048003880", // From a valid Twilio number
		"To" => $phone,   // Text this  number
		"Body" => $random,
		));*/
	}
	else
		echo '{"Status" : 2}';
}
?>