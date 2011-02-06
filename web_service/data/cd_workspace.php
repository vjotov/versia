<?php

class cd_workspace {
	static public function create_master_workspace($release_id){
		cd_executer::execQuery("cd_workspace:create_master_workspace",
			"INSERT INTO t_workspace (release_id, ws_name) VALUES (".$release_id.", 'Master Workspace')");
		$master_workspace_id = get_last_insert_id();
		cd_executer::execQuery("cd_workspace:create_master_workspace",
			"UPDATE t_release SET master_ws_id = '.$master_workspace_id.' WHERE release_id = '.$release_id.' ");
		return get_last_insert_id();
	}
	static public function get_workspace($ws_id) {
		try {
			$ws_arr = cd_executer::execQuery("cd_workspace:get_workspace",
				"SELECT ws_id AS workspace_id, release_id, ancestor_ws_id AS ancestor_workspace_id, ws_name AS workspace_name FROM t_workspace WHERE ws_id='".$ws_id."'");
			
			if(sizeof($ws_arr) != 1) {
				throw new Exception("no such workspace : '.$ws_id", 102);
			}
			$result = array();
			$result['error'] = array( 'code' => '0', 'message' => 'Successful');
			$result['result'] = $ws_arr[0];
			if($result['result']['ancestor_workspace_id'] == NULL)
				$result['result']['ancestor_workspace_id'] = -1;
			return $result;
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}	
	static public function create_workspace($ancestor_ws_id, $ws_name) {
		try {
    		cd_executer::beginTransaction();
		
			$ancestor_ws_data = cd_workspace::get_workspace($ancestor_ws_id);
			if($ancestor_ws_data['error']['code'] != "0") { 
				throw new Exception($ancestor_ws_data['error']['message'], $ancestor_ws_data['error']['code']);
			}
			$release_id = $ancestor_ws_data['result']['release_id'];
			cd_executer::execQuery("cd_workspace:create_workspace",
				"INSERT INTO t_workspace (ancestor_ws_id, ws_name, release_id) VALUES ('".$ancestor_ws_id."', '".$ws_name."', '".$release_id."') ");
			$new_ws_id = get_last_insert_id(); 
			cd_workitem::create_default_workitem($new_ws_id, $release_id);			
			
			cd_executer::commitTransaction();
				
			$return = array();
			$return['error'] = array('code' => 0, 'message' => 'Successful');
			$return['result'] = array(
	          'workspace_id' => $new_ws_id,
	          'release_id' => $parent_ws_data['release_id'],
	          'ancestor_workspace_id' => $ancestor_ws_id,
	          'workspace_name' => $ws_name);
			return $return;
		} catch (Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
	static public function update_workspace($ws_id, $new_name) {
		try {			
			cd_executer::execQuery("cd_workspace:create_workspace",
				"UPDATE t_workspace SET name='".$new_name."' WHERE ws_id='".$ws_id."'");
						
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'result'=> array());
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
	static public function get_offspring_workspaces($ws_id) {
		try{
			$ws_ls = cd_executer::execQuery("cd_workspace:get_offspring_workspaces",
				"SELECT ws_id AS workspace_id, release_id, ancestor_ws_id AS ancestor_workspace_id, name AS workspace_name FROM t_workspace WHERE ancestor_ws_id='".$ws_id."'");
			
			return array('error' => array('code' => 0, 'message' => 'Successful'),
				'result' => $ws_ls);
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
	static public function get_mater_workspace($ws_id) { 
		try{
			$mater_workspace_id = cd_executer::execQuery("cd_workspace:get_mater_workspace",
				"SELECT r.master_ws_id "
				."FROM t_release r INNER JOIN t_workspace w ON w.release_id = r.release_id "
				."WHERE w.ws_id = '".$ws_id."' ");
			return self::get_workspace($master_ws_id);
		}  catch(Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
}

?>