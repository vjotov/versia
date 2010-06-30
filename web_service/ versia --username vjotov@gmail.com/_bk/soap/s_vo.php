<?php
function createVersionedObject($ws_id, 
															 $vo_name, 
															 $vo_datum, 
															 //$vo_ancestor_id, 
															 //$vo_model, 
															 //$vo_model_place,
															 $session_id){							 	
	$result = cd_vo::create_versioned_object($ws_id, 
											 $vo_name, 
											 $vo_datum, 
											 //$vo_ancestor_id, 
											 //$vo_model, 
											 //$vo_model_place, 
											 $session_id);
	return $result;
}
function getVisibleVersionedObjectList($ws_id, $session_id) {
	$result = cd_vo::get_visible_vo_list($ws_id, 	$session_id); 
	return $result;
}
function saveVersionedObjectState ($ws_id, $vo_id, $new_datum, $session_id) {
	$result = cd_vo::save_versioned_object_state($ws_id, $vo_id, $new_datum, $session_id);
	return $result;
}
function publishVersionedObjectState($in_ws_id, $vo_id, $session_id ) {
	$result = cd_vo::publish_versioned_object_state($in_ws_id, $vo_id, $session_id );
	return $result;
}
function putbackVersionedObjectState($in_ws_id, $vo_id, $session_id ) {
	$result = cd_vo::putback_versioned_object_state($in_ws_id, $vo_id, $session_id );
	return $result;
}

//////////////
class s_vo { 
	function registerComplexTypes($server){
		$server->wsdl->addComplexType(
			'CreateVOResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'new_vo_id' => array('name' => 'new_vo_id','type' => 'xsd:int'),
				'error' => array('name' => 'error','type' => 'tns:Error')
			)
		);
		$server->wsdl->addComplexType(
			'VersionedObjectState',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'vo_id' => array('name' => 'vo_id','type' => 'xsd:int'),
				'vp_id' => array('name' => 'vp_id','type' => 'xsd:int'),
				'vp_name' => array('name' => 'vp_name','type' => 'xsd:string'),
				'vp_datum' => array('name' => 'vp_datum','type' => 'xsd:string'),
				'locked' => array('name' => 'locked','type' => 'xsd:int'),
				'v_vector' => array('name' => 'v_vector','type' => 'xsd:int')
			)
		);
		$server->wsdl->addComplexType(
			'VersionedObjectList',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'tns:VersionedObjectState[]')
			),
			'tns:VersionedObjectState'
		);
		$server->wsdl->addComplexType(
			'VersionedObjectListResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'error','type' => 'tns:Error'),
				'vo_list' => array('name' => 'vo_list', 'type' => 'tns:VersionedObjectList')
			)
		);
	}
	function registerFncs($server) {
		$server->register("getVisibleVersionedObjectList",
			array('ws_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:VersionedObjectListResponce'),
			'urn:versia',
			'urn:versia#getVisibleVersionedObjectList'
		);
		$server->register("createVersionedObject",
			array('ws_id' => 'xsd:int',
			      'vo_name' => 'xsd:string', 
				    'vo_datum' => 'xsd:string', 
				  //'vo_ancestor_id' => 'xsd:int', 
				  //'vo_model' => 'xsd:int', 
				  //'vo_model_place' => 'xsd:string',
			 	  'session_id' => 'xsd:int'),
			array('return' => 'tns:CreateVOResponce'),
			'urn:versia',
			'urn:versia#createVersionedObject'
		);
		$server->register("saveVersionedObjectState",
			array('ws_id' => 'xsd:int',
			      'vo_id' => 'xsd:int', 
				    'new_datum' => 'xsd:string', 
	 			    'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#createVersionedObject'
		);		
		$server->register("publishVersionedObjectState",
			array('in_ws_id' => 'xsd:int',
			      'vo_id' => 'xsd:int', 
	 			    'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#publishVersionedObjectState'
		);
		$server->register("putbackVersionedObjectState",
			array('in_ws_id' => 'xsd:int',
			      'vo_id' => 'xsd:int', 
	 			    'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#putbackVersionedObjectState'
		);
	}
}

?>