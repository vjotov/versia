<?php

function getProduct($params) {
	$result = cd_product::load_by_id($params['product_id']);
	$responce = array();
	$responce['result'] = $result['product'];
  $responce['error'] = $result['error'];
	return $responce;
}
function getProductList() { // OK
	$result = cd_product::get_list();
	$responce = array();
	$responce['result'] = $result['product_list'];
  $responce['error'] = $result['error'];
	return $responce;
}
function createProduct($params) {
	$result = cd_product::create_product($params['product_name']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result['error'];
	return $responce;
}
function updateProduct($params) {
	$result = cd_product::update_product($params['product_id'], $params['product_name']);
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = $result['error'];
	return $responce;
}
function deleteProduct($product_id) {
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = array('code' => -1, 'message' => 'Not implemented');
	return $responce;
}

//////////////
class j_product { 

	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'getProduct', 
			'params' => array('product_id' => 'number'), 
			'permition' => 'get_product');
		$functions[] = array('method' => 'getProductList', 
			'params' => array(), 
			'permition' => 'get_product');
		$functions[] = array('method' => 'createProduct', 
			'params' => array('product_name' => 'string'), 
			'permition' => 'create_product');
		$functions[] = array('method' => 'updateProduct', 
			'params' => array('product_id' => 'number','new_product_name' => 'string'), 
			'permition' => 'update_product');
		$functions[] = array('method' => 'deleteProduct', 
			'params' => array('product_id' => 'number'), 
			'permition' => 'delete_product');
		return $functions;
	}

}


?>