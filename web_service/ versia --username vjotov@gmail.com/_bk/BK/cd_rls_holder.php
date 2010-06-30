<?

class cd_rls_holder {
	var $r_id;
	var $vo_id;
	var $vp_id;
	
	function cd_rls_holder() {}
	
	static function load_by_release($release_id) {
		global $mdb;
		$query="SELECT vo_id, vp_id FROM t_release_holder WHERE r_id='".$release_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die("Failed to issue query, error message :" . $resultset->getMessage());
		}
		
		$set = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		$result = array();
		foreach($set as $row => $value) {
			$result[] = $value;
		}
	}
}

?>