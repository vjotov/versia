<?php

function getProduct($product_id, $session_id) {
	$right = hasRights($session_id, 'get_product');
  if($right['error']['err_code'] != 0)
  	return $right;   
	$result = cd_product::load_by_id($product_id);
	return $result;
}
function getProductList($session_id) {
	$right = hasRights($session_id, 'get_product');
  if($right['error']['err_code'] != 0)
  	return $right;   
	$result = cd_product::get_list();
	return $result;
}
function createProduct($product_name, $session_id) {
	$right = hasRights($session_id, 'create_product');
  if($right['error']['err_code'] != 0)
  	return $right['error'];  
	$result = cd_product::create_product($product_name);
	return $result;
}
function updateProduct($product_id, $product_name, $session_id) {
	$right = hasRights($session_id, 'update_product');
  if($right['error']['err_code'] != 0)
  	return $right['error']; 
	$result = cd_product::update_product($product_id, $product_name);
	return $result;
}
function deleteProduct($product_id, $session_id) {
	return array( 
			'err_code' => -1,
			'err_msgs' => 'Not implemented');
}

//////////////
class s_product { 
	function registerComplexTypes($server){
		$server->wsdl->addComplexType(
			'Error',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'code' => array('name'=>'err_code','type'=>'xsd:int'),
				'message' => array('name'=>'err_msgs', 'type' => 'xsd:string')
			)
		);
		$server->wsdl->addComplexType(
			'Product',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'product_id' => array('name' => 'product_id','type' => 'xsd:string'),
				'product_name' => array('name' => 'product_name', 'type' => 'xsd:string')
			)
		);
		$server->wsdl->addComplexType(
			'ProductResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'err_code','type' => 'tns:Error'),
				'product' => array('name' => 'product', 'type' => 'tns:Product')
			)
		);
		$server->wsdl->addComplexType(
			'ProductArray',
			'complexType',
			'array',
			'',
			'SOAP-ENC:Array',
			array(),
			array(
				array('ref' => 'SOAP-ENC:arrayType', 'wsdl:arrayType' => 'tns:Product[]')
			),
			'tns:Product'
		);
		$server->wsdl->addComplexType(
			'ProductArrayResponce',
			'complexType',
			'struct',
			'all',
			'',
			array(
				'error' => array('name' => 'err_code','type' => 'tns:Error'),
				'product_list' => array('name' => 'product_list', 'type' => 'tns:ProductArray')
			)
		);
	}
	function registerFncs($server) {
		$server->register("getProduct",
			array('product_id' => 'xsd:int', 'session_id' => 'xsd:int' ),
			array('return' => 'tns:ProductResponce'),
			'urn:versia',
			'urn:versia#getProduct'
		);
		$server->register("getProductList",
			array('session_id' => 'xsd:int'),
			array('return' => 'tns:ProductArrayResponce'),
			'urn:versia',
			'urn:versia#getProductArray'
		);
		$server->register("createProduct",
			array('product_name' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#createProduct'
		);
		$server->register("updateProduct",
			array('product_id' => 'xsd:int','new_product_name' => 'xsd:string', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#updateProduct'
		);
		$server->register("deleteProduct",
			array('product_id' => 'xsd:int', 'session_id' => 'xsd:int'),
			array('return' => 'tns:Error'),
			'urn:versia',
			'urn:versia#deleteProduct'
		);
	}
}


?>