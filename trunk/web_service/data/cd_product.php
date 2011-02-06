<?php

class cd_product { 
	static public function load_by_id($id) { 
    	try{
			$product_arr = cd_executer::execQuery("cd_product:load_by_id", 
				"SELECT product_id, product_name FROM t_product WHERE pr_id='".$id."'");
			
			if(sizeof($product_arr) != 1) {
				throw new Exception("No such product");
			}
			return array( 
				'result' => $product_arr[0],
				'error' => array('code' => 0, 'message' => 'Successful')
			);
   		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result' => array());
		}
	}
	static public function get_list() {
		try{
			$products = cd_executer::execQuery("cd_product:get_list", 
				"SELECT product_id, product_name FROM t_product ORDER BY product_name");
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'result' => $products);
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result' => array());
		}
	}	
	static public function create_product($product_name) {
		// CREATION OF NEW PRODUCT, ZERO RELEASE & MASTER WORKSPACE
		try {
			cd_executer::beginTransaction();
			cd_executer::execQuery("cd_product:create_product", 
				"INSERT INTO t_product (product_name) VALUES ('".$product_name."')");
			$product_id = get_last_insert_id();
			cd_executer::execQuery("cd_product:create_product",
				"INSERT INTO t_release (product_id, release_name) VALUES (".$product_id.", 'Zero Release')");
			$release_id = get_last_insert_id();
			cd_workspace::create_master_workspace($release_id);
			cd_executer::commitTransaction();
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'result' => array());
		} catch (Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result' => array());
		}
		
	}
	static public function update_product($id, $product_name) {
		try {
			$product_arr = cd_executer::execQuery("cd_product:load_by_id", 
				"UPDATE t_product SET product_name='".$product_name."' WHERE product_id='".$id."'");
			return array(
				'error' => array('code' => 1, 'message' => 'Successful'),
				'result'=> array());
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result' => array());
		}
	}

}

?>