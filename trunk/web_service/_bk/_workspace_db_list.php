<?php

class workspace_db_list {
	
	function get_child_ws ($id) {
		global $mdb;

		$query="SELECT * FROM t_workspace WHERE parent_ws_id='".$id."' ORDER BY name";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $resultset->getMessage());
		} 
		$list = array();
		if($resultset->numRows() >= 1) {
			$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
			foreach($set as $value) {
				$list[] = new workspace_db($value['ws_id'], $value['name'], $value['parent_ws_id'], $value['domain_id']);
			}
		}
		return $list;
	}
	function get_domain_ws_hirarchy($domain) {
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
			$ws_h_ls = $this->insert_after($ws_h_ls, $value['parent_ws_id'], $new_node);
		}
		return $ws_h_ls;
	}
	function insert_after($source_array, $parent_id, $data) {
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