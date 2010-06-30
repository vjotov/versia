<?php

class cd_release_arc{
	var $source;
	var $target;
		
	function cd_release_arc($source=NULL, $target = NULL) {
		$this->source = $source;
		$this->target = $target;
	}
	function load($source, $target) {
		global $mdb;

		$query="SELECT * FROM t_release_arc WHERE source='".$source."' AND target='".$target."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('cd_release->get('.$id.') - no such release');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->source = $set[0]['source'];
		$this->target = $set[0]['target'];
	}
	function save() {
		global $mdb;
		
		$result = $mdb->query("INSERT INTO t_release_arc (source, target) VALUES ('".$this->source."', '".$this->target."')");
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('Failed to issue query, error message (cd_release_arc.save#1): ' . $trx->getMessage());
			}
			die('Failed to issue query, error message (cd_release_arc.save#2): ' . $result->getMessage());
		}
		return TRUE;
	}
	function delete() {
	}
	
	static function load_ls_by_source($source) {
		global $mdb;

		$query="SELECT target FROM t_release_arc WHERE source='".$source."' ORDER BY target";
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
	static function load_ls_by_target($source) {
		global $mdb;

		$query="SELECT source FROM t_release_arc WHERE target='".$target."' ORDER BY source";
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
}

?>