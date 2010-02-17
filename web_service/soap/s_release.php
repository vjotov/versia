<?php

function getReleaseList($product_id, $session_id) {
	$result = cd_release::load_ls_by_prd($product_id, $session_id);
	return $result;
}

function createReleaseOneSource($product_id, $source_release_id, $new_release_name, $session_id) {
	$result = cd_release::create_release_one_src($product_id, $source_release_id, $new_release_name, $session_id);
	return $result;		
}
function deleteRelease(int $product_id) {
	return array( 
			'err_code' => -1,
			'err_msgs' => 'Not implemented');
}
function getMasterWorkspace(int $release_id) {
}

//////////////
class s_release { 
	function registerComplexTypes($server){
		// Commenter - this type first declaration is in s_product.php
		/*$server->wsdl->addComplexType(
			'Error',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'err_code' => array('name'=>'err_code', 'type'=>'xsd:int'),
				'err_msgs' => array('name'=>'err_msgs', 'type' => 'xsd:string')
			)
		); //*/
		$server->wsdl->addComplexType(
			'Release',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'release_id' => array('name' => 'release_id','type' => 'xsd:int'),
				'product_id' => array('name' => 'product_id','type' => 'xsd:int'),
				'release_name' => array('name' => 'release_name', 'type' => 'xsd:string'),
				'master_workspace_id' => array('name' => 'master_workspace_id','type' => 'xsd:int')
			)
		);
		$server->wsdl->addComplexType(
			'ReleaseResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'err_code','type' => 'tns:Error'),
				'release' => array('name' => 'release', 'type' => 'tns:Release')
			)
		);
		$server->wsdl->addComplexType(
			'ReleaseArray',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'tns:Release[]')
			),
			'tns:Release'
		);
		$server->wsdl->addComplexType(
			'ReleaseArrayResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'err_code','type' => 'tns:Error'),
				'release_list' => array('name' => 'releases', 'type' => 'tns:ReleaseArray')
			)
		);
		$server->wsdl->addComplexType(
			'IntArray',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'xsd:int[]')
			),
			'xsd:int'
		);
	}
	function registerFncs($server) {
		$server->register("getReleaseList",
			array('product_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:ReleaseArrayResponce'),
			'urn:versia',
			'urn:versia#getProduct'
		);
		$server->register("createReleaseOneSource",
			array('product_id' => 'xsd:int', 'source_release_id' => 'xsd:int', 'new_release_name' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:ReleaseResponce'),
			'urn:versia',
			'urn:versia#createRelease'
		);
	}
}


?>