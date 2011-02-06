<?php

class cd_vo {
	static public function create_versioned_object($ws_id, $vo_name, $vo_datum, $uid, $type = '1', $constructs='NULL') {
		try{
			cd_executer::beginTransaction();
			
			cd_executer::execQuery("cd_vo:create_versioned_object", 
				"INSERT INTO t_versioned_object VALUES (NULL)");
			$new_vo_id = get_last_insert_id();
			cd_executer::execQuery("cd_vo:create_versioned_object", 
				"INSERT INTO t_versioned_primitive (vp_id, vo_id, name, datum) VALUES (0, ".$new_vo_id.", ".$vo_name.", ".$vo_datum.") ");				
			cd_executer::execQuery("cd_vo:create_versioned_object",
				"INSER INTO t_ws_obj_ver_selector (vp_id, vo_id, ws_id) VALUES (0, ".$new_vo_id.", ".$ws_id.") ");
			
			cd_workitem::create_traceability($new_vo_id, $vp_id, $ws_id);
			
			cd_executer::commitTransaction();
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'result'=> array());
		}  catch (Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
	static public function get_visible_vo_list($ws_id) { //TODO - SQL
		try{
			$vo_list = cd_executer::execQuery("cd_vo:get_visible_vo_list", 
				"SELECT vv.vo_id, vv.vp_id, vv.vp_name as vo_name, vv.locked, vp.datum AS vo_datum, vv.v_vector AS v_vector, vp.constructs AS constructs, vp.type_id AS type "
				." FROM v_vo_visibility vv INNER JOIN t_versioned_primitive vp ON vp.vp_id = vv.vp_id AND vp.vo_id = vv.vo_id "
				." WHERE ws_id = '".$ws_id."' ORDER BY constructs "); 
					
			return array('error' => array('code' => 0, 'message' => 'Successful') ,
				'result' => $vo_list);
		}  catch (Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}	
	}
	static public function save_versioned_object_state($ws_id, $vo_id, $new_datum, $vo_name, $uid, $type = '1', $constructs='NULL') { //TODO
		try{
			//TODO WI attached to the WS??
			
			global $mdb; 
			
			$query = "SELECT f_save_vo_state('".$vo_id."', '".$ws_id."', '".$new_datum."', '".$vo_name."', '".$uid."',  '".$type."', '".$constructs."') AS save_result";
			$resultset = $mdb->query($query);
			if(PEAR::isError($resultset)) {
				$err_['code'] = 1;
				$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
				$err_['message'] .= '\n cd_vo::save_versioned_object_state - 1'.$query;
				return $err_;
			}
			$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
	
			$result = $set[0]['save_result'];
			switch ($result) {
				case -1 :
					$err_['code'] = $result;
					$err_['message'] = 'No workitems in selected workspace.';
					break;
				case -2 :
					$err_['code'] = $result;
					$err_['message'] = 'Missing type for the new object state.';
					break;
				case -3 :
					$err_['code'] = $result;
					$err_['message'] = 'Missing visible object to be constructed by this one.';
					break;
				case -4 :
					$err_['code'] = $result;
					$err_['message'] = 'Versioned Object is locked.';
					break;
				default:	
					$err_['code'] = 0; $err_['message'] = 'Sucessful';
					break;
			}
			return $err_;
			
			cd_workitem::create_traceability($vo_id, $new_vp_id, $ws_id);
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'result'=> array());
		}  catch (Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}	
	}
	static public function publish_versioned_object_state($in_ws_id, $vo_id){//TODO
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
	static public function putback_versioned_object_state($in_ws_id, $vo_id){//TODO
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
	
	static public function view_versioned_object_distribution($vo_id, $release_id) {//TODO
		global $mdb;

		$query = "SELECT ws_id, name FROM v_vo_distribution "
				." WHERE vo_id = '".$vo_id."' AND release_id = '".$release_id."' "; 
				
		$resultset = $mdb->query($query); 
		
		if(PEAR::isError($resultset)) {
			$err_['code'] = 2;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::view_versioned_object_distribution - 1';
			return array();
		}
		$ws_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array('error' => array('code' => 0, 'message' => 'Successful') ,
			'ws_list' => $ws_list);
	}
	
	static public function view_versioned_object_history($vo_id) {//TODO
    global $mdb;

		$query ="SELECT uid, vo_id, vp_id, on_date_time, wi_id, `action` FROM t_history WHERE vo_id = '".$vo_id."'";
		
		$resultset = $mdb->query($query);
		if(PEAR::isError($resultset)) {
			$err_['code'] = 2;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_vo::view_versioned_object_history - 1';
			return array();
		}
		$changes = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$head = array('uid', 'vo_id', 'vp_id', 'on_date_time', 'wi_id', 'action');
		return array('error' => array('code' => 0, 'message' => 'Successful'), 
		  'result' => array('changes' => $changes, 'head' => $head));
	}
	// PRIVATE FUNCTIONs
	
}
?>