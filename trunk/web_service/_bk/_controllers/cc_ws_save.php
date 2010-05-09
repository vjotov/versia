<?php

class cc_ws_save {
	function perform() {
		global $mdb;
		$parent_ws_id = $_POST['parent_ws_id'];
		$new_name = $_POST['new_name']; 
		
		$trx = $mdb->beginTransaction();
		if(PEAR::isError($trx)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		$parent_ws = new cd_ws();
		$parent_ws->load($parent_ws_id);
		
		$new_workspace = new cd_ws($new_name, $parent_ws_id, $parent_ws->domain_id, $ws_id=NULL); 
		$new_workspace->save();
		
		$trx = $mdb->commit();
		if(PEAR::isError($trx)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		$domain = new cd_d();
		$domain->load($parent_ws->domain_id); 
		$ws_h_ls = cd_ws::load_ws_hirarchy_in_domain($domain);
		
		$result['ws_h_ls'] = $ws_h_ls;
		return $result;
	}
}

?>