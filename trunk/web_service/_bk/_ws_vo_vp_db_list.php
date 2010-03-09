<?php

class ws_vo_vp_db_list {
		
	function get_local_vo_vp_ls ($ws) {
		global $mdb;

		$query="SELECT * FROM t_ws_vo_p WHERE ws_id='".$ws->ws_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		$list = array();
		if($resultset->numRows() >= 1) {
			$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
			foreach($set as $value) {
				$ws_vo_vp = new ws_vo_vp_db( $value['ws_id'], $value['vo_id'], $value['vp_id']);
				$ws_vo_vp->load_components();
				$list[] = $ws_vo_vp;
			}
		}
		return $list;
	}
	function get_visible_vo_vp_ls($start_ws) {
		//$current_ws_id = $start_ws->ws_id;
		$master_ws = $start_ws->get_master_ws();
		$master_ws_id = $master_ws->ws_id;
		
		$result_list = array();
		$current_ws = $start_ws;
		$i=0;
		while($current_ws->parent_ws_id != 0) {
			echo ($i.' '.$current_ws->parent_ws_id);
			$tmp_ls = $this->get_local_vo_vp_ls($current_ws);
			$result_list = array_merge($result_list, $tmp_ls);
			$current_ws = $current_ws->get_parent();
		}
				
		$tmp_ls = $this->get_local_vo_vp_ls($master_ws);
		$result_list = array_merge($result_list, $tmp_ls);
		
		return $result_list;
	}
}

?>