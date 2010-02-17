<?php

class cd_d {
	var $domain_id;
	var $release_id;
	var $ws_id;
	
	function cd_d ($domain_id=NULL, $release_id=NULL, $ws_id=NULL){
		$this->domain_id = $domain_id;
		$this->release_id = $release_id;
		$this->ws_id = $ws_id;
	}
	
	function load($id) {
		global $mdb;

		$query="SELECT * FROM t_domain WHERE domain_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$em = $resultset->getMessage();
			die('Failed to issue query, error message : ' . $em);
		}
		$nr = $resultset->numRows();
		if($nr != 1) {
			die('cd_d->load('.$id.') - no such domain');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->domain_id 	 = $set[0]['domain_id'];
		$this->release_id  = $set[0]['release_id'];
	  $this->ws_id 			 = $set[0]['ws_id'];
	}
	
	function load_by_release($release_id) {
		global $mdb;

		$query="SELECT * FROM t_domain WHERE release_id='".$release_id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$em = $resultset->getMessage();
			die('Failed to issue query, error message : ' . $em);
		}
		$nr = $resultset->numRows();
		if($nr != 1) {
			die('domain_db->load_by_release('.$release_id.') - no such domain');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->domain_id 	 = $set[0]['domain_id'];
		$this->release_id  = $set[0]['release_id'];
	  $this->ws_id 			 = $set[0]['ws_id'];
	}
	
	function save() {
		global $mdb;
		if ($this->r_id==0) {
			$result = $mdb->query("INSERT INTO t_domain (release_id, ws_id)"
							." VALUES ('".$this->release_id."', '".$this->ws_id."')");
			$this->domain_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_domain SET release_id='".$this->release_id."', ws_id='".$this->ws_id."'"
							." WHERE domain_id='".$this->domain_id."'");
		}
		//$sth->free();
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('Failed to issue query, error message : ' . $trx->getMessage());
			}
			var_dump($result);
			die('Failed to issue query, error message : ' . $result->getMessage());
		}
		return TRUE;
	}
	function delete() {
	}
}

?>