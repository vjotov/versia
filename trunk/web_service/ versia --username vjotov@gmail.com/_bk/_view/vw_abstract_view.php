<?

abstract class vw_abstract_view { 
	public function __call($name, $arguments) {
		echo "Calling object method ".get_class($this).".".$name."(". implode(', ', $arguments). ") is temporary unavailable\n";
	}
	
	abstract 	public function show(mixed $parameter_in);
} 

?>