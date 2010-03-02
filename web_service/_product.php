<?php

class product{
	public function __call($name, $arguments) {
		echo "Calling object method '$name' ". implode(', ', $arguments). " is temporary unavailable\n";
	}
	function context_process(){ 
		$action = $_GET["action"];
		switch ($action) {
			case "view" :
				$this->product_view();
				break;
			case "releases" :
				$this->product_view();
				break;
			case "edit" :
				$this->product_edit();
				break;
			case "new" :
				$this->product_new();
				break;
			case "save" :
				$this->product_save();
				break;
			case "delete" :
				$this->product_delete();
				break;
			case "products_list" :
				$this->product_list();
				break;	
			default: 
				$this->product_list();
				break;
		}
	}
	function product_list(){
		$pr_ls = new product_db_list();
		$pr_ls->load_list(); 
		$list = $pr_ls->list;
		$this->product_list_html($list);
	}
	function product_list_html($list) {
		?><table border="1"><tr><th>#</th><th>Product name</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th></tr>
		<?php 
		foreach($list as $row => $product_db) {
			?>
			<tr>
			<td><?php echo $row+1; ?></td>
			<td><a href="?context=product&action=view&pr_id=<?php echo $product_db->pr_id; ?>"><?php echo $product_db->name; ?></a></td>
			<td><a href="?context=product&action=edit&pr_id=<?php echo $product_db->pr_id; ?>">Edit</a></td>
			<td><a href="?context=product&action=delete&pr_id=<?php echo $product_db->pr_id; ?>">DELETE</a></td>
			<td><a href="?context=product&action=releases&pr_id=<?php echo $product_db->pr_id; ?>">Releases</a></td>
			</tr>
			<?php
		}
		?></table> <hr><a href="?context=product&action=new">Create new product</a><?php
	} 

	function product_edit(){
		$pr_id = $_GET["pr_id"];
		$product = new product_db();
		$product->load($pr_id);	
		$this->product_edit_html($product);
	}	
	function product_edit_html($product){
		?>
		<form action="?context=product&action=save" method="post">
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
		</table></form><?php
	}
	function product_new() {
		?>
		<form action="?context=product&action=save" method="post">
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
		</table></form><?php
	}
	function product_save() {
		$product = new product_db();
		$product->pr_id = $_POST["pr_id"];
		$product->name = $_POST["name"];
		if($product->save())
			echo "The product was successfuly ".$type.". <a href='".HOME_URL."'>Home</a>";
		
	}
	function product_view() {
		$pr_id = $_GET["pr_id"];
		$product = new product_db();
		$product->load($pr_id);
		$html_data= array();
		$html_data['product'] = $product;

		$rel_ls = new release_db_list();
		$rel_ls->load_list($pr_id); 		
		$html_data['releases'] = $rel_ls->list;
		
		$this->product_view_html($html_data);
	}
	function product_view_html($html_data) {
		
		$product = $html_data['product'];
		$releases = $html_data['releases'];
		?><table border="1">
			<tr>
				<th><?php echo $product->pr_id; ?></th>
				<th colspan="3">Product: <i><?php echo $product->name; ?></i></th>
			</tr>
			<tr><td>#</td><td colspan="2">Release name</td><td>&nbsp;</td></tr>
			<?php
		foreach($releases as $k => $release) {
			?>
			<tr>
				<td><?php echo $k; ?></td>
				<td><?php echo $release->name; ?></td>
				<td><a href="?context=release&action=create_new&source_release_id=<?php echo $release->r_id;?>">Create from this new Release</a></td>
				<td><a href="?context=release&action=open_release_domain&target_release_id=<?php echo $release->r_id;?>">Open Release Domain</a></td>
			</tr>
			<?php
		}
		echo "</table><a href='".HOME_URL."'>Home</a>";
	}
}
?>