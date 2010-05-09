<?php

class vw_prd_list { //extends vw_abstract_view {
	public function show($parameter_in) {
		$list = $parameter_in['prd_list'];
		?><table border="1"><tr><th>#</th><th>Product name</th><th>&nbsp;</th><th>&nbsp;</th></tr>
		<?php 
		foreach($list as $row => $product_db) {
			?>
			<tr>
			<td><?php echo $row+1; ?></td>
			<td><a href="?request=prd_view&pr_id=<?php echo $product_db->pr_id; ?>"><?php echo $product_db->name; ?></a></td>
			<td><a href="?request=prd_edit&pr_id=<?php echo $product_db->pr_id; ?>">Edit</a></td>
			<td><a href="?request=prd_delete&pr_id=<?php echo $product_db->pr_id; ?>">DELETE</a></td>
			</tr>
			<?php
		}
		?></table> <hr><a href="?request=prd_new">Create new product</a><?php
	}
}