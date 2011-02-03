<?php

class cd_product { 
	static public function load_by_id($id) { // TODO REWRITE
    global $mdb; 

		$query="SELECT pr_id AS product_id, name AS product_name FROM t_product WHERE pr_id='".$id."'";
		$resultset = $mdb->query($query); 
		
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_product::load_by_id($id) - 1';
			return array('error' => $err_);
		}
		if($resultset->numRows() != 1) {
			$err_['code'] = 1;
			$err_['message'] = 'cd_product->get('.$id.') - no such product';
			$err_['message'] .= '\n cd_product::load_by_id($id) - 2';
			return array('error' => $err_);
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return array( 
			'product' => $set[0],
			'error' => array('code' => 0, 'message' => 'Successful')
		);
	}
	static public function get_list() {
		try{
			$products = cd_executer::selectQuery("cd_product:get_list", 
				"SELECT product_id, product_name FROM t_product ORDER BY product_name");
			
			return array(
				'error' => array('code' => 0, 'message' => 'Successful'),
				'product_list' => $products);
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'message'=> 'Error');
		}
	}	
	static public function create_product($product_name) {
		// CREATION OF NEW PRODUCT, ZERO RELEASE & MASTER WORKSPACE
		try {
			$r1 = cd_executer::selectQuery("cd_product:create_product", 
				"INSERT INTO t_product (product_name) VALUES ('".$product_name."')");
			$product_id = get_last_insert_id();
			$r2 = cd_executer::selectQuery("cd_product:create_product",
				"INSERT INTO t_release (pr_id, name) VALUES (".$product_id.", 'Zero Release')");
			$release_id = get_last_insert_id();
			$r3 = cd_executer::selectQuery("cd_product:create_product",
				"INSERT INTO t_workspace (release_id, name) VALUES (".$release_id.", 'Master Workspace')");
			$master_workspace_id = get_last_insert_id();
			$r4 = cd_executer::selectQuery("cd_product:create_product",
				"UPDATE t_release SET master_ws_id = '.$master_workspace_id.' WHERE release_id = '.$release_id.' ");
			
			$res = array();
			$res['code'] = 0;
			$res['message'] = 'Sucessful';
			return $res;
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'message'=> 'Error');
		}
		
	}
	static public function update_product($id, $product_name) {// TODO REWRITE
		global $mdb;
		    	
		$err_ = array();
		$result = $mdb->query("UPDATE t_product SET name='".$product_name."' WHERE pr_id='".$id."'");
		if(PEAR::isError($result)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $result->getMessage();
			$err_['message'] .= 'cd_product.update_product() - 1';
			return $err_;
		}
		$err_['code'] = 0;
		$err_['message'] = 'Successful';
		return $err_;
	}

}

?>