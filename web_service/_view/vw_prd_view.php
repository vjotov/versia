<?php

class vw_prd_view { //extends vw_abstract_view {
	public function show($parameter_in) {
		$product = $parameter_in['product'];
		$releases = $parameter_in['releases'];
		?>
		<table border="1">
			<tr>
				<th><?php echo $product->pr_id; ?></th>
				<th colspan="2">Product: <i><?php echo $product->name; ?></i></th>
			</tr>
			<tr><td>#</td><td colspan="2">Release name</td></tr>
			<?php
		foreach($releases as $k => $release) {
			?>
			<tr>
				<td><?php echo $k; ?></td>
				<td><a href="?request=rls_open_domain&release_id=<?php echo $release->r_id;?>"><?php echo $release->name; ?><i>(Open Release Domain)</i></a></td>
				<td><a href="?request=rls_create&source_release_id=<?php echo $release->r_id;?>">Create from this new Release (for review)</a></td>
			</tr>
			<?php
		}
	?></table><?php
	}
}