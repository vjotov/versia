<?php

class cd_ws_holder {
	var $ws_id	= NULL;
	var $vo_id	= NULL;
	var $vp_id	= NULL;
	var $vo			= NULL;
	var $vp			= NULL;
	var $vis_vector = NULL;
	
	function cd_ws_holder($ws_id = NULL, $vo_id = NULL, $vp_id = NULL) {
		$this->ws_id = $ws_id;
		$this->vo_id = $vo_id;
		$this->vp_id = $vp_id;
	}
	function load_wsvo($ws_id, $vo_id) {
		global $mdb;
		$query="SELECT vo_id, vp_id, ws_id FROM t_workspace_holder WHERE vo_id='".$vo_id."' AND ws_id='".$ws_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('load_wsvo Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {echo $query;
			die('cd_ws_holder->load_wsvo('.$ws_id.' '.$vo_id.') - no such WS_HOLDER');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->ws_id = $set[0]['ws_id'];
		$this->vo_id = $set[0]['vo_id'];
		$this->vp_id = $set[0]['vp_id'];
	}	
	function create() { //TODO
		global $mdb;
		$result = $mdb->query("INSERT INTO t_workspace_holder (ws_id, vo_id, vp_id)"
							." VALUES ( '".$this->ws_id."', '".$this->vo_id."', '".$this->vp_id."' )");
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('ws_h1 Failed to issue query, error message : ' . $trx->getMessage());
			}
			var_dump($result);
			die('ws_h2 Failed to issue query, error message : ' . $result->getMessage()); 
		}
	}	
	function save() { 
		if($this->is_local_vovp()) { // UPDATE local
			$this->update_local_wsvo();
		} else { // CREATE LOCAL FROM PARENT
			$this->create_local_wsvo();
		}
	}
	function delete() { //TODO
	}
		
	/// PRIVATE FUNCTIONS
	private function load_vovp() {
		if($this->vo_id!=NULL && $this->vp_id != NULL) { 
			$vo = new cd_versioned_object();
		  $vo->load($this->vo_id);
		  $this->vo = $vo;
			$vp= new cd_versioned_primitive();
			$vp->load($this->vo_id, $this->vp_id);
			$this->vp = $vp;
		}
	}

	private function is_local_vovp() {
		global $mdb;
		$query="SELECT count(*) AS c FROM t_workspace_holder WHERE vo_id='".$this->vo_id."' AND vp_id='".$this->vp_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('load_wsvo Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {
			die('cd_ws_holder->check_local_vovp - no such WS_HOLDER');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		if( $set[0]['c'] == 0)
			return false;
		else
			return true;
		
	}
	private function update_local_wsvo() {
		global $mdb;
		$result = $mdb->query( "UPDATE t_workspace_holder SET vp_id='".$this->vp_id."' "
			."WHERE ws_id='".$this->ws_id."' AND vo_id='".$this->vo_id."' ");
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx))  {
					$msg=$trx->getMessage();
					die('Failed to issue query, update_local_wsvo 1, error message : ' . $msg);
				}
			}
			die('Failed to issue query, update_local_wsvo 2, error message : ' );//. $msg);
		}
		return TRUE;
	}
	private function create_local_wsvo() {
		global $mdb;
		$result = $mdb->query( "INSERT INTO t_workspace_holder (ws_id, vo_id, vp_id) "
			."VALUES ('".$this->ws_id."', '".$this->vo_id."', '".$this->vp_id."')");
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx))  {
					$msg=$trx->getMessage();
					die('Failed to issue query, create_local_wsvo 1, error message : ' . $msg);
				}
			}
			die('Failed to issue query, create_local_wsvo 1, error message : ' );//. $msg);
		}
		return TRUE;
	}
	
	/// STATIC FUNCTIONS
	static public function save_by_ws($ws_id, $vo_vp_ls) {
		global $mdb;
		foreach($vo_vp_ls as $couple) {
			if (exist_vo_in_ws($ws_id, $couple['vo_id'])) {// UPDATE
				$query = "UPDATE t_workspace_holder SET vp_id='".$couple['vp_id']."' "
					."WHERE ws_id='".$ws_id."' AND vo_id='".$couple['vo_id']."' ";
			} else { // CREATE
				$query = "INSERT INTO t_workspace_holder (ws_id, vo_id, vp_id) "
					."VALUES ('".$ws_id."', '".$couple['vo_id']."', '".$couple['vp_id']."') ";
			}
			$result = $mdb->query($query);
			
			if(PEAR::isError($result)) {
				if($mdb->inTransaction()) {
					$trx = $mdb->rollback();
					if(PEAR::isError($trx))  {
						$msg=$trx->getMessage();
						die('Failed to issue query, error message : ' . $msg);
					}
				}
//				$msg = $result-  getMessage();
				die('Failed to issue query, error message : ' );//. $msg);
			}
		}
		return TRUE;
	}

	static function get_visible_vovp_ls($current_ws_id) {
		$result_list = array();
		$current_ws = new cd_ws();
		$current_ws->load($current_ws_id);
		// $i=0;
		while($current_ws->parent_ws_id != 0) {
			//echo ($i.' '.$current_ws->parent_ws_id);
			$tmp_ls = self::get_local_vovp_ls($current_ws_id);
			$result_list = array_merge($result_list, $tmp_ls);
			$current_ws = $current_ws->get_parent_ws();
			$current_ws_id = $current_ws->ws_id;
			// $i++;
		}
		
		// master_ws
		$master_ws = $current_ws->get_master_ws();
		$tmp_ls = self::get_local_vovp_ls($master_ws->ws_id);
		$result_list = array_merge($result_list, $tmp_ls);
		
		return $result_list;
	}	
	static function create_new_vovp($vo_name, $ws_id) { 
		$vo = cd_versioned_object::create($vo_name);
		$ws_holder = new cd_ws_holder($ws_id, $vo->vo_id, $vo->selected_vp_id);
		$ws_holder->create();
		return $ws_holder;
	}
	static function load_visibility_vector_ls (&$wsvovp_ls, $selected_ws) {
		foreach($wsvovp_ls as $wsvovp) {
			self::load_visibility_vector($wsvovp, $selected_ws);
		}
	}	
	/// STATIC PRIVATE FUNCTIONS
	static private function exist_vo_in_ws($ws_id,$vo_id) {
		global $mdb;
		$query="SELECT * FROM t_workspace_holder WHERE ws_id='".$ws_id."' AND vo_id='".$vo_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ');// . $resultset- getMessage());
		}
		if($resultset->numRows() != 1) 
			return FALSE;
		return TRUE;
	}
	static private function get_local_vovp_ls($ws_id) {
		global $mdb;
		$query="SELECT * FROM t_workspace_holder WHERE ws_id='".$ws_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		$list = array();
		if($resultset->numRows() >= 1) {
			$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
			//echo"###"; var_dump($set);
			foreach($set as $value) {
				$ws_vo_vp = new cd_ws_holder( $value['ws_id'], $value['vo_id'], $value['vp_id']);
				$ws_vo_vp->load_vovp();
				$list[] = $ws_vo_vp;
			}
		}
		return $list;
	}
	static private function load_visibility_vector ($wsvovp, $selected_ws) {
		global $mdb;
		$query="SELECT f_visibility_vector(".$wsvovp->vo_id.", ".$selected_ws.")AS vector ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('load_visibility_vector Failed to issue query, error message : ' . $resultset->getMessage());
		}
		if($resultset->numRows() != 1) {echo $query;
			die('cd_ws_holder->load_visibility_vector('.$ws_id.' '.$vo_id.') - no such WS_HOLDER');
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$wsvovp->vis_vector = (int) $set[0]['vector'];
	}	

}

?>