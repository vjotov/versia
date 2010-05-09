<?php

class cc_prd_view { // extends cc_abstract_controller {
	public function perform() {
		$pr_id = $_GET["pr_id"];
		$product = new cd_product();
		$product->load($pr_id);
		
		$result = array();
		$result['product'] = $product;
		
		$rel_ls = cd_release::load_ls_by_prd($pr_id); 
		$result['releases'] = $rel_ls; 
		return $result;
	}
}