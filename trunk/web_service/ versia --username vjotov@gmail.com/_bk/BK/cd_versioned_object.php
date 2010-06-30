<?php

class cd_versioned_object {
	var $vo_id					= NULL;
	var $name						= NULL;
	//var $super_vo; // future use
	//var $vo_type;  // future use
	var $selected_vp_id = NULL;
	
	function cd_versioned_object($vo_id=NULL, $name=NULL) {
		$this->name = $name;
		$this->vo_id = $vo_id;
	}
	function load($vo_id) { 
		global $mdb;
		$query="SELECT vo_id, name FROM t_versioned_object WHERE vo_id='".$vo_id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('cd_product->get('.$id.') - no such VO');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->vo_id = $set[0]['vo_id'];
		$this->name = $set[0]['name'];
	}
	static function create($name) {
		global $mdb;
		$result = $mdb->query("INSERT INTO t_versioned_object (name)"
							." VALUES ( '".$name."' )");
		$vo_id = $mdb->lastInsertID();
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) {
					die('vo1 Failed to issue query, error message : ' . $trx->getMessage());
				}
			}
			die('vo 2 Failed to issue query, error message : ' . $result->getMessage()); 
		}
		
		$vo = new cd_versioned_object($vo_id, $name); 
		cd_versioned_primitive::create($vo_id);
		$vo->selected_vp_id = 0;
		return $vo;
	}
	function delete() {//TODO
	}
}

?>