<?php

class cc_ws_open {
	function perform() {
		$ws_id = $_GET["ws_id"];
		
		$ws_vovp_ls = cd_ws_holder::get_visible_vovp_ls($ws_id);
		cd_ws_holder::load_visibility_vector_ls($ws_vovp_ls, $ws_id);

		$parameter_out = array('ws_vovp_ls' => $ws_vovp_ls, 'ws_id' => $ws_id);
		return $parameter_out;
	}
}
?>