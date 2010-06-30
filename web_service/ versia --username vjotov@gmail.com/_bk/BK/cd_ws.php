<?php

class cd_ws {
	var $ws_id 				= NULL;
	var $parent_ws_id = NULL;
	var $name 				= NULL;
	var $domain_id 		= 0;
		
	function cd_ws( $name = NULL, $parent_ws_id = NULL, $domain_id = 0, $ws_id=0) {
		$this->ws_id 	 			= $ws_id;
		$this->parent_ws_id = $parent_ws_id;
		$this->name 				= $name;
		$this->domain_id 	 	= $domain_id;
	}
	function load($id) {
		global $mdb;

		$query="SELECT * FROM t_workspace WHERE ws_id='".$id."'";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		if($resultset->numRows() != 1) {
			debug_print_backtrace(); var_dump($query);
			die('cd_ws->load('.$id.') - no such workspace');
			return;
		}
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$this->ws_id 	 			= $set[0]['ws_id'];
		$this->parent_ws_id = $set[0]['parent_ws_id'];
		$this->name 				= $set[0]['name'];
	  $this->domain_id 		= $set[0]['domain_id'];
	}
	function save() {
		global $mdb;
		if ($this->ws_id==0) {
			$result = $mdb->query("INSERT INTO t_workspace (parent_ws_id, name, domain_id)"
							." VALUES ( '".$this->parent_ws_id."', '".$this->name."', '".$this->domain_id."')");
			$this->ws_id = $mdb->lastInsertID();
		} else {
			$result = $mdb->query("UPDATE t_workspace SET parent_ws_id='".$this->parent_ws_id."', name='".$this->name."', domain_id='".$this->domain_id."'"
							." WHERE ws_id='".$this->ws_id."'");
		}
		//$sth->free();
		if(PEAR::isError($result)) {
			if($mdb->inTransaction()) {
				$trx = $mdb->rollback();
				if(PEAR::isError($trx)) 
					die('Failed to issue query, error message : ' . $trx->getMessage());
			}
			die('Failed to issue query, error message : ' . $result->getMessage()); 
		}
		return TRUE;
	}
	function delete() {
	}
	private function get_domain() {
		$domain = new domain_db();
		$domain->load($this->domain_id);
		return $domain;
	}
	function get_parent_ws() {
		$parent = new cd_ws();
		$parent->load($this->parent_ws_id);
		return $parent;
	}
	function get_master_ws() {
		$domain = $this->get_domain();
		if ($domain->ws_id == $this_ws_id)
			return $this;
		else {
			$master_ws = new cd_ws();
			$master_ws->load($domain->ws_id);
			return $master_ws;
		}
	}
	
	static function load_ws_hirarchy_in_domain($domain) {
		global $mdb;
	  $query = 	"SELECT 0 AS parent_ws_id, ws_id AS child_ws_id, name AS child_name"
							." FROM t_workspace WHERE parent_ws_id=0 AND domain_id=".$domain->domain_id
							." UNION"
							." SELECT P.ws_id, C.ws_id, C.name "
							." FROM t_workspace P  JOIN t_workspace C ON P.ws_id = C.parent_ws_id "
							." WHERE P.domain_id=".$domain->domain_id
							." ORDER BY parent_ws_id ASC , child_name DESC ";
							
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		
		$ws_h_ls = array();
		$ws_h_ls[] = array('ws_id' => '0', 'level'=> '0');
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		foreach($set as $value) {
			$new_node = array(
				'ws_id' => $value['child_ws_id'],
				'parent_ws_id' => $value['parent_ws_id'],
				'name' => $value['child_name']);
			$ws_h_ls = self::insert_after($ws_h_ls, $value['parent_ws_id'], $new_node);
		}
		return $ws_h_ls;
	}
	static function load_vo_vectors($domain) {
		$query = "SELECT 0 AS parent_ws_id, ws_id AS local_ws_id, H.vo_id AS vo"
						."FROM t_workspace LEFT JOIN t_workspace_holder H USING(ws_id)"
						."WHERE parent_ws_id=0 AND domain_id= 1 #AND H.vo_id=30"
						."UNION "
						."SELECT P.ws_id, C.ws_id, H.vo_id"
						."FROM t_workspace P  JOIN t_workspace C ON P.ws_id = C.parent_ws_id "
						."     LEFT JOIN t_workspace_holder H ON  H.ws_id = C.ws_id"
						."WHERE P.domain_id= 1"
						."ORDER BY parent_ws_id ASC ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		
		$ws_h_ls = array();
		$ws_h_ls[] = array('ws_id' => '0', 'level'=> '0');
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		foreach($set as $value) {
			$new_node = array(
				'ws_id' => $value['local_ws_id'],
				'parent_ws_id' => $value['parent_ws_id'],
				'vo' => $value['vo']);
			$ws_h_ls = self::insert_after($ws_h_ls, $value['parent_ws_id'], $new_node);
		}
		return $ws_h_ls;
	}
	private static function insert_after($source_array, $parent_id, $data) {
		$result = array();
		foreach($source_array as $val) {
			$result[] = $val;
			if ($val['ws_id'] == $parent_id) {
				$parent_level = $val['level'];
				$data['level'] = $parent_level + 1;
				$result[] = $data;
			}
		}
		return $result;
	}
	
	
}

?>