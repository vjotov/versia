<?php

class cc_prd_save { // extends cc_abstract_controller {
	public function perform() {
		$product = new cd_product();
		$product->pr_id = $_POST["pr_id"];
		$product->name = $_POST["name"];
		if($product->save())
			echo "The product was successfuly ".$type.". <a href='".HOME_URL."'>Home</a>";
		
		return array();
	}
}