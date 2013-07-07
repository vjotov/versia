<?php

function get_dispatch_table() {
	$dispatch_table = array(
		//array(CONTEXT,TASK,CONTROLLER,VIEW),
		'default'					=> array('controller'=>'cc_prd_list',			'view'=>'vw_prd_list'),
		
		//PRODUCT
		'prd_view' 				=> array('controller'=>'cc_prd_view',				'view'=>'vw_prd_view'),
		'prd_releases' 		=> array('controller'=>'cc_prd_releases',		'view'=>'vw_prd_releases'),
		'prd_edit' 				=> array('controller'=>'cc_prd_edit',				'view'=>'vw_prd_edit'),
		'prd_new'					=> array('controller'=>NULL,								'view'=>'vw_prd_new'),
		'prd_save'				=> array('controller'=>'cc_prd_save',				'view'=>NULL),
		'prd_delete' 			=> array('controller'=>NULL,								'view'=>NULL),
		'prd_list' 				=> array('controller'=>'cc_prd_list',				'view'=>'vw_prd_list'),
		
		//RELEASE
		'rls_create'			=> array('controller'=>'cc_rls_create',			'view'=>'vw_rls_create'),
		'rls_save'				=> array('controller'=>'cc_rls_save',				'view'=>NULL),
		'rls_edit'				=> array('controller'=>'cc_rls_edit',				'view'=>'vw_rls_edit'),
		'rls_open_domain' => array('controller'=>'cc_rls_open_domain','view'=>'vw_rls_open_domain'),
		//'rls_list'				=> array('controller'=>'cc_rls_list',			'view'=>'vw_rls_list'),
				
		//WORKSPACE
		'ws_create_child'	=> array('controller'=>'cc_ws_create_child','view'=>'vw_ws_create_child'),
		'ws_save'	 				=> array('controller'=>'cc_ws_save',				'view'=>'vw_rls_open_domain'),
		'ws_open'					=> array('controller'=>'cc_ws_open',				'view'=>'vw_ws_open'),
		'ws_edit'					=> array('controller'=>'cc_ws_edit',				'view'=>'vw_ws_edit'),
		
		//VO&VP
		'vo_create'				=> array('controller'=>'cc_vo_create',			'view'=>'vw_vo_create'),
		'vo_edit'					=> array('controller'=>'cc_vo_edit',				'view'=>'vw_vo_edit'),
		'vo_save'					=> array('controller'=>'cc_vo_save',				'view'=>'vw_vo_save'),
		'vo_delete'				=> array('controller'=>'cc_vo_delete',			'view'=>'vw_vo_delete'),
		'vo_publish'			=> array('controller'=>'cc_vo_publish',			'view'=>'vw_vo_publish'),
		'vo_putback'			=> array('controller'=>'cc_vo_putback',			'view'=>'vw_vo_putback'),
		'vo_history'			=> array('controller'=>'cc_vo_history',			'view'=>'vw_vo_history')
	);
	return $dispatch_table;
}

?>