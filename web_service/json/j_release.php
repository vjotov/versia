<?php

function getReleaseList($params) {
	$result = cd_release::load_ls_by_prd($params['product_id']);

	$responce = array();
	$responce['result'] = $result['release_list'];
  $responce['error'] = $result['error'];
	return $responce;
}
function createReleaseOneSource($params) {
	$result = cd_release::create_release_one_src(
		$params['product_id'], 
		$params['source_release_id'], 
		$params['new_release_name']);
		
	$responce = array();
	$responce['result'] = $result['release'];
  $responce['error'] = $result['error'];
	return $responce;
}
function deleteRelease($product_id) {
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = array('code' => -1, 'message' => 'Not implemented');
	return $responce;
}
function getMasterWorkspace( $release_id) {
	$responce = array();
	$responce['result'] = array();
  $responce['error'] = array('code' => -1, 'message' => 'Not implemented');
	return $responce;
}

//////////////
class j_release { 
	function registerFncs() {
		$functions = array();
		$functions[] = array('method' => 'getReleaseList', 
			'params' => array('product_id' => 'number'), 
			'permition' => 'get_release');
		$functions[] = array('method' => 'createReleaseOneSource', 
			'params' => array('product_id' => 'number', 'source_release_id' => 'number', 'release_name' => 'string'), 
			'permition' => 'create_release');
		return $functions;
	}
}
?>