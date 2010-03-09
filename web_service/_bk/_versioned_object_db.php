<?php

class versioned_object_db {
	var $vo_id				= 0;
	var $name 				= NULL;

		
	function versioned_object_db( $name = NULL, $vo_id = 0) {
		$this->vo_id 	 			= $vo_id;
		$this->name 				= $name;
	}
	function load($id) {
		global $mdb;

		$query="SELECT * FROM t_versioned_object WHERE vo_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		if($resultset->numRows() != 1) {
			debug_print_backtrace();
			die('versioned_object_db->load('.$id.') - no such workspace');
			return;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->vo_id 	 			= $set[0]['vo_id'];
		$this->name 				= $set[0]['name'];
	  //*/
	}

	function save() {
		global $mdb;
		if ($this->vo_id == 0) {
			$result = $mdb->query("INSERT INTO t_versioned_object (name)"
							." VALUES ( '".$this->name."') ");
			$this->vo_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_versioned_object SET name='".$this->name."' "
							." WHERE vo_id='".$this->vo_id."'");
		}
		
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
	function create_versioned_object() {
		$this->save();
		$empty_primitive = new versioned_primitive_db(" ",$this->vo_id);
		$empty_primitive->create();
		return $empty_primitive;
	}
	function delete() {
	}
}

?>