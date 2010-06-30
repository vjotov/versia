<?php

function getWorkspace($workspace_id, $session_id) {
	$result = cd_workspace::get_workspace($workspace_id, $session_id);
	return $result;
}
function getMaterWorkspace($ws_id, $session_id) {
	$result = cd_workspace::get_mater_workspace($ws_id, $session_id);
	return $result;
}
function createWorkspace($ancestor_ws_id, $new_ws_name, $session_id) {
	$result = cd_workspace::create_workspace($ancestor_ws_id, $new_ws_name, $session_id);
	return $result;
}
function deleteWorkspace($workspace_id, $session_id) {
	return array( 
			'err_code' => -1,
			'err_msgs' => 'Not implemented');
}
function updateWorkspace($workspace_id, $new_workspace_name, $session_id) {
	$result = cd_workspace::update_workspace($workspace_id, $new_workspace_name, $session_id);
	return $result;
}
function getOffspringWorkspaces($workspace_id, $session_id) {
	$result = cd_workspace::get_offspring_workspaces($workspace_id, $session_id);
	return $result;
}


//////////////
class s_workspace { 
	function registerComplexTypes($server){
		/*
		$server->wsdl->addComplexType(
			'Error',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'err_code' => array('name'=>'err_code','type'=>'xsd:int'),
				'err_msgs' => array('name'=>'err_msgs', 'type' => 'xsd:string')
			)
		); //*/
		$server->wsdl->addComplexType(
			'Workspace',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'workspace_id' => array('name' => 'workspace_id', 'type' => 'xsd:int'),
				'release_id' => array('name' => 'release_id', 'type' => 'xsd:int'),
				'ancestor_workspace_id' => array('name' => 'ancestor_workspace_id', 'type' => 'xsd:int'),
				'workspace_name' => array('name' => 'workspace_name', 'type' => 'xsd:string')
			)
		);
		$server->wsdl->addComplexType(
			'WorkspaceResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'err_code', 'type' => 'tns:Error'),
				'workspace' => array('name' => 'workspace', 'type' => 'tns:Workspace')
			)  
		);
		$server->wsdl->addComplexType(
			'WorkspaceArray',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'tns:Workspace[]')
			),
			'tns:Workspace'
		);
		$server->wsdl->addComplexType(
			'WorkspaceArrayResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'err_code', 'type' => 'tns:Error'),
				'workspaces' => array('name' => 'workspaces', 'type' => 'tns:WorkspaceArray')
			)  
		);
	}
	function registerFncs($server) {
		$server->register("getWorkspace",
			array('workspace_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:WorkspaceResponce'),
			'urn:versia',
			'urn:versia#getWorkspace'
		);
		$server->register("getMaterWorkspace",
			array('workspace_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:WorkspaceResponce'),
			'urn:versia',
			'urn:versia#getMaterWorkspace'
		);
		$server->register("createWorkspace",
			array('ancestor_workspace_id' => 'xsd:int','new_workspace_name' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:WorkspaceResponce'),
			'urn:versia',
			'urn:versia#createWorkspace'
		);
		$server->register("updateWorkspace",
			array('workspace_id' => 'xsd:int','new_workspace_name' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#updateWorkspace'
		);
		$server->register("deleteWorkspace",
			array('workspace_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#deleteWorkspace'
		);
		$server->register("getOffspringWorkspaces",
			array('workspace_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:WorkspaceArrayResponce'),
			'urn:versia',
			'urn:versia#getOffspringWorkspaces'
		);
	}
}


?>