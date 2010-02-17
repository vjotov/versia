<?php

class cd_workspace {
	static public function get_workspace($ws_id) {
		global $mdb;
    	
		$query = "SELECT ws_id AS workspace_id, release_id, ancestor_ws_id AS ancestor_workspace_id, name AS workspace_name FROM t_workspace WHERE ws_id='".$ws_id."'";
		$resultset = $mdb->query($query); 
		$result = array();
		if(PEAR::isError($resultset)) {
			$result['code'] = 101;
			$result['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			return array('error' => $result);
		} 
		if($resultset->numRows() != 1) {
			$result['code'] = 102;
			$result['message'] = 'no such workspace : '.$id;
			return array('error' => $result);
		}
		$ws_list = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$result['error'] = array( 'code' => '0', 'message' => 'Successful');
		$result['workspace'] = $ws_list[0];
		if($result['workspace']['ancestor_workspace_id'] == NULL)
			$result['workspace']['ancestor_workspace_id'] = -1;
		return $result;
	}	
	static public function create_workspace($ancestor_ws_id, $name) {
		global $mdb;
    	
		$return = array();
		$ancestor_ws_data = cd_workspace::get_workspace($ancestor_ws_id);
		if($ancestor_ws_data['error']['code'] != "0") { 
			return array('error' => $ancestor_ws_data['error']);
		}
		$query = "SELECT f_create_workspace( '".$ancestor_ws_id."', '".$name."', '".$ancestor_ws_data['workspace']['release_id']."')";
		$result = $mdb->query($query);  
		if(PEAR::isError($result)) {
			$return['code'] = 101;
			$return['message'] = 'Failed to issue query, error message : ' . $result->getMessage();
			return array('error' => $result);
		}	
		
		$new_ws_id = get_last_insert_id(); 
		
		$return['error'] = array('code' => 0, 'message' => 'Successful');
		$return['workspace'] = array(
          'workspace_id' => $new_ws_id,
          'release_id' => $parent_ws_data['release_id'],
          'ancestor_workspace_id' => $ancestor_ws_id,
          'workspace_name' => $name);
		return $return;
	}
	static public function update_workspace($ws_id, $new_name) {
		global $mdb;
		
		$return = array();
		$result = $mdb->query("UPDATE t_workspace SET name='".$new_name."' "
							." WHERE ws_id='".$ws_id."'");
		if(PEAR::isError($result)) {
			$return['code'] = 1;
			$return['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			return $return;
		}
		$return['code'] = 0;
		$return['message'] = 'Successful';
		return $return;
	}
	static public function get_offspring_workspaces($ws_id) {
		global $mdb;
			
		$return = array();
		$query = "SELECT ws_id AS workspace_id, release_id, ancestor_ws_id AS ancestor_workspace_id, name AS workspace_name FROM t_workspace WHERE ancestor_ws_id='".$ws_id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$return['code'] = '1';
			$return['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			return array('error' => $return);
		}
		$ws_ls = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$return ['error'] = array('code' => '0', 'message' => 'Successful');
		$return ['workspaces'] = $ws_ls;
		return $return;
	}
	static public function get_mater_workspace($ws_id) {
		
		$master_ws_id = self::get_mater_workspace_id($ws_id);
		if( $master_ws_id <= 0)
			return array('error' => array('code' => '1', 'message' => 'Error in cd_workspace::get_mater_workspace'));

		return self::get_workspace($master_ws_id);  
	}
	static public function get_workspace_list_for_release($release_id) {
		global $mdb;
		
		$result = array();
		$query = "SELECT * FROM v_workspace_hierarchy WHERE release_id='".$release_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$return['code'] = '1';
			$return['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			return array('error' => $return);
		}
		$ws_ls = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$return ['error'] = array('code' => '0', 'message' => 'Successful');
		$return ['workspaces'] = $ws_ls;
		return $return;
	}
// PRIVATE	
	static private function get_mater_workspace_id($ws_id) {
		global $mdb;
		$query = "SELECT r.master_ws_id "
				."FROM t_release r INNER JOIN t_workspace w ON w.release_id = r.release_id "
				."WHERE w.ws_id = '".$ws_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			return -1;
		}
		if($resultset->numRows() != 1) {
			return -2;
		}
		$result = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return $result[0]['master_ws_id'];
	}
	
	
}

?>