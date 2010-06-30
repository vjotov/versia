<?php

class cc_rls_save { // extends cc_abstract_controller {
	public function perform() {
		global $mdb; 
		$source_releace = new cd_release();
		//$source_release_id = ;
		$source_release_id = $_POST['source_release_id'];
		$source_releace->load($source_release_id);
		$new_release_name = $_POST['name'];
		
		$trx = $mdb->beginTransaction();
		if(PEAR::isError($trx)) {
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		
		// new release
		$new_release = new cd_release(0, $source_releace->pr_id, $new_release_name);
		$new_release->save();
		
		// arc from old to new release
		$new_rls_arc = new cd_release_arc($source_releace->r_id,$new_release->r_id );
		$new_rls_arc->save();
		
		// domain
		$new_domain = new cd_d(0, $new_release->r_id, 0);
		$new_domain->save();
		
		// Todo master WS
		$new_master_ws = new cd_ws("Master", NULL, $new_domain->domain_id);
		$new_master_ws->save();
		
		$new_domain->ws_id = $new_master_ws->ws_id;
		$new_domain->save();
		//*/
		// VOs from old release in master WS
		
		//$vo_vp_handler_ls = cd_release_holder::load_holders($source_releace->r_id);
	//	$vo_vp_handler_ls = cd_release::load_rls_holders($source_releace->r_id);
		
		//cd_workspace_holder::save_by_ws($new_domain->ws_id, $vo_vp_handler_ls);
		//cd_ws_holder::save_by_ws($new_domain->ws_id, $vo_vp_handler_ls);
	//	cd_release::save_ws_holders($new_domain->ws_id, $vo_vp_handler_ls);

		$trx = $mdb->commit();
		if(PEAR::isError($trx)) {
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		echo "The release was successfuly ".$type.". <a href='".HOME_URL."?pr_id=".$source_releace->pr_id."'>Home</a>";
		return array();
	}
}