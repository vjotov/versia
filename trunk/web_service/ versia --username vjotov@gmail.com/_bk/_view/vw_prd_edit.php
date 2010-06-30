<?php

class vw_prd_edit { //extends vw_abstract_view {
	public function show($parameter_in) {
		$product = $parameter_in['product'];
		?>
		<form action="?request=prd_save" method="post">
		<input type="hidden" name="pr_id" value="<?php echo $product->pr_id;?>" />
		<table border="1">
			<tr>
				<th>#</th>
				<th>Product name</th>
			</tr><tr>
				<td><?php echo $product->pr_id;?></td>
				<td><input type="text" name="name" value="<?php echo $product->name;?>" /></td>
			</tr><tr>
				<td colspan="2">
					<input type="button" value="Cancel" onclick="javascript:history.back(1);">
					<input type="submit" value="Update" />
				</td>
			</tr>
		</table></form>
		<?php
	}
}