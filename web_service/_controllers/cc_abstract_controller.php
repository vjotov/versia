<?

class cc_abstract_controller { 
	public function __call($name, $arguments) {
		echo "Calling object method ".get_class($this).".".$name."(". implode(', ', $arguments). ") is temporary unavailable\n";
	}
	
	public function perform() {};
} 

?>