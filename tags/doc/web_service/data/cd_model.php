<?php

class cd_model {
	static public function load_by_id($id, $session_id) {
		global $mdb;
		$right = hasRights($session_id, 'get_model'); 
    if($right['error']['err_code'] != 0)
    	return $right;
    	
		$query="SELECT model_id, model_name, model_definition  FROM t_model WHERE model_id='".$id."'";
		$resultset = $mdb->query($query);
		$return = array();
		if(PEAR::isError($resultset)) {
			$return['err_code'] = '1';
			$return['err_msgs'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$return['err_msgs'] .= '\n cd_model::load_by_id($id) - 1';
			return array('error' => $return);
		} 
		if($resultset->numRows() != 1) {
			$return['err_code'] = '1';
			$return['err_msgs'] = 'cd_model->load_by_id('.$id.') - no such modeler - 2';
			return array('error' => $return);
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array( 
			'model' => $set[0],
			'error' => array('err_code' => '0', 'err_msgs' => 'Sucessful')
		);
	}
	static public function load_list($session_id) {
		global $mdb;
		$right = hasRights($session_id, 'get_model'); 
    if($right['error']['err_code'] != 0)
    	return $right;
    
    $query="SELECT model_id, model_name, model_definition  FROM t_model ORDER BY model_name ";
		$resultset = $mdb->query($query); 
		$return = array();
		if(PEAR::isError($resultset)) {
			$return['err_code'] = '1';
			$return['err_msgs'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$return['err_msgs'] .= '\n cd_model::load_list - 1';
			return array('error' => $return);
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array( 
			'model_list' => $set,
			'error' => array('err_code' => '0', 'err_msgs' => 'Sucessful')
		);
	}
	static public function create_model($name, $definition, $session_id) {
		global $mdb;
		$right = hasRights($session_id, 'create_model'); 
    if($right['error']['err_code'] != 0)
    	return $right;
    
		// TODO - check_model_definition($definition)
		$result = $mdb->query("INSERT INTO t_model (model_name, model_definition) VALUES ('".$name."', '".$definition."')");
		$result = array();
		if(PEAR::isError($result)) {
			$result['err_code'] = '1';
			$result['err_msgs'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$result['err_msgs'] .= 'cd_model.create_modeler() - 1';
			return array('error' => $result);
		}
		$model_id = get_last_insert_id();
		$result = array();
		$result['error'] = array('err_code' => '0', 'err_msgs' => 'Sucessful');
		$result['model'] = array('model_id' => $model_id,
														 'model_name' => $name,
														 'model_definition' => $definition);
		return $result;
	}
	static public function update_model($id, $name, $definition, $session_id) {
		global $mdb;
		$right = hasRights($session_id, 'update_model'); 
    if($right['error']['err_code'] != 0)
    	return $right;
    
		// TODO - check_model_definition($definition)
		$result = $mdb->query("UPDATE t_model SET model_name='".$name."', model_definition='".$definition."' "
							." WHERE model_id='".$id."'");
		$return = array();
		if(PEAR::isError($result)) {
			$return['err_code'] = '1';
			$return['err_msgs'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$return['err_msgs'] .= 'cd_model.update_model() - 1';
			return $return;
		}
		$return['err_code'] = '0';
		$return['err_msgs'] = 'Successful';
		return $return;
	}
	
	//////// PRIVATE functions
	static private function check_model_definition($definition) {
		return true;
		//TODO
	}
}

?>