<?php
define('VPATH_BASE', dirname(__FILE__) );
define( 'DS', DIRECTORY_SEPARATOR );

require('lib/nusoap.php');
$mdb = null;

require_once (VPATH_BASE .DS.'loader.php');
require_once (VPATH_BASE .DS.'global_functions.php');
my_mysql_connect();

function registerComplexTypes($server) {
	s_product::registerComplexTypes($server);
	s_release::registerComplexTypes($server);
	s_workspace::registerComplexTypes($server);
	s_model::registerComplexTypes($server);
	s_vo::registerComplexTypes($server);
	s_workitem::registerComplexTypes($server);
}
function registerFncs($server) {
	s_product::registerFncs($server);
	s_release::registerFncs($server);
	s_workspace::registerFncs($server);
	s_model::registerFncs($server);
	s_vo::registerFncs($server);
	s_workitem::registerFncs($server);
}

//$debug = 1;

$server = new soap_server();
$server->configureWSDL('stockserver', 'urn:versia');
registerComplexTypes($server);
registerFncs($server);

$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA)
                      ? $HTTP_RAW_POST_DATA : '';

$server->service($HTTP_RAW_POST_DATA);

?>