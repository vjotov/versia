<?php
function createWorkItem($release_id, $vo_id, $vp_vp, $wi_name, $session_id){
	$result = cd_workitem::create_workitem($release_id, $vo_id, $vp_vp, $wi_name, $session_id);
	return $result;
}
function updateWorkItem($wi_id, $wi_name, $session_id) {
	$result = cd_workitem::update_workitem($wi_id, $wi_name, $session_id);
	return $result;
}
function attachWorkItem($wi_id, $ws_id, $session_id) {
	$result = cd_workitem::attach_workitem($wi_id, $ws_id, $session_id); 
	return $result;
}
function detachWorkItem ($wi_id, $ws_id, $session_id) {
	$result = cd_workitem::detach_workitem($wi_id, $ws_id, $session_id);
	return $result;
}
function getWorkItemList ($release_id, $session_id) {
	$result = cd_workitem::get_workitem_list($release_id, $session_id);
	return $result;
}
function getAttachedWorkItemList($ws_id, $session_id) {
	$result = cd_workitem::get_attached_workitem_list($ws_id, $session_id);
	return $result;
}

//////////////
class s_workitem { 
	function registerComplexTypes($server){
		$server->wsdl->addComplexType(
			'WorkItem',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'wi_id' => array('name' => 'wi_id','type' => 'xsd:int'),
				'release_id' => array('name' => 'release','type' => 'xsd:int'),
				'vo_id' => array('name' => 'vo_id','type' => 'xsd:int'),
				'vp_id' => array('name' => 'vp_id','type' => 'xsd:int'),
				'name' => array('name' => 'name','type' => 'xsd:string')
			)
		);
		$server->wsdl->addComplexType(
			'WorkItemList',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'tns:WorkItem[]')
			),
			'tns:WorkItem'
		);
		$server->wsdl->addComplexType(
			'WorkItemListResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'error','type' => 'tns:Error'),
				'workitem_list' => array('name' => 'workitem_list', 'type' => 'tns:WorkItemList')
			)
		);
	}
	function registerFncs($server) {
		$server->register("createWorkItem",
			array('release_id' => 'xsd:int',
						'vo_id' => 'xsd:int',
						'vp_vp' => 'xsd:int',
						'wi_name' => 'xsd:string', 
						'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#createWorkItem'
		);
		$server->register("updateWorkItem",
			array('wi_id' => 'xsd:int',
						'wi_name' => 'xsd:string', 
				 	  'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#updateWorkItem'
		);
		$server->register("attachWorkItem",
			array('wi_id' => 'xsd:int',
			      'ws_id' => 'xsd:int', 
				 	  'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#attachWorkItem'
		);
		$server->register("detachWorkItem",
			array('wi_id' => 'xsd:int',
			      'ws_id' => 'xsd:int', 
				 	  'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#detachWorkItem'
		);
		$server->register("getWorkItemList",
			array('release_id' => 'xsd:int',
		 			  'session_id' => 'xsd:int'),
			array('return' => 'tns:WorkItemListResponce'),
			'urn:versia',
			'urn:versia#getWorkItemList'
		);
		$server->register("getAttachedWorkItemList",
			array('ws_id' => 'xsd:int',
		 			  'session_id' => 'xsd:int'),
			array('return' => 'tns:WorkItemListResponce'),
			'urn:versia',
			'urn:versia#getAttachedWorkItemList'
		);
	}
}

?>