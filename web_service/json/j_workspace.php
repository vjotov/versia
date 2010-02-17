<?php

function getWorkspace($params) {
	$result = cd_workspace::get_workspace($params['workspace_id']);
	$responce = array();
	$responce['result'] = $result['workspace'];
  $responce['error'] = $result['error'];
	return $responce;
}
function getMaterWorkspace($params) {
	$result = cd_workspace::get_mater_workspace($params['ws_id']);
	$responce = array();
	$responce['result'] = $result['workspace'];
  $responce['error'] = $result['error'];
	return $responce;
}
function createWorkspace($params) {
	$result = cd_workspace::create_workspace($params['ancestor_workspace_id'], $params['new_workspace_name']);
	$responce = array();
	$responce['result'] = $result['workspace'];
  $responce['error'] = $result['error'];
	return $responce;
}
function deleteWorkspace($params) {
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = array('code' => -1, 'message' => 'Not implemented');
	return $responce;
}
function updateWorkspace($params) {
	$result = cd_workspace::update_workspace($params['workspace_id'], $params['new_workspace_name']);
	$responce = array(); 
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function getOffspringWorkspaces($params) {
	$result = cd_workspace::get_offspring_workspaces($params['workspace_id']);
	$responce = array();
	$responce['result'] = $result['workspaces'];
  $responce['error'] = $result['error'];
	return $responce;
}
function getReleaseWorkspaces($params) {
	$result = cd_workspace::get_workspace_list_for_release($params['release_id']);
	$responce = array();
	$responce['result'] = $result['workspaces'];
  $responce['error'] = $result['error'];
	return $responce;
}


//////////////
class j_workspace { 
	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'getWorkspace', 
			'params' => array('workspace_id' => 'number'), 
			'permition' => 'get_workspace');
		$functions[] = array('method' => 'getMaterWorkspace', 
			'params' => array('workspace_id' => 'number'), 
			'permition' => 'get_workspace');
		$functions[] = array('method' => 'createWorkspace', 
			'params' => array('ancestor_workspace_id' => 'number','new_workspace_name' => 'string'), 
			'permition' => 'create_worspace');
		$functions[] = array('method' => 'updateWorkspace', 
			'params' => array('workspace_id' => 'number','new_workspace_name' => 'string'),
			'permition' => 'update_workspace');
		$functions[] = array('method' => 'deleteWorkspace', 
			'params' => array('workspace_id' => 'number'), 
			'permition' => 'delete_workspace');
		$functions[] = array('method' => 'getOffspringWorkspaces', 
			'params' => array('workspace_id' => 'number'),
			'permition' => 'get_workspace');
		$functions[] = array('method' => 'getReleaseWorkspaces', 
			'params' => array('release_id' => 'number'),
			'permition' => 'get_workspace');
		return $functions;		
	}
}


?>