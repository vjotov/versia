<?php

class cc_prd_list { // extends cc_abstract_controller {
	public function perform() {
		$list = cd_product::get_list();
		$result = array('prd_list' => $list);
		return $result;
	}
}