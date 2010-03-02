<?php

class cd_vo {
	static public function create_versioned_object($ws_id, $vo_name, $vo_datum, $uid /*$vo_ancestor, $vo_model, $vo_model_place,*/) {
		global $mdb;   
		
		$query = "SELECT create_versioned_object(".$ws_id.", '".$vo_name."', '".$vo_datum."', '".$uid."') AS new_vo_id ";
		$resultset = $mdb->query($query); 
		
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::create_versioned_object($id) - 1';
			return array('error' => $err_);
		}
		if($resultset->numRows() != 1) {
			$err_['code'] = 1;
			$err_['message'] = 'cd_vo->create_versioned_object -';
			return array('error' => $err_);
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array( 
			'new_vo_id' => $set[0]['new_vo_id'],
			'error' => array('code' => 0, 'message' => 'Successful')
		);
	}
	static public function get_visible_vo_list($ws_id) {
		global $mdb;

		$query = "SELECT vv.vo_id, vv.vp_id, vv.vp_name, vv.locked, vp.datum AS vp_datum, vv.v_vector AS v_vector"
				." FROM v_vo_visibility vv INNER JOIN t_versioned_primitive vp ON vp.vp_id = vv.vp_id AND vp.vo_id = vv.vo_id "
				." WHERE ws_id = '".$ws_id."' "; 
				
		$resultset = $mdb->query($query); 
		
		if(PEAR::isError($resultset)) {
			$err_['code'] = 2;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::get_visible_vo_list - 1';
			return array('error' => $err_);
		}
		$vo_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array('error' => array('code' => 0, 'message' => 'Successful') ,
			'vo_list' => $vo_list);
	}
	static public function save_versioned_object_state($ws_id, $vo_id, $new_datum, $vo_name, $uid) {
		global $mdb; 
		
		$query = "SELECT f_save_vo_state('".$vo_id."', '".$ws_id."', '".$new_datum."', '".$vo_name."', '".$uid."') AS save_result";
		$resultset = $mdb->query($query);
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::save_versioned_object_state - 1';
			return $err_;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);

		$result = $set[0]['save_result'];
		if($result == 0) {
			$err_['code'] = 2;
			$err_['message'] = 'Versioned Object is locked.';
			return $err_;
		} else {
			return array('code' => 0, 'message' => 'Sucessful');
		}
	}
	static public function publish_versioned_object_state($in_ws_id, $vo_id){
		global $mdb;   
		
		$query = "SELECT f_publish_versioned_object('".$in_ws_id."', '".$vo_id."') AS result ";
		$resultset = $mdb->query($query); var_dump($query);
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::publish_versioned_object_state - 1';
			return $err_;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$result = $set[0]['result'];

		if($result == -1) {
			return array('code' => 2, 'message' => 'This oject is not local for the workspace.');
		}
		if( $result == -2) {
			return array('code' => 3, 'message' => 'The workspace is Master workpace for the release.');
		}
		if( $result == -3) {
			return array('code' => 4, 'message' => 'The oject`s version in ancestor workspace is locked.');
		}
		
		return  array('code' => 0, 'message' => 'Sucessful');
	}
	static public function putback_versioned_object_state($in_ws_id, $vo_id){
		global $mdb;   

		$query = "SELECT f_putback_versioned_object( '".$in_ws_id."', '".$vo_id."') AS result ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::putback_versioned_object_state - 1';
			return $err_;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$result = $set[0]['result'];
		
		if($result == -1) {
			return array('code' => 2, 'message' => 'This oject is not local for the workspace.');
		}
		if( $result == -2) {
			return array('code' => 3, 'message' => 'The workspace is Master workpace for the release.');
		}
		if( $result == -3) {
			return array('code' => 4, 'message' => 'The oject`s version in ancestor workspace is locked.');
		}
		
		return  array('code' => 0, 'message' => 'Sucessful');
	}
	
	static public function vew_versioned_object_distribution($vo_id, $release_id) {
		global $mdb;

		$query = "SELECT ws_id, name FROM v_vo_distribution "
				." WHERE vo_id = '".$vo_id."' AND release_id = '".$release_id."' "; 
				
		$resultset = $mdb->query($query); 
		
		if(PEAR::isError($resultset)) {
			$err_['code'] = 2;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::putback_versioned_object_state - 1';
			return array();
		}
		$ws_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array('error' => array('code' => 0, 'message' => 'Successful') ,
			'ws_list' => $ws_list);
	}
	// PRIVATE FUNCTIONs
	
}
?>