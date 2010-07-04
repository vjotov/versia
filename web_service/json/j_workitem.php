<?php
function createWorkItem($params){
	$result = cd_workitem::create_workitem($params['release_id'], $params['vo_id'], $params['vp_vp'], $params['wi_name'], $params['ws_id']);
	$responce = array();
	$responce['result'] = ayyar();
  $responce['error'] = $result;
	return $responce;
}
function updateWorkItem($params) {
	$result = cd_workitem::update_workitem($params['wi_id'], $params['wi_name']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function attachWorkItem($params) {
	$result = cd_workitem::attach_workitem($params['wi_id'], $params['ws_id']); 
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function detachWorkItem ($params) {
	$result = cd_workitem::detach_workitem($params['wi_id'], $params['ws_id']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function getWorkItemList ($params) {
	$r1 = getAttachedWorkItemList($params);
	$r2 = getNotAttachedWorkItemList($params);
	$responce = array();
	$responce['result'] = array('attached' => $r1['result'], 'not_attached' => $r2['result']);
  $responce['error'] = $r2['error'];
	return $responce;
}
function getAttachedWorkItemList($params) {
	$result = cd_workitem::get_attached_workitem_list($params['ws_id']);
	$responce = array();
	$responce['result'] = $result['workitem_list'];
  $responce['error'] = $result['error'];
	return $responce;
}
function getNotAttachedWorkItemList($params) {
	$result = cd_workitem::get_not_attached_workitem_list($params['ws_id']);
	$responce = array();
	$responce['result'] = $result['workitem_list'];
  $responce['error'] = $result['error'];
	return $responce;
}

//////////////
class j_workitem { 
	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'createWorkItem', 
			'params' => array('release_id' => 'number', 'vo_id' => 'number', 'vp_vp' => 'number', 'wi_name' => 'string', 'ws_id' => 'number'), 
			'permition' => 'create_workitem');
		$functions[] = array('method' => 'updateWorkItem', 
			'params' => array('wi_id' => 'number', 'wi_name' => 'string'), 
			'permition' => 'update_workitem');
		$functions[] = array('method' => 'attachWorkItem', 
			'params' => array('wi_id' => 'number', 'ws_id' => 'number'), 
			'permition' => 'attach_detach_workitem');
		$functions[] = array('method' => 'detachWorkItem', 
			'params' => array('wi_id' => 'number', 'ws_id' => 'number'), 
			'permition' => 'attach_detach_workitem');
		$functions[] = array('method' => 'getWorkItemList', 
			'params' => array('ws_id' => 'number'), 
			'permition' => 'get_workitem');
		$functions[] = array('method' => 'getAttachedWorkItemList', 
			'params' => array('ws_id' => 'number'), 
			'permition' => 'get_workitem');
		$functions[] = array('method' => 'getNotAttachedWorkItemList', 
			'params' => array('ws_id' => 'number'), 
			'permition' => 'get_workitem');
		return $functions;
	}
}

?>