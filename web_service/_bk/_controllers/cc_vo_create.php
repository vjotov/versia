<?php

class cc_vo_create {
	function perform () {
		
		$ws_id = $_POST["ws_id"];
		$vo_name = $_POST["vo_name"];
		
		$vo = cd_ws_holder::create_new_vovp($vo_name, $ws_id);
		$parameter_out = array('ws_id' => $ws_id);

		return $parameter_out;
	}
}

?>