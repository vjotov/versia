<?php
class cd_executer {
	static public function execQuery($method, $query){
		global $mdb;
		$result = array();
		$SQLresult = $mdb->query($query);
		if(PEAR::isError($SQLresult)) {
			//$result['code'] = 1;
			$message = 'Failed to issue query, error message : ' . $SQLresult->getMessage();
			$message .= $method . $query;
			throw new Exception($message, 1);
		}
		$result = $SQLresult->fetchAll(MDB2_FETCHMODE_ASSOC);
		return $result;
	} 
	static public function beginTransaction(){
		global $mdb;
		$mdb->beginTransaction();
	}
	static public function commitTransaction() {
		global $mdb;
		$mdb->commit();
	}
	static public function rollbackTransaction() {
		global $mdb;
		$mdb->rollback();
	}
} 