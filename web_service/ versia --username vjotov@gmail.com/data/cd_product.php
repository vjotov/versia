<?php

class cd_product { 
	static public function load_by_id($id) {
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
		global $mdb;
    	
		$query="SELECT pr_id AS product_id, name AS product_name FROM t_product ORDER BY name";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $resultset->getMessage();
			$err_['message'] .= '\n cd_product.get_list() - 1';
			return array('error' => $err_);
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$products = array();
		foreach($set as $value) {
			$products[] = array ('product_id' => $value["product_id"], 'product_name' => $value['product_name']);
		}
		
		return array(
			'error' => array('code' => 0, 'message' => 'Successful'),
			'product_list' => $products);
	}	
	static public function create_product($product_name) {
		global $mdb;
		    	
		$err_ = array();
		// CREATION OF NEW PRODUCT & ZERO RELEASE
		$query = "INSERT INTO t_product (name) VALUES ('".$product_name."')";
		$result = $mdb->query($query);
		if(PEAR::isError($result)) {
			$err_['code'] = 1;
			$err_['message'] = 'Failed to issue query, error message : ' . $result->getMessage();
			$err_['message'] .= ' -- cd_product::create_product() - 1'.$query;
			return $err_;
		}
		$err_['code'] = 0;
		$err_['message'] = 'Sucessful';
		return $err_;
		
	}
	static public function update_product($id, $product_name) {
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