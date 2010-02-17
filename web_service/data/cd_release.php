<?php

class cd_release{
	static public function load_ls_by_prd($product_id) {
		global $mdb;
    	
		$query="SELECT release_id AS release_id, pr_id AS product_id, name AS release_name, master_ws_id AS master_workspace_id "
			."FROM t_release WHERE pr_id='".$product_id."' ORDER BY name"; 
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			$err_ = array(
				'code' => '1', 
				'message' => 'Failed to issue query, error message : ' . $resultset->getMessage());
			return array('error' => $err_);
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC); 
		$result = array(
			'error' => array(
				'code' => '0', 	
				'message' => 'Successful'),
			'release_list' => $set);
		return $result;
	}
	static public function create_release_one_src($product_id, $source_release_id, $new_release_name) {
		global $mdb;
		
		$is_ok = cd_release::check_release_existence($product_id, $source_release_id);
		if($is_ok == false) {
			return array(
				'error' => array(
					'code' => 100,
					'message' => 'The source releases does not exist.'));
		}
		
		$release_id = cd_release::create_new_release($product_id, $new_release_name);
		if($release_id == false) {
			return array(
				'error' => array(
					'code' => 101,
					'message' => 'Error during new release creation.'));
		}
		
		$new_r_arcs = cd_release::create_releae_arcs($source_release_id, $release_id);
		if( $new_r_arcs == false) {
			return array(
					'error' => array(
						'code' => 102,
						'message' => 'Error during new release arcs creation.'));
		}
		
		$master_workspace_id = cd_release::create_master_workspace($release_id);
		if( $new_r_arcs == false) {
			return array(
					'error' => array(
						'code' => 103,
						'message' => 'Error during loading master workspace id.'));
		}
		
		return array(
				'error' => array(
					'code' => 0,
					'message' => 'Successful'),
				'release' => array(
					'release_id' => $release_id,
					'product_id' => $product_id,
					'release_name' => $new_release_name,
					'master_workspace_id' => $master_workspace_id));
	}
	
	///////////////////////// 
	// PRIVATE FUNCTION    //
	/////////////////////////
	static private function check_release_existence($product_id, $release_id) {
		global $mdb;
		$query = "SELECT COUNT(*) AS c FROM t_release WHERE pr_id='".$product_id."' AND release_id='".$release_id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) 
			die('Failed to issue query, error message in cd_release::check_release_existence(): ' . $resultset->getMessage());
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		if( $set[0]['c'] == 1) return true;
		else return flase;
	}
	static private function create_new_release($product_id, $release_name) {
		global $mdb; 
		
		$query = "INSERT INTO t_release (pr_id, name) VALUES ('".$product_id."', '".$release_name."') ";
		$result = $mdb->exec($query); 
		if(PEAR::isError($result)) {
			return false;
		}
		return get_last_insert_id(); 
	}
	static private function create_releae_arcs($source_release_id, $target_release_id) {
		global $mdb;
		$query = "INSERT INTO t_release_vgraph_arcs (source_release_id, target_release_id) VALUES ('".$source_release_id."', '".$target_release_id."') ";
		$result = $mdb->query($query);
		if(PEAR::isError($result)) {
			return false;
		}
		return true;
	}
	static private function create_master_workspace($release_id) {
		global $mdb;
		$query = "INSERT INTO t_workspace (release_id, name) VALUES ('".$release_id."', 'Master Workspace') ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) 
			return false;
			
		$new_master_workspace = get_last_insert_id();
		
		$query = "UPDATE t_release SET master_ws_id='".$new_master_workspace."' WHERE release_id='".$release_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			return false;
		}
		return $new_master_workspace;
	}

}

?>