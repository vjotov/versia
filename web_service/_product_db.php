<?php

class product_db{
	var $pr_id;
	var $name;
	
	function product_db($pr_id = NULL, $name = NULL) {
		//$this = new product_db();
		$this->pr_id = $pr_id;
		$this->name = $name;
	}
	function load($id) {
		global $mdb;

		$query="SELECT pr_id, name FROM t_product WHERE pr_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('product_db->get('.$id.') - no such product');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->pr_id = $set[0]['pr_id'];
		$this->name = $set[0]['name'];
	}
	function save() {
		global $mdb;
		if ($this->pr_id==0) {
			$result = $mdb->query("INSERT INTO t_product (name) VALUES ('".$this->name."')");
			$this->pr_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_product SET name='".$this->name."' WHERE pr_id='".$this->pr_id."'");
		}
		//$sth->free();
		if(PEAR::isError($result)) {
			die('Failed to issue query, error message : ' . $result->getMessage());
		}
		return TRUE;
	}
	function delete() {
	}
}

?>