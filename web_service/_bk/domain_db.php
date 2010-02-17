<?php

class domain_db{
	var $domain_id;
	var $source_r_id;
	var $target_r_id;
	var $ws_id;
	
	
	function domain_db($domain_id=NULL, $source_r_id = NULL, $target_r_id = NULL, $ws_id = NULL) {
		$this->domain_id 	 = $domain_id;
		$this->source_r_id = $source_r_id;
		$this->target_r_id = $target_r_id;
		$this->ws_id 			 = $ws_id;
	}
	function load($id) {
		global $mdb;

		$query="SELECT * FROM t_domain WHERE domain_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('domain_db->load('.$id.') - no such domain');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->domain_id 	 = $set[0]['domain_id'];
		$this->source_r_id = $set[0]['source_r_id'];
		$this->target_r_id = $set[0]['target_r_id'];
	  $this->ws_id 			 = $set[0]['ws_id'];
	}
	function load_by_target($id) {
		global $mdb;

		$query="SELECT * FROM t_domain WHERE target_r_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('domain_db->load_by_target('.$id.') - no such targer_release');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->domain_id 	 = $set[0]['domain_id'];
		$this->source_r_id = $set[0]['source_r_id'];
		$this->target_r_id = $set[0]['target_r_id'];
	  $this->ws_id 			 = $set[0]['ws_id'];
	}
	function save() {
		global $mdb;
		if ($this->r_id==0) {
			$result = $mdb->query("INSERT INTO t_domain (source_r_id, target_r_id, ws_id)"
							." VALUES ('".$this->source_r_id."', '".$this->target_r_id."', '".$this->ws_id."')");
			$this->domain_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_domain SET source_r_id='".$this->source_r_id."', target_r_id='".$this->target_r_id."', ws_id='".$this->ws_id."'"
							." WHERE domain_id='".$this->domain_id."'");
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
	function get_master_ws() {
		$master_ws = new workspace_db();
		$master_ws->load($this->ws_id);
		return $master_ws;
	}
}

?>