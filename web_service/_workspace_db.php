<?php

class workspace_db{
	var $ws_id 				= NULL;
	var $parent_ws_id = NULL;
	var $name 				= NULL;
	var $domain_id 		= 0;
		
	function workspace_db( $name = NULL, $parent_ws_id = NULL, $domain_id = 0, $ws_id=NULL) {
		$this->ws_id 	 			= $ws_id;
		$this->parent_ws_id = $parent_ws_id;
		$this->name 				= $name;
		$this->domain_id 	 	= $domain_id;
	}
	function load($id) {
		global $mdb;

		$query="SELECT * FROM t_workspace WHERE ws_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		if($resultset->numRows() != 1) {
			debug_print_backtrace();
			die('workspace_db->load('.$id.') - no such workspace');
			return;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->ws_id 	 			= $set[0]['ws_id'];
		$this->parent_ws_id = $set[0]['parent_ws_id'];
		$this->name 				= $set[0]['name'];
	  $this->domain_id 		= $set[0]['domain_id'];
	}
	function get_parent_ws() {
		$parent_ws_id = $this->parent_ws_id;
		$parent_ws = new workspace_db();
		$parent_ws->load($parent_ws_id);
		return $parent_ws;
	}
	function save() {
		global $mdb;
		if ($this->ws_id==0) {
			$result = $mdb->query("INSERT INTO t_workspace (parent_ws_id, name, domain_id)"
							." VALUES ( '".$this->parent_ws_id."', '".$this->name."', '".$this->domain_id."')");
			$this->ws_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_workspace SET parent_ws_id='".$this->parent_ws_id."', name='".$this->name."', domain_id='".$this->domain_id."'"
							." WHERE ws_id='".$this->ws_id."'");
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
	function get_domain() {
		$domain = new domain_db();
		$domain->load($this->domain_id);
		return $domain;
	}
	function get_parent() {
		$parent = new workspace_db();
		$parent->load($this->parent_ws_id);
		return $parent;
	}
	function get_master_ws() {
		$domain = $this->get_domain();
		$master_ws = $domain->get_master_ws();
		return $master_ws;
	}
}

?>