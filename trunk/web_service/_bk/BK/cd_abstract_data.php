<?

abstract class cd_abstract_data { 
	public function __call($name, $arguments) {
		echo "Calling object method ".get_class($this).".".$name."(". implode(', ', $arguments). ") is temporary unavailable\n";
	}
	
	//abstract 	public function perform();
} 

?>