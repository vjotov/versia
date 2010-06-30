<?php

class vw_ws_open {
	function show($parameter_in) { 
		$ws_vovp_ls = $parameter_in['ws_vovp_ls'];
		?><form action="?request=multible_vo_action" method="POST">
			<input type="hidden" name="action" value="" />
			<table border="1"> 
			<th>
				<td>Object Name</td>
				<td>version</td>
				<td>Visibility vector</td>
				<td colspan="4">Actions</td>
			</th>
		<?php
		foreach($ws_vovp_ls as $i => $ws_vovp) {
			
			$ws_vovp_ch = $parameter_in['ws_id']."_".$ws_vovp->vo_id."_".$ws_vovp->vp_id;
			$ws_vovp_GET = "crnt_ws=".$parameter_in['ws_id']
										."&vovp_ws=".$ws_vovp->ws_id
										."&vo_id=".$ws_vovp->vo_id
										."&vp_id=".$ws_vovp->vp_id;
			?>
			<tr>
				<td><input type="checkbox" name="vovp<?php echo $ws_vovp_ch; ?>" /></td>
				<td><?php echo $ws_vovp->vo->name; ?></td>
				<td><?php echo $ws_vovp->vp->vp_id; ?></td>
				<td><?php echo $this->print_visibility_vector($ws_vovp->vis_vector); ?></td>
				<td><a href="?request=vo_edit&<?php echo $ws_vovp_GET; ?>">Edit</a></td>
				<td><a href="?request=vo_delete&<?php echo $ws_vovp_GET; ?>">Delete</a></td>
				<td><a href="?request=vo_publish&<?php echo $ws_vovp_GET; ?>">Publish</a></td>
				<td><a href="?request=vo_putback&<?php echo $ws_vovp_GET; ?>">Put Back</a></td>
				<td><a href="?request=vo_history&<?php echo $ws_vovp_GET; ?>">View History</a></td>
			</tr>
			<?php
		}
		?></table></form>
		<form action="?request=vo_create" method="POST">
			<input type="hidden" name="ws_id" value="<?php echo $parameter_in['ws_id']; ?>" />
			<table border="1">
				<tr>
					<th colspan="2">Create new versioned object</th>
				</tr>
				<tr>
					<td> Name: </td>
					<td><input type="text" name="vo_name" /></td>
				</tr>
				<tr>
					<td><input type="reset" value="Reset" /></td>
					<td><input type="submit" value="Create" /></td>
				</tr>
			</table>
		</form>
		<?php
	}
	function print_visibility_vector($vv) {
		$result="";
		$vv & 1  ? $result.="R" : $result .="_";
		$vv & 2  ? $result.="P" : $result .="_";
		$vv & 4  ? $result.="C" : $result .="_";
		$vv & 8  ? $result.="L" : $result .="_";
		$vv & 16 ? $result.="O" : $result .="_";
		
		return $result;
	}
}

?>