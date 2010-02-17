<?php

class cd_versioned_primitive {
	var $vo_id		= NULL;
	var $vp_id		= NULL;
	var $content	= NULL;
	
	function cd_versioned_primitive($vo_id = NULL, $vp_id = NULL, $content = NULL) {
		$this->vo_id = $vo_id;
		$this->vp_id = $vp_id;
		$this->content = $content;
	}
	function load($vo_id, $vp_id) {		
		global $mdb;
		$query="SELECT vo_id, vp_id, content FROM t_versioned_primitive WHERE vo_id='".$vo_id."' AND vp_id='".$vp_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) { 
			die('load cd_versioned_primitive->get('.$id.') - no such VP');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->vo_id = $set[0]['vo_id'];
		$this->vp_id = $set[0]['vp_id'];
		$this->content = $set[0]['content'];
	}
	function save() {
		global $mdb; 
		echo $r = $this->check_vpid(); 
		if($r) {
			$result = $mdb->query("UPDATE t_versioned_primitive SET content='".$this->content."' "
				." WHERE vo_id='".$this->vo_id."' AND vp_id='".$this->vp_id."' ");
			
			if(PEAR::isError($result)) {
				die('Failed to issue query, error message : ' . $result->getMessage());
			}
		} else {
			$result = $mdb->query("INSERT INTO t_versioned_primitive (vo_id, vp_id, content)"
				." VALUES ('".$this->vo_id."', '".$this->vp_id."', '".$this->content."') ");
			if(PEAR::isError($result)) { 
				die('vp save insert Failed to issue query, error message : ' . $result->getMessage());
			}
		}		
		return TRUE;
	}
	private function check_vpid() {
		global $mdb; 
		$query="SELECT count(vp_id) AS maxi FROM t_versioned_primitive WHERE vo_id='".$this->vo_id."' AND vp_id='".$this->vp_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('check_vpid Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1)  {
			die('cd_versioned_primitive-check_vpid()');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$maxi = $set[0]['maxi'];
		if($maxi == 0)
			return false;
		return true;
	}
	static function create($vo_id) {
		global $mdb;
		$result = $mdb->query("INSERT INTO t_versioned_primitive (vo_id, vp_id, content)"
							." VALUES ( '".$vo_id."', '0', '' )");
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('vp1 Failed to issue query, error message : ' . $trx->getMessage());
			}
			die('vp2 Failed to issue query, error message : ' . $result->getMessage()); 
		}
		
		$vp = new cd_versioned_object($vo_id, 0, ""); 
		return $vp;
	}
	static function get_last_vp_id($vo_id) {
		global $mdb;
		$query="SELECT last_vp FROM v_last_vo_version WHERE vo_id='".$vo_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('get_last_vp_id-('.$vo_id.') - no such VP');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return $set[0]['last_vp'];
	}
}

?>