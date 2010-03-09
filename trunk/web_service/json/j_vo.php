<?php
function createVersionedObject($params){							 	
	$result = cd_vo::create_versioned_object($params['ws_id'], 
											 $params['vo_name'], 
											 $params['vo_datum'],
											 $params['uid'],
											 $params['type'],
											 $params['constructs']
											 );
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result['error'];
	return $responce;
}
function getVisibleVersionedObjectList($params) {
	$result = cd_vo::get_visible_vo_list($params['ws_id']); 
	$responce = array();
	$responce['result'] = $result['vo_list'];
  $responce['error'] = $result['error'];
	return $responce;
}
function saveVersionedObjectState ($params) {
	$result = cd_vo::save_versioned_object_state($params['ws_id'], 
												$params['vo_id'], 
												$params['vo_datum'], 
												$params['vo_name'],
												$params['uid'],
											 	$params['type'],
											 	$params['constructs']
												);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function publishVersionedObjectState($params) {
	$result = cd_vo::publish_versioned_object_state($params['ws_id'], $params['vo_id']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function putbackVersionedObjectState($params) {
	$result = cd_vo::putback_versioned_object_state($params['ws_id'], $params['vo_id']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result;
	return $responce;
}
function viewVersionedObjectDistribution($params) {
	$result = cd_vo::vew_versioned_object_distribution($params['vo_id'], $params['release_id']);
	$responce = array();
	$responce['result'] = $result['ws_list'];
  $responce['error'] = $result['error'];
  return $responce;
}

//////////////
class j_vo { 
	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'createVersionedObject', 
			'params' => array('ws_id' => 'number', 'vo_name' => 'string', 'vo_datum' => 'string', 'uid' => 'number', 'type' => 'number', 'constructs' => 'number' ), 
			'permition' => 'create_versioned_object');
		$functions[] = array('method' => 'getVisibleVersionedObjectList', 
			'params' => array('ws_id' => 'number'), 
			'permition' => 'get_versioned_object');
		$functions[] = array('method' => 'saveVersionedObjectState', 
			'params' => array('ws_id' => 'number', 'vo_id' => 'number', 'vo_datum' => 'string', 'vo_name' => 'string', 'uid' => 'number', 'type' => 'number', 'constructs' => 'number'), 
			'permition' => 'save_versioned_object');
		$functions[] = array('method' => 'publishVersionedObjectState', 
			'params' => array('ws_id' => 'number', 'vo_id' => 'number'), 
			'permition' => 'publish_versioned_object');
		$functions[] = array('method' => 'putbackVersionedObjectState', 
			'params' => array('ws_id' => 'number', 'vo_id' => 'number'), 
			'permition' => 'putback_versioned_object');
		$functions[] = array('method' => 'viewVersionedObjectDistribution',
			'params' => array('vo_id' => 'number', 'release_id' => 'number'),
			'permition' => 'view_versioned_object_distribution');
		return $functions;
	}
}

?>