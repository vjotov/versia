<?php

class product_db_list{
	var $list;
	
	function load_list() {
		global $mdb;

		$query="SELECT pr_id, name FROM t_product ORDER BY name";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->list = array();
		foreach($set as $row => $value) {
			$this->list[] = new product_db($value["pr_id"], $value['name']);
		}
	}
}

?>