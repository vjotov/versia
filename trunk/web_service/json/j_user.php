<?php

function getUserList() { // OK
	$result = cd_user::get_user_list();
	$responce = array();
	$responce['result'] = $result['user_list'];
  $responce['error'] = $result['error'];
	return $responce;
}
function createUser($params) {
	$result = cd_user::create_user($params['name'], $params['password']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result['error'];
	return $responce;
}
function updateUserPerminiton($params) {
// TODO: da pomislq kak da predam vsichki permitions
	//update in cd_user.php i GetPermition.java
	$result = cd_user::update_permition($params['user_id'], $params['action_id'], $params['permition_value']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result['error'];
	return $responce;
}
function deleteUser($params) {
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = array('code' => -1, 'message' => 'Not implemented');
	return $responce;
}

//////////////
class j_user { 

	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'getUserList', 
			'params' => array(), 
			'permition' => 'get_user_list');
		$functions[] = array('method' => 'createUser', 
			'params' => array('name' => 'string', 'password' => 'string'), 
			'permition' => 'create_user');
		$functions[] = array('method' => 'updateUserPerminiton', 
			'params' => array('user_id' => 'number', 'action_id' => 'number', 'permition_value' => 'number'), 
			'permition' => 'update_permition');
		$functions[] = array('method' => 'deleteUser', 
			'params' => array('user_id' => 'number'), 
			'permition' => 'delete_product');
		return $functions;
	}

}


?>