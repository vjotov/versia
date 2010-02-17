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

class s_model { 
	function registerComplexTypes($server){
		$server->wsdl->addComplexType(
			'Model',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'model_id' => array('name'=>'model_id','type'=>'xsd:int'),
				'model_name' => array('name'=>'model_name', 'type' => 'xsd:string'),
				'model_definition' => array('name'=>'model_definition', 'type' => 'xsd:string')
			)
		);
		$server->wsdl->addComplexType(
			'ModelResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name'=>'err_code','type'=>'tns:Error'),
				'model' => array('name'=>'model_name', 'type' => 'tns:Model')
			)
		);
		$server->wsdl->addComplexType(
			'ModelList',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'tns:Model[]')
			),
			'tns:Model'
		);
		$server->wsdl->addComplexType(
			'ModelListResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name'=>'err_code','type'=>'tns:Error'),
				'model_list' => array('name'=>'model_list', 'type' => 'tns:ModelList')
			)
		);
	}
	function registerFncs($server) {
		$server->register("getModel",
			array('model_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:ModelResponce'),
			'urn:versia',
			'urn:versia#getModel'
		);
		$server->register("getModelList",
			array('session_id' => 'xsd:int'),
			array('return' => 'tns:ModelListResponce'),
			'urn:versia',
			'urn:versia#getModelList'
		);
		$server->register("createModel",
			array('name' => 'xsd:string', 'definition' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:ModelResponce'),
			'urn:versia',
			'urn:versia#createModel'
		);
		$server->register("updateModel",
			array('id' => 'xsd:int', 'name' => 'xsd:string', 'definition' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#updateModel'
		);
		$server->register("deleteModel",
			array('model_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#deleteModel'
		);
	}
}
?>