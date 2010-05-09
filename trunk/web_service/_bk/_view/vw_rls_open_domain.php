<?php

class vw_rls_open_domain {
	function show($parameter_in){ 
		$ws_h_ls = $parameter_in['ws_h_ls'];
		?>
		<table border="1">
		<tr><th colspan="3">Domain</th></tr>
		<?php 
		foreach($ws_h_ls as $ws_node){
			if($ws_node['level'] == 0) 
				continue;
		?>
		<tr>
			<td align="left"><img src="pics/_.gif" width="<?php echo $ws_node['level']*10+5; ?>" height="1"/><?php echo $ws_node['level']."-".$ws_node['name']; ?></td>
			<td><a href="?request=ws_create_child&parent_ws_id=<?php echo $ws_node['ws_id']; ?>">Create child workspace</a></td>
			<td><a href="?request=ws_open&ws_id=<?php echo $ws_node['ws_id']; ?>">Open workspace</a></td>
		</tr>
		<?php
		}
	}
}

?>