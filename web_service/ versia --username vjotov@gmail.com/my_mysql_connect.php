<?php

require_once("MDB2.php");

function my_mysql_connect() {
	global $mdb;

	// DRIVER://USER:PASS@SERVER/DATABASE
	$url = "mysql://root:@localhost/versia_er2";

	$mdb = MDB2::factory($url);
	if(PEAR::isError($mdb)) {
		die("Error while connecting : " . $con->getMessage());
	}
}

?>