<?php

class cd_release{
	static public function load_ls_by_prd($product_id) {
		try {
    	
			$release_arr = cd_executer::execQuery("cd_release:load_ls_by_prd", 
				"SELECT release_id, product_id, release_name, master_ws_id "
				."FROM t_release WHERE product_id='".$product_id."' ORDER BY release_name"); 
						
			return array(
				'error' => array('code' => '0','message' => 'Successful'),
				'result' => $release_arr);
		} catch (Exception $e) {
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result'=> array());
		}
	}
	static public function create_release_one_src($product_id, $source_release_id, $new_release_name) {
		try {
			cd_executer::beginTransaction();
			$is_ok = cd_release::check_release_existence($product_id, $source_release_id);
			if($is_ok == false) {
				cd_executer::rollbackTransaction();
				return array(
					'error' => array('code' => 100, 'message' => 'The source releases does not exist.'),
					'result'=> array());
			}
			
			$release_id = cd_release::create_new_release($product_id, $new_release_name);
			if($release_id == false) {
				cd_executer::rollbackTransaction();
				return array(
					'error' => array('code' => 101,'message' => 'Error during new release creation.'),
					'result'=> array());
			}
			
			cd_executer::execQuery("cd_release:create_releae_arcs",
				"INSERT INTO t_release_vgraph_arcs (source_release_id, target_release_id) VALUES ('".$source_release_id."', '".$target_release_id."') ");
			$master_workspace_id = cd_workspace::create_master_workspace($release_id);
			cd_executer::commitTransaction();
			
			return array(
					'error' => array('code' => 0, 'message' => 'Successful'),
					'release' => array(
						'release_id' => $release_id,
						'product_id' => $product_id,
						'release_name' => $new_release_name,
						'master_workspace_id' => $master_workspace_id));
		}catch (Exception $e) {
			cd_executer::rollbackTransaction();
			return array(
				'error' => array('code' => 1, 'message' => $e->getMessage()),
				'result' => array());
		}
	}
	
	///////////////////////// 
	// PRIVATE FUNCTION    //
	/////////////////////////
	static private function check_release_existence($product_id, $release_id) {
		$resultset = cd_executer::execQuery("cd_release:check_release_existence", 
			"SELECT COUNT(*) AS c FROM t_release WHERE product_id='".$product_id."' AND release_id='".$release_id."'");
		if( $resultset[0]['c'] == 1) return true;
		else return flase;
	}
	static private function create_new_release($product_id, $release_name) {
		$resultset = cd_executer::execQuery("cd_release:create_new_release",
			"INSERT INTO t_release (product_id, release_name) VALUES ('".$product_id."', '".$release_name."') ");
		return get_last_insert_id(); 
	}
}

?>