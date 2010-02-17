<?php

class cd_workitem {
	static public function create_workitem($release_id, $vo_id, $vp_vp, $wi_name) {
		global $mdb; 
		
		$query = "INSERT INTO t_workitem (initiator_vo_id, initiator_vp_id, wi_name, release_id) "
						." VALUES ('".$vo_id."', '".$vp_vp."', '".$wi_name."', '".$release_id."')";
		$resultset = $mdb->exec($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_workitem::create_workitem - 1';
			return $err_;
		}
		return array('code' => 0, 'message' => 'Sucessful');
	}
	static public function update_workitem($wi_id, $wi_name) {
		global $mdb;
		
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
		return array('error' => array('code' => 0, 'message' => 'Successful'),
			'workitem_list' => $wi_list );
	}
	static public function get_not_attached_workitem_list($ws_id) {
		global $mdb;
			
		$query = "SELECT wi_id, vo_id, wi_name AS name "
				." FROM v_workitem_distribution WHERE ws_id='".$ws_id."' AND is_active='0'  ORDER BY name DESC";	
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