<?php

class release_db_list{
	var $list;
	
	function load_list($pr_id) {
		global $mdb;

		$query="SELECT r_id, pr_id, name FROM t_release WHERE pr_id='".$pr_id."' ORDER BY name";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->list = array();
		foreach($set as $row => $value) {
			$this->list[] = new release_db($value['r_id'], $value['pr_id'], $value['name']);
		}
	}
}

?>