<?

class cd_release_holder{
	var $r_id;
	var $vo_id;
	var $vp_id;
	
	public function cd_release_holder() {}
	
	static public function load_by_release($release_id) {
		global $mdb;
		$query="SELECT vo_id, vp_id FROM t_release_holder WHERE r_id='".$release_id."' ";
		$resultset = $mdb->query($query); 
		if(PEAR::isError($resultset)) {
			die("Failed to issue query, error message :" . $resultset->getMessage());
		}
		
		$result = $resultset->fetchAll(MDB2_FETCHMODE_ASSOC);
		return $result;
	}
}

?>