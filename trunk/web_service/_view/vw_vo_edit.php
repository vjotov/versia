<?php

class vw_vo_edit {
	function show($parameter_in) {
		$vo = $parameter_in['vo'];
		$vp = $parameter_in['vp'];
		?>
		<form action="?request=vo_save" method="POST">
			<input type="hidden" name="crnt_ws" value="<?php echo $parameter_in['crnt_ws'];?>" />
			<input type="hidden" name="vovp_ws" value="<?php echo $parameter_in['vovp_ws'];?>" />
			<input type="hidden" name="vo_id" value="<?php echo $vo->vo_id;?>" />
			<input type="hidden" name="vp_id" value="<?php echo $vp->vp_id;?>" />
			<table>
				<tr><td align="right"> Name:</td><td><?php echo $vo->name; ?></td></tr>
				<tr><td align="right" valign="top"> Content:</td>
					<td><textarea name="content" rows="5" cols="30"><?php echo $vp->content; ?></textarea></td>
				</tr>
				<tr><td align="right">Save State Mark:</td><td><input type="checkbox" name="save_state_mark" /></td></tr>
				<tr><td align="right"><input type="reset" /></td><td><input type="submit" value="Save" /></td></tr>
			</table>
		</form>
		<a href="?request=ws_open&ws_id=<?php echo $parameter_in['crnt_ws']; ?>" >Go to workspace</a>
		<?php
	}
}