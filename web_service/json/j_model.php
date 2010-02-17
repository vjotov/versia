<?php

function getModel($id, $session_id) {
	$result = cd_model::load_by_id($id, $session_id);
	return $result;
}
function getModelList($session_id) {
	$result = cd_model::load_list($session_id);
	return $result;
}
function createModel($model_name, $model_definition, $session_id) {
	$result = cd_model::create_model($model_name, $model_definition, $session_id);
	return $result;
}
function updateModel($model_id, $model_name, $model_definition, $session_id) {
	$result = cd_model::update_model($model_id, $model_name, $model_definition, $session_id);
	return $result;
}
function deleteModel($model_id, $session_id) {
	return array( 
			'err_code' => -1,
			'err_msgs' => 'Nnot implemented');
}

class j_model { 
	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'getModel', 
			'params' => array('model_id' => 'number'), 
			'permition' => 'get_model');
		$functions[] = array('method' => 'getModelList', 
			'params' => array(), 
			'permition' => 'get_model');
		$functions[] = array('method' => 'createModel', 
			'params' => array('name' => 'string', 'definition' => 'string'), 
			'permition' => 'create_model');
		$functions[] = array('method' => 'updateModel', 
			'params' => array('id' => 'number', 'name' => 'string', 'definition' => 'string'), 
			'permition' => 'update_model');
		$functions[] = array('method' => 'deleteModel', 
			'params' => array('model_id' => 'number'), 
			'permition' => 'delete_model');
		return $functions;
	}

}
?>