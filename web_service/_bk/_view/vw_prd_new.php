<?php

class vw_prd_new { //extends vw_abstract_view {
	public function show($parameter_in) {
		?>
		<form action="?request=prd_save" method="post">
		<input type="hidden" name="pr_id" value="0" />
		<table border="1">
			<tr>
				<th>#</th>
				<th>Product name</th>
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