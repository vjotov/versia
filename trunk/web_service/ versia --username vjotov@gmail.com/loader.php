<?php

require_once(VPATH_BASE .DS.'my_mysql_connect.php');
require_once(VPATH_BASE .DS.'dispatch_table.php');

//require_once(VPATH_BASE .DS.'soap'.DS.'s_workspace.php');
/*
require_once(VPATH_BASE .DS.'controllers'.DS.'cc_prd_edit.php');
require_once(VPATH_BASE .DS.'controllers'.DS.'cc_prd_list.php');
require_once(VPATH_BASE .DS.'controllers'.DS.'cc_prd_save.php');
require_once(VPATH_BASE .DS.'controllers'.DS.'cc_prd_view.php');
require_once(VPATH_BASE .DS.'controllers'.DS.'cc_rls_create.php');
require_once(VPATH_BASE .DS.'controllers'.DS.'cc_rls_save.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_abstract_data.php'); echo":)";
//require_once(VPATH_BASE .DS.'data'.DS.'cd_domain.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_d.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_product.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_release.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_release_arc.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_release_holder.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_versioned_object.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_versioned_primitive.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_versioned_primitive_arc.php');
//require_once(VPATH_BASE .DS.'data'.DS.'cd_workspace.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_ws.php');
require_once(VPATH_BASE .DS.'data'.DS.'cd_workspace_holder.php');
require_once(VPATH_BASE .DS.'view'.DS.'vw_abstract_view.php');
require_once(VPATH_BASE .DS.'view'.DS.'vw_prd_edit.php');
require_once(VPATH_BASE .DS.'view'.DS.'vw_prd_list.php');
require_once(VPATH_BASE .DS.'view'.DS.'vw_prd_new.php');
require_once(VPATH_BASE .DS.'view'.DS.'vw_prd_view.php');
require_once(VPATH_BASE .DS.'view'.DS.'vw_rls_create.php');
//*/                                       
// this  the code used to load and store DataObjects Configuration.
//$options = &PEAR::getStaticProperty('DB_DataObject','options');


$config = parse_ini_file('dbconnect.ini',TRUE);
/*$options = $config['DB_DataObject'];
require_once 'DB/DataObject.php'; //*/



function __autoload($class_name) {
	/*echo "#########<br />";
		var_dump(debug_backtrace());
		if( $class_name == "cd_release_holder") {
			echo"#*#*#*#*#*$<br />";
			$t1 = ereg("^cc_", $class_name);
			echo $t1."<br />";
			$t2 = VPATH_BASE .DS."data".DS.$class_name . '.php';
			echo $t2."<br />";
			$t3 = file_exists($t2);
			echo $t3."<br />";
				var_dump(debug_backtrace());
		}//*/
		//Controllers...
		if ( ereg("^cc_", $class_name)) {// Returns true if "cc_" is found at the beginning of $string
			require_once (VPATH_BASE .DS."controllers".DS.$class_name . '.php'); 
			return;
		}
		//Data...
		if ( ereg("^cd_", $class_name)) {// Returns true if "cd_" is found at the beginning of $string
			require_once (VPATH_BASE .DS."data".DS.$class_name . '.php');
			return;
		}
		//View...
		if ( ereg("^vw_", $class_name)) {// Returns true if "vw_" is found at the beginning of $string
			require_once (VPATH_BASE .DS."view".DS.$class_name . '.php');
			return;
		}
		// SOAP
		if ( ereg("^s_", $class_name)) {// Returns true if "s_" is found at the beginning of $string
			require_once (VPATH_BASE .DS."soap".DS.$class_name . '.php');
			return;
		}
		// JSON
		if ( ereg("^j_", $class_name)) {// Returns true if "s_" is found at the beginning of $string
			require_once (VPATH_BASE .DS."json".DS.$class_name . '.php');
			return;
		}
    //else ///
    require_once (VPATH_BASE .DS.$class_name . '.php');
}//*/

?>