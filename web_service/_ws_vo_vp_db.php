<?php

class ws_vo_vp_db {
	var $ws_id 				= NULL;
	var $vo_id				= NULL;
	var $vp_id 				= NULL;
	var $vo						= NULL;
	var $vp						= NULL;

		
	function ws_vo_vp_db( $ws_id = NULL, $vo_id = NULL, $vp_id = NULL) {
		$this->ws_id 	 			= $ws_id;
		$this->vo_id 				= $vo_id;
		$this->vp_id 				= $vp_id;
	}
	function load() {
		global $mdb;

		$query="SELECT * FROM t_ws_vo_p WHERE ws_id='".$this->ws_id."' AND $vo_id = '".$this->vo_id."', $vp_id = '".$this->vp_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		if($resultset->numRows() != 1) {
			debug_print_backtrace();
			die('ws_vo_vp_db->load() - no such ws_vo_vp');
			return;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->ws_id 	 			= $set[0]['ws_id'];
		$this->vo_id				= $set[0]['vo_id'];
		$this->vp_id 				= $set[0]['vp_id'];
	  //*/
		$this->load_components();
	}
	function load_components() {
		$vo = new versioned_object_db();
	  $vo->load($this->vo_id);
	  $this->vo = $vo;
	  $vp= new versioned_primitive_db();
	  $vp->load($this->vo_id, $this->vp_id);
	  $this->vp = $vp;
	}

	function create() {
		global $mdb;
		$result = $mdb->query("INSERT INTO t_ws_vo_p (ws_id, vo_id, vp_id)"
						." VALUES ( '".$this->ws_id."', '".$this->vo_id."', '".$this->vp_id."')");
		$this->ws_id = $mdb->lastInsertID();
		
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) {
					debug_print_backtrace();
					die('Failed to issue query, error message : ' . $trx->getMessage());
				}
			}
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $result->getMessage()); 
		}
		return TRUE;
	}
	function update_vp() {
		global $mdb;
		$result = $mdb->query("UPDATE t_ws_vo_p SET vp_id='".$this->vp_id."' "
						." WHERE ws_id='".$this->ws_id."' AND vo_id='".$this->vo_id."'");
		
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx))  {
					debug_print_backtrace();
					die('Failed to issue query, error message : ' . $trx->getMessage());
				}
			}
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $result->getMessage()); 
		}
		return TRUE;
	}
	function delete() {
	}
	function create_versioned_object($ws_id, $name) {
		global $mdb;
		$trx = $mdb->beginTransaction();
		if(PEAR::isError($trx)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		
		$vo = new versioned_object_db($name);
		$vp = $vo->create_versioned_object();
		
		$this->vo_id = $vo->vo_id;
		$this->vp_id = $vp->vp_id;
		$this->ws_id = $ws_id;
		
		$this->create();
		
		$trx = $mdb->commit();
		if(PEAR::isError($trx)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
	}
}

?>