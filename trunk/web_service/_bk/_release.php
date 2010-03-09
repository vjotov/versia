<?php
class release {
	public function __call($name, $arguments) {
		echo "Calling object method '$name' ". implode(', ', $arguments). " is temporary unavailable\n";
	}
	function context_process(){ 
		$action = $_GET["action"];
		switch ($action) {
			case "create_new" :
				$this->create_new();
				break;
			case "create_save" :
				$this->create_save();
				break;
			case "open_release_domain" :
				$this->open_release_domain();
				break;
			default: 
				break;
		}
	}
	function create_new() {
		$source_release_id = $_GET["source_release_id"];		
		?>
		<form action="?context=release&action=create_save" method="post">
		<input type="hidden" name="source_release_id" value="<?php echo $source_release_id; ?>" />
		<table border="1">
			<tr>
				<th>#</th>
				<th>New Release name</th>
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
		$source_release_id = $_POST["source_release_id"];
		$new_name = $_POST["new_name"]; 
		
		$trx = $mdb->beginTransaction();
		if(PEAR::isError($trx)) {
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
			
		$source_release = new release_db();
		$source_release->load($source_release_id);
		
		$new_release = new release_db(0, $source_release->pr_id, $new_name);
		$new_release->save();
		
		$master_ws = new workspace_db(0, "Master Workspace");
		$master_ws->save();
		
		$domain = new domain_db(0, $source_release->pr_id, $new_release->r_id, $master_ws->ws_id);
		$domain->save();
		
		$master_ws->domain_id = $domain->domain_id;
		$master_ws->save();		
		
		$trx = $mdb->commit();
		if(PEAR::isError($trx)) {
			die('Failed to issue query, error message : ' . $trx->getMessage()); 
		}
		?>
		Done
		<a href="?context=product&action=view&pr_id=<?php echo $source_release->pr_id; ?>">Back to releases</a>
		<?php
	}
	function open_release_domain() {
		$target_release_id = $_GET["target_release_id"];	
		global $mdb;
		
		$domain = new domain_db();
		$domain->load_by_target($target_release_id); 
		$ws_ls = new workspace_db_list();
		$ws_h_ls = $ws_ls->get_domain_ws_hirarchy($domain);
		/*
			$ws_h_ls <=> []=>array(4) {["ws_id"],["parent_ws_id"], ["name"], ["level"]}
		*/
		?>
		<table border="1">
		<tr><th colspan="3">Domain</th></tr>
		<?php
		foreach($ws_h_ls as $ws_node){
			if($ws_node['level'] == 0) 
				continue;
		?>
		<tr>
			<td align="left"><img src="pics/_.gif" width="<?php echo $ws_node['level']*10+5; ?>" height="1"/><?php echo $ws_node['level']."-".$ws_node['name']; ?></td>
			<td><a href="?context=workspace&action=create_child_ws&parent_ws_id=<?php echo $ws_node['ws_id']; ?>">Create child workspace</a></td>
			<td><a href="?context=workspace&action=open_ws&ws_id=<?php echo $ws_node['ws_id']; ?>">Open workspace</a></td>
		</tr>
		<?php
		}
		?>
	</table>
		<?php
		
	}
}
?>