<?php

function hasRights($session_id, $right) {
	global $mdb;
	$query="SELECT validate_session_n_permition ('".$session_id."', '".$right."') AS permition";
		$resultset1 = $mdb->query($query); 
		if(PEAR::isError($resultset1)) {
			$err_['err_code'] = '1';
			$err_['err_msgs'] = 'Failed to issue query, error message : ' . $resultset1->getMessage();
			$err_['err_msgs'] .= '\n cd_product::load_by_id($id) - 0 '.$query;
			return array('error' => $err_);
		} 
		$set1 = $resultset1->fetchAll(MDB2_FETCHMODE_ASSOC);//die(var_dump($set1).var_dump($query));
		if($set1[0]['permition'] == 0) {
			$err_['err_code'] = '2';
			$err_['err_msgs'] = 'Access denied - unautorised request';
			return array('error' => $err_);
		}
		return array('error' => array('err_code' => '0', 'err_msgs' => 'Access allow'));
}

function get_last_insert_id() {
	global $mdb;
	$query = "SELECT LAST_INSERT_ID() as lid";
	$resultset = $mdb->query($query); 
	if(PEAR::isError($resultset)) {
		return false;
	}
	$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
	return $set[0]['lid']; 
}

?>