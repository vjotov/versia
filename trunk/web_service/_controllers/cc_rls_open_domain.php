<?php

class cc_rls_open_domain {
	function perform() {
		$release_id = $_GET['release_id'];
		
		$domain = new cd_d();
		$domain->load_by_release($release_id);
		$ws_h_ls = cd_ws::load_ws_hirarchy_in_domain($domain);
		
		$result['ws_h_ls'] = $ws_h_ls;
		return $result;
	}
}

?>