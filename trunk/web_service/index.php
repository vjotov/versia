<img src='pics/logo.jpg' />
<pre>
<?php
define('VPATH_BASE', dirname(__FILE__) );
define( 'DS', DIRECTORY_SEPARATOR );
define('HOME_URL', "http://192.168.56.2/versia/");

@set_magic_quotes_runtime( 0 );
@ini_set('zend.ze1_compatibility_mode', '0');

$mdb = null;

require_once (VPATH_BASE .DS.'loader.php');
my_mysql_connect();
$dispatch_table = get_dispatch_table();

$request = $_GET["request"];   
$current_conf = $dispatch_table[$request];

if ($current_conf == NULL)
	$current_conf = $dispatch_table['default'];
//var_dump($current_conf);

if ($current_conf['controller'] != NULL){
	$controller = new $current_conf['controller'];
	$c_parameters = $controller->perform();
} else 
	$c_parameters = array();
	
if ($current_conf['view'] != NULL) {
	$view = new $current_conf['view'];
	$view->show($c_parameters);
}
?>
<p><a href='soap_controller.php'>SOAP</a></p>
<?php
require_once (VPATH_BASE .DS.'copyright.php');
?>
</pre>