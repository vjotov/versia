<?php

class cc_vo_save{
	function perform() {
		var_dump($_POST);
						
		$crnt_ws = $_POST['crnt_ws'];
		$vovp_ws = $_POST['vovp_ws'];
		$vo_id = $_POST['vo_id'];
		$vp_id = $_POST['vp_id'];
		$content = $_POST['content'];
		$save_state_mark = $_POST['save_state_mark'];
		
		if($save_state_mark == "on" && $vovp_ws == $crnt_ws) {  echo "Local STATE-MARK \n";
			$last_vp_id = cd_versioned_primitive::get_last_vp_id($vo_id);
			$new_vp = new cd_versioned_primitive($vo_id, $last_vp_id + 1, $content);
			$new_vp->save();
			cd_versioned_primitive_arc::create_arc($vo_id, $vp_id, $new_vp->vp_id);
			
			$ws_holder = new cd_ws_holder();
			$ws_holder->load_wsvo($vovp_ws, $vo_id);
			$ws_holder->vp_id = $new_vp->vp_id; 
			$ws_holder->save();			
		} 
		else if($vovp_ws != $crnt_ws) {  echo "From Parent STATE-MARK";
			$last_vp_id = cd_versioned_primitive::get_last_vp_id($vo_id);
			var_dump($last_vp_id);
			//echo $last_vp_id+1;
			$new_vp = new cd_versioned_primitive($vo_id, $last_vp_id + 1, $content); var_dump($new_vp);
			$new_vp->save();
			cd_versioned_primitive_arc::create_arc($vo_id, $vp_id, $new_vp->vp_id);
			
			$local_ws_holder = new cd_ws_holder($crnt_ws, $vo_id, $new_vp->vp_id);
			$local_ws_holder->save();	var_dump($local_ws_holder);		
		} 
		else if($vovp_ws == $crnt_ws) { // echo"Local PLAIN UPDATE"; 
				$vp = new cd_versioned_primitive();
				$vp->load($vo_id, $vp_id);
				$vp->content = $content;
				$vp->save();
			} else {  
				echo "cc_vo_save->perform impossible case!!! very strange...";
			}
		return array('ws_id' => $crnt_ws);
	}
}
?>