<?php 

class cc_ws_create_child {
	function perform() {
		$result['parent_ws_id'] = $_GET['parent_ws_id'];
		return $result;
	}
}

?>