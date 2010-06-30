<pre>
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
var_dump($json_functions);

?>
</pre>