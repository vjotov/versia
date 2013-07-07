<?php

class cd_user { 
	static public function get_user_list() {
		try{	    	
			$user_set = cd_executer::execQuery("cd_user:get_user_list", 
				"SELECT uid, username FROM t_user");

			$users = array();
			foreach($set as $value) {
				$permition = cd_executer::execQuery("cd_user:get_user_list", 
				"SELECT a.permition_id, action_id, action_name, permited FROM t_permition p INNER JOIN t_action a USING (action_id) "
				." WHERE uid = '".$value["uid"]."' ");
						
				$users[] = array ('uid' => $value["uid"], 'username' => $value['username'], 'permitions' => $permition_set);
			}
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'result' => $users);
		}  catch(Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}	
	
	static public function create_user($name, $password) {
		try{	    	
			cd_executer::beginTransaction();
			cd_executer::execQuery("cd_user:create_user", 
				"INSERT INTO t_user (name) VALUES ('".$name."')");
			$new_user_id = get_last_insert_id();
			cd_executer::execQuery("cd_user:create_user", 
				"INSERT INTO t_permition (uid, permition_id, permited) SELECT ".$new_user_id.", action_id, 0 FROM t_action ");
			
			cd_executer::commitTransaction();
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'user_list' => array());
		}  catch(Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
		
	} 
	static public function update_permition($permition_id, $permition_value) {
		try{
			if($permition_value != 1) $permition_value = 0; // only 1 and 0 are correct values
			    	
			cd_executer::execQuery("cd_user:create_user", 
				"UPDATE t_permition SET permited='".$permition_value."' WHERE permition_id='".$permition_id."' ");
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'user_list' => array());
		}  catch(Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
	
}

?>