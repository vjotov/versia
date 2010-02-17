<?php

class cc_vo_edit {
	function perform() {
		$parameter_out = array();
		$vo_id = $_GET['vo_id'];
		$vp_id = $_GET['vp_id'];
		$parameter_out['vovp_ws']=$_GET['vovp_ws'];
		$parameter_out['crnt_ws'] = $_GET['crnt_ws'];
		
		$vp = new cd_versioned_primitive();
		$vp->load($vo_id, $vp_id);
		$vo = new cd_versioned_object();
		$vo->load($vo_id);
		
		
		
		$parameter_out['vo'] = $vo;
		$parameter_out['vp'] = $vp;
		
		return $parameter_out;
	}
}
?>