<?php

class cc_prd_edit { // extends cc_abstract_controller {
	public function perform() {
		$pr_id = $_GET["pr_id"];
		$product = new cd_product();
		$product->load($pr_id);
		
		$result = array();
		$result['product'] = $product;
		
		return $result;
	}
}