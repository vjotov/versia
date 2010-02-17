<?php

class vw_rls_create { //extends vw_abstract_view {
	public function show($parameter_in) {
		?>
		<form action="?request=rls_save" method="post">
		<input type="hidden" name="source_release_id" value="<?php echo $parameter_in['source_release_id'] ?>" />
		<table border="1">
			<tr>
				<th>#</th>
				<th>New release name</th>
			</tr><tr>
				<td>&nbsp;</td>
				<td><input type="text" name="name" value="" /></td>
			</tr><tr>
				<td colspan="2">
					<input type="button" value="Cancel" onclick="javascript:history.back(1);">
					<input type="submit" value="Create" />
				</td>
			</tr>
		</table></form>
		<?php
	}
}