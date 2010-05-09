<?php

class cc_rls_create {
	
	public function perform() {
		$source_releace = new cd_release();
		echo $source_release_id = $_GET["source_release_id"];
		$source_releace->load($source_release_id);
		
		//$result['pr_id']=$source_releace->pr_id;
		$result['source_release_id']=$source_releace->r_id;
		
		return $result;
	}
	
}

?>