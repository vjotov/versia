<?php

class versioned_primitive_db {
	var $vp_id				= NULL;
	var $vo_id				= NULL;
	var $content 				= NULL;

		
	function versioned_primitive_db( $content = NULL, $vo_id = NULL, $vp_id=0) {
		$this->vp_id 	 			= $vp_id;
		$this->vo_id 	 			= $vo_id;
		$this->content 			= $content;
	}
	function load($vo_id, $vp_id) {
		global $mdb;

		$query="SELECT * FROM t_versioned_primitive WHERE vp_id='".$id."' AND vo_id='".$vo_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		if($resultset->numRows() != 1) {
			debug_print_backtrace();
			die('versioned_object_db->load('.$id.') - no such workspace');
			return;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->vp_id 	 			= $set[0]['vp_id'];
		$this->vo_id 	 			= $set[0]['vo_id'];
		$this->content 			= $set[0]['content'];
	  //*/
	}

	function create() {
		global $mdb;
		
		$result = $mdb->query("INSERT INTO t_versioned_primitive (vp_id, vo_id, content)"
						." VALUES ( '0', '".$this->vo_id."', '".$this->content."')");
		
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
		//*/
	}
	function delete() {
	}
}

?>