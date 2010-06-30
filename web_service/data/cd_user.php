<?php

class cd_user { 
	static public function get_user_list() {
		global $mdb;
    	
		$query="SELECT uid, username FROM t_user";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_user.get_user_list() - 1';
			return array('error' => $err_);
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);//var_dump($set);
		$users = array();
		foreach($set as $value) {
			$permitions = self::get_permitions($value["uid"]);//var_dump($permitions);
			if($permitions == false) {
				$err_['code'] = 1;
				$err_['message'] = 'Failed to issue query';
				$err_['message'] .= '\n cd_user.get_permitions() - 1';
				return array('error' => $err_);
			}
			
			
			$users[] = array ('uid' => $value["uid"], 'username' => $value['username'], 'permitions' => $permitions);
		}
		
		return array(
			'error' => array('code' => 0, 'message' => 'Successful'),
			'user_list' => $users);
	}	
	
	static public function create_user($name, $password) {
		global $mdb;
		    	
		$err_ = array();
		// CREATION OF NEW USER & ZERO RIGHTS
		$query = "INSERT INTO t_user (name) VALUES ('".$name."')";
		$result = $mdb->query($query);
		if(PEAR::isError($result)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $result->getMessage();
			$err_['message'] .= ' -- cd_user::create_user() - 1'.$query;
			return $err_;
		}
		$err_['code'] = 0;
		$err_['message'] = 'Sucessful';
		return $err_;
		
	} 
	static public function update_permition($user_id, $action_id, $permition_value) {
	// TODO: da pomislq kak da predam vsichki permitions
	//update in j_user.php i GetPermition.java
		global $mdb;
		if($permition_value != 1) $permition_value = 0; // only 1 and 0 are correct values
		    	
		$err_ = array();
		$result = $mdb->query("UPDATE t_user SET permited='".$permition_value."' WHERE uid='".$user_id."' AND action_id = '".$action_id."' ");
		if(PEAR::isError($result)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $result->getMessage();
			$err_['message'] .= 'cd_user.update_permition() - 1';
			return $err_;
		}
		$err_['code'] = 0;
		$err_['message'] = 'Successful';
		return $err_;
	}
	
	///// PRIVATE FUNCTIONS
	static private function get_permitions($uid) {
		global $mdb;
		
		$query="SELECT * FROM t_permition INNER JOIN t_action USING (action_id) WHERE uid = '".$uid."' ";
		$sub_resultset = $mdb->query($query); 
		if(PEAR::isError($sub_resultset)) {
      
			return false;
		}
		$permitions = array();
		$set = $sub_resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		foreach($set as $value) {
			$permition[] = array('permition_id' => $value['permition_id'], 
								'action_id' => $value['action_id'], 
								'action_name' => $value['action_name'], 
								'permited' => $value['permited']);
		}
		return $permition;
	}

}

?>