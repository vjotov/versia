<?php

class cd_versioned_primitive_arc{
	var $source = NULL;
	var $targer = NULL;
	var $vo_id  = NULL;
		
	function cd_versioned_primitive_arc($source=NULL, $targer = NULL, $vo_id = NULL) {
		$this->source = $source;
		$this->targer = $targer;
		$this->vo_id  = $vo_id;
	}
	function load($source, $target, $vo_id) {
		global $mdb;

		$query="SELECT * FROM t_versioned_primitive_arc WHERE source='".$source."' AND target='".$target."' AND vo_id='".$vo_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('cd_release->get('.$id.') - no such vo primitive');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->source = $set[0]['source'];
		$this->targer = $set[0]['targer'];
		$this->vo_id  = $set[0]['vo_id'];
	}
	function save() {
		global $mdb;
		$result = $mdb->query("INSERT INTO t_versioned_primitive_arc (source, target, vo_id) "
			."VALUES ('".$this->source."', '".$this->targer."', '".$this->vo_id."')");
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('cd_versioned_primitive_arc 1 Failed to issue query, error message : ' . $trx->getMessage());
			} 
			die('cd_versioned_primitive_arc 2 Failed to issue query, error message : ' . $result->getMessage());
		}
		return TRUE;
	}
	function delete() {
	}
	
	static function load_ls_by_source($source, $vo_id) {
		global $mdb;

		$query="SELECT target FROM t_versioned_primitive_arc WHERE source='".$source."' AND vo_id='".$vo_id."' ORDER BY target";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$result = array();
		foreach($set as $row => $value) {
			$result[] = new cd_release_arc($source, $value['target']);
		}
		return $result;
	}
	static function load_ls_by_target($target, $vo_id) {
		global $mdb;

		$query="SELECT source FROM t_versioned_primitive_arc WHERE target='".$target."' AND vo_id='".$vo_id."' ORDER BY source";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$result = array();
		foreach($set as $row => $value) {
			$result[] = new cd_release_arc($value['source'], $target);
		}
		return $result;
	}
	static function create_arc($vo_id, $source, $target) {
		$n = new cd_versioned_primitive_arc($source, $target, $vo_id);
		$n->save();
	}
}

?>