<?php

class release_db{
	var $r_id;
	var $pr_id;
	var $name;
		
	function release_db($r_id=NULL, $pr_id = NULL, $name = NULL) {
		$this->r_id = $r_id;
		$this->pr_id = $pr_id;
		$this->name = $name;
	}
	function load($id) {
		global $mdb;

		$query="SELECT * FROM t_release WHERE r_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('release_db->get('.$id.') - no such release');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->r_id = $set[0]['r_id'];
		$this->pr_id = $set[0]['pr_id'];
		$this->name = $set[0]['name'];
	}
	function save() {
		global $mdb;
		if ($this->r_id==0) {
			$result = $mdb->query("INSERT INTO t_release (pr_id, name) VALUES ('".$this->pr_id."', '".$this->name."')");
			$this->r_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_release SET name='".$this->name."' WHERE r_id='".$this->r_id."'");
		}
		//$sth->free();
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('Failed to issue query, error message : ' . $trx->getMessage());
			}
			die('Failed to issue query, error message : ' . $result->getMessage());
		}
		return TRUE;
	}
	function delete() {
	}
}

?>