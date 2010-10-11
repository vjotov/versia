<?php

class cd_workitem {
	static public function create_workitem($release_id, $vo_id, $wi_name) {
		global $mdb; 
		//some checks
		$query_0 = "SELECT count(*) AS cc FROM t_workitem WHERE initiator_vo_id='".$vo_id."' AND release_id='".$release_id."' "; 
		$resultset_0 = $mdb->query($query_0);
		if(PEAR::isError($resultset_0)) {
			$err_['code'] = 2;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset_0->getMessage();
			$err_['message'] .= '\n cd_workitem::create_workitem - 2';
			return $err_;
		}
		$wi_count = $resultset_0->fetchAll(MDB2_FETCHMODE_ASSOC);
		if($wi_count[0]['cc'] != 0) { 
      $err_['code'] = 3;
      $err_['message']  = 'This object is already a workitem for the selected release';
      return $err_;
		}
		
		$query = "INSERT INTO t_workitem (initiator_vo_id, wi_name, release_id) "
						." VALUES ('".$vo_id."', '".$wi_name."', '".$release_id."')";
		$resultset = $mdb->exec($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::create_workitem - 1';
			return $err_;
		}
		return array('code' => 0, 'message' => 'Sucessful');
	}
	static public function update_workitem($wi_id, $wi_name, $release_id) {
		global $mdb;
		$query_0 = "SELECT count(*) AS cc FROM t_workitem WHERE initiator_vo_id='".$vo_id."' AND release_id='".$release_id."' "; 
		$resultset_0 = $mdb->query($query_0);
		if(PEAR::isError($resultset_0)) {
			$err_['code'] = 2;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset_0->getMessage();
			$err_['message'] .= '\n cd_workitem::create_workitem - 2';
			return $err_;
		}
		$wi_count = $resultset_0->fetchAll(MDB2_FETCHMODE_ASSOC);
		if($wi_count[0]['cc'] != 1) { 
      $err_['code'] = 3;
      $err_['message']  = 'This object is not a workitem for the selected release';
      return $err_;
		}
		
		$query = "UPDATE t_workitem SET wi_name='".$wi_name."' WHERE wi_id='".$wi_id."' ";
		$resultset = $mdb->exec($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::update_workitem - 1';
			return $err_;
		}
		return array('code' => 0, 'message' => 'Sucessful');
	}
	static public function attach_workitem($wi_id, $ws_id) {
		global $mdb;
			
		$query = "INSERT INTO t_ws_workitem (wi_id, ws_id) "
						." VALUES ('".$wi_id."', '".$ws_id."' )";
		$resultset = $mdb->exec($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::attach_workitem - 1';
			return $err_;
		}
		return array('code' => 0, 'message' => 'Sucessful');
	}
	static public function detach_workitem($wi_id, $ws_id) {
		global $mdb;
		
		$query = "DELETE FROM t_ws_workitem WHERE wi_id = '".$wi_id."' AND ws_id = '".$ws_id."' ";
		$resultset = $mdb->exec($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::detach_workitem - 1';
			return $err_;
		}
		return array('code' => 0, 'message' => 'Sucessful');
	}
	/*
	static public function get_workitem_list($release_id) {
		global $mdb; 
			
		$query = "SELECT wi_id, release_id, initiator_vo_id AS vo_id, initiator_vp_id AS vp_id, wi_name AS name FROM t_workitem";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::get_workitem_list - 1';
			return array('error' => $err_);
		}
		$wi_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array ('error' => array('code' => 0, 'message' => 'Sucessful'),
			'workitem_list' => $wi_list);
	}
	//*/
	static public function get_attached_workitem_list($ws_id) {
		global $mdb;
		$query = "SELECT wi_id, vo_id, wi_name AS name "
				." FROM v_workitem_distribution WHERE ws_id='".$ws_id."' AND is_active='1' ORDER BY name DESC";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::get_attached_workitem_list - 1';
			return array('error' => $err_);
		}
		$wi_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		//TODO: compozirani workitems
		return array('error' => array('code' => 0, 'message' => 'Successful'),
			'workitem_list' => $wi_list );
	}
	static public function get_not_attached_workitem_list($ws_id, $release_id) {
		global $mdb;
			
		$query = "SELECT wi_id, vo_id, "
        ." (SELECT constructs FROM t_versioned_primitive WHERE vo_id=wid.vo_id AND vp_id=wid.vp_id) AS constructs, "
        ." (SELECT name FROM t_versioned_primitive WHERE vo_id=wid.vo_id AND vp_id=wid.vp_id) AS name"
				." FROM v_workitem_distribution wid "
				." WHERE ws_id='".$ws_id."' AND release_id='".$release_id."' AND is_active='0'  "
				." ORDER BY constructs ASC, name DESC";	

		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::get_not_attached_workitem_list - 1';
			return array('error' => $err_);
		}
		$wi_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array('error' => array('code' => 0, 'message' => 'Successful'),
			'workitem_list' => $wi_list );
	}

}
?>