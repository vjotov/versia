<?php

define('VPATH_BASE', dirname(__FILE__) );
define( 'DS', DIRECTORY_SEPARATOR );

$mdb = null;

require_once (VPATH_BASE .DS.'loader.php');
require_once (VPATH_BASE .DS.'global_functions.php');

my_mysql_connect();
//echo"<pre>";

function registerFncs() {
	$functions = j_product::getFunctions();
	$tmp_arr = j_release::registerFncs();
	$functions = array_merge($functions, $tmp_arr);
	$tmp_arr = j_workspace::registerFncs();
	$functions = array_merge($functions, $tmp_arr);
	$tmp_arr = j_vo::registerFncs();
	$functions = array_merge($functions, $tmp_arr);
	$tmp_arr = j_workitem::registerFncs();
	$functions = array_merge($functions, $tmp_arr);
	return $functions;
}
$json_functions = registerFncs();
$URL_decoded = urldecode($_GET['json']);
$decoded = json_decode($URL_decoded, true); 

// JSON _RPC
$method = $decoded['method'];
$params = $decoded['params'];
$session_id = $decoded['id'];

$found = 0; 
$responce = array(); 
foreach ($json_functions as $func_definition) {
	if($func_definition['method'] == $method) {
		$found = 1;
		$func_params = $func_definition['params'];
		
		$right = hasRights($session_id, $func_definition['permition']);
    if($right['error']['err_code'] != 0) {
    	$found = -10; 
    	$responce['result'] = array();
    	$responce['error'] = array('code' => -10, 'message' => 'Access denied - unautorised request');
    	$responce['id'] = $session_id;
    	break;
    }
		
		$f_param = array();
		foreach($params as $param_name => $param_value){
			if(array_key_exists($param_name, $func_params)) {
				$param_type = $func_params [$param_name];
				switch($param_type) {
				case 'number' :
					if(! is_int($param_value)) {
						$found = -20;
						$responce['result'] = array();
			    	$responce['error'] = array('code' => -20, 
			    		'message' => 'Incorrect parameter - int/'.var_dump($params) );
			    	$responce['id'] = $session_id;
					}
					break;
				case 'string' :
					if(! is_string($param_value)) {
						$found = -21;
						$responce['result'] = array();
			    	$responce['error'] = array('code' => -21, 
			    		'message' => 'Incorrect parameter - string/'.var_dump($params));
			    	$responce['id'] = $session_id;
			    }
					break;
				default:
					$found = -22;
					$responce['result'] = array();
		    	$responce['error'] = array('code' => -22, 
		    		'message' => 'Incorrect parameter - default/'.var_dump($params));
		    	$responce['id'] = $session_id;
					break;
				}
				if ($found < 1) break;
				
				$f_param[$param_name] = $param_value;
			} //else { // if params
				//$found = 0; 
				//break;
			//} // else params
		} // foreach
		
		if ($found < 1) break;
				
		$responce = $method($f_param);
		$responce['id'] = $session_id; 
	} // if method
} // foreach

if($found == 0) {
	$responce['result'] = array();
  $responce['error'] = array('code' => -30, 'message' => 'Procedure not found - '.$method);
  $responce['id'] = $session_id;
}

$encoded = json_encode($responce);
echo $encoded; //echo var_dump($_SERVER['REQUEST_METHOD']).var_dump($_POST);
//phpinfo();

?>