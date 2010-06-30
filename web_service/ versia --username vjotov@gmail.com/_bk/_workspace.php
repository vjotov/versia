<?php
class workspace {
	public function __call($name, $arguments) {
		echo "<p>Calling object method '$name' is temporary unavailable</p>\n";
		var_dump($arguments);
		debug_print_backtrace();
	}
	function context_process(){ 
		$action = $_GET["action"];
		switch ($action) {
			case "create_child_ws" :
				$this->create_child_ws();
				break;
			case "create_save" :
				$this->create_save();
				break;
			case "open_ws" :
				$this->open_ws();
				break;
			case "create_local_vo" :
				$this->create_local_vo();
				break;
			default: 
				break;
		}
	}
	function create_child_ws() {
		$parent_ws_id = $_GET["parent_ws_id"];		
		?>
		<form action="?context=workspace&action=create_save" method="post">
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
					<input type="button" value="Cancel" onclick="javascript:history.back(1);">
					<input type="submit" value="Create" />
				</td>
			</tr>
		</table></form><?php
	}
	function create_save() {
		global $mdb;
		$parent_ws_id = $_POST["parent_ws_id"];
		$new_name = $_POST["new_name"]; 
		
		$trx = $mdb->beginTransaction();
		if(PEAR::isError($trx)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
			
		$parent_ws = new workspace_db();
		$parent_ws->load($parent_ws_id);
		
		$new_workspace = new workspace_db($new_name, $parent_ws_id, $parent_ws->domain_id, $ws_id=NULL);
		$new_workspace->save();
		
		$trx = $mdb->commit();
		if(PEAR::isError($trx)) {
			debug_print_backtrace();
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		
		$domain = new domain_db();
		$domain->load($parent_ws->domain_id);
		$target_release_id = $domain->target_r_id;
		
		?>
		Done
		<a href="?context=release&action=open_release_domain&target_release_id=<?php echo $target_release_id; ?>">Back to releases</a>
		<?php
	}
	function open_ws() {
		$ws_id = $_GET["ws_id"];
		$ws_env_data = $this->load_ws_environment($ws_id);
		echo("<i>"
				."<b>".$ws_env_data['product']->name."</b>/"
				."<u>".$ws_env_data['release']->name."</u>/"
				.$ws_env_data['ws']->name
				."</i>");
		$this->list_vos($ws_env_data['ws']);
		?>
		<form action="?context=workspace&action=create_local_vo&ws_id=<?php echo $ws_id; ?>" method="POST">
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
	function load_ws_environment($ws_id) {
		$ws = new workspace_db();
		$ws->load($ws_id);
		$domain = new domain_db();
		$domain->load($ws->domain_id);
		$t_release = new release_db();
		$t_release->load($domain->target_r_id);
		$product = new product_db();
		$product->load($t_release->pr_id);
		
		return array(
			'ws' => $ws,
			'domain' => $domain,
			'release' => $t_release,
			'product' => $product);
	}
	function list_vos($ws) {
		$ws_ls_manager = new ws_vo_vp_db_list();
		$list = $ws_ls_manager->get_visible_vo_vp_ls($ws);
		
		var_dump($list);
	}
	function create_local_vo() {
		$ws_id = $_GET['ws_id'];
		$name = $_POST['vo_name'];
		$ws_vo_vp_db = new ws_vo_vp_db();
		$ws_vo_vp_db->create_versioned_object($ws_id, $name);
	}
}
?>