<?php

class vw_ws_create_child {
	function show($parameter_in) {
		$parent_ws_id = $parameter_in['parent_ws_id'];
		?>
		<form action="?request=ws_save" method="post">
		<input type="hidden" name="parent_ws_id" value="<?php echo $parent_ws_id; ?>" />
		<table border="1">
			<tr>
				<th>#</th>
				<th>New Workspace name</th>
			</tr><tr>
				<td>&nbsp;</td>
				<td><input type="text" name="new_name" value="" /></td>
			</tr><tr>
				<td colspan="2">
					<input type="button" value="Back" onclick="javascript:history.back(1);">
					<input type="submit" value="Create" />
				</td>
			</tr>
		</table></form>
		<?php
	}
}

?>
