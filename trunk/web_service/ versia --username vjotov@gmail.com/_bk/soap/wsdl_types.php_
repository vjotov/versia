<?php

function get_types() {
	$definition_list = array (
		array(
			'name' => 'Product', 'type' => 'struct', 
			'definition' => array(
				'productNumber' => array('name' => 'productNumber', 'type' => 'xsd:int'),
        'productName' => array('name' => 'productName', 'type' => 'xsd:string')
			)
		),
		array (
			'name' => 'ProductList', 'type' => 'array',
			'definition' => 'Product'
		),
		array(
			'name' => 'Revision', 'type' => 'struct', 
			'definition' => array(
				'productNumber' => array('name' => 'productNumber', 'type' => 'xsd:int'),
				'revisionNumber' => array('name' => 'revisionNumber', 'type' => 'xsd:int'),
        'revisionName' => array('name' => 'productName', 'type' => 'xsd:string'),
        'masterWorkspace' => array('name' => 'masterWorkspace', 'type' => 'xsd:int')
			)
		),
		array (
			'name' => 'RevisionList', 'type' => 'array',
			'definition' => 'Product'
		),
		array (
			'name' => 'vPrimitive', 'type' => 'struct',
			'definition' => array(
				'voID' => array('name' => 'voID', 'type' => 'xsd:int'),
				'vpID' => array('name' => 'voID', 'type' => 'xsd:int'),
				'content' => array('name' => 'content', 'type' => 'xsd:string')
			)
		),
		array(
			'name' => 'vPrimitiveList', 'type' => 'array',
			'content' => 'vPrimitive'
		),
		array(
			'name' => 'vObject', 'type' => 'stuct',
			'content' => array(
				'vpID' => array('name' => 'voID', 'type' => 'xsd:int'),
				'voName' => array('name' => 'voName', 'type' => 'xsd:string')
			)
		),
		array(
			'name' => 'vObjectList', 'type' => 'array',
			'content' => 'vObject'
		),
		array(
			'name' => 'vSelector', 'type' => 'struct',
			'content' => array(
				'vObj' => array('name' => 'vObj', 'type' => 'tns:vObject'),
				'vPri' => array('name' => 'vPri', 'type' => 'tns:vPrimitive')
			)
		)
	);
	return $definition_list;
}

function register_wsdl_types($server) { 
	$dl = get_types();
	foreach ($dl as $item) {
		if($item['type'] == 'strct' ) {
			$server->wsdl->addComplexType(
		    $item['name'],
		    'complexType',
		    'struct',
		    'all',
		    '',
		    $item['definition']
	    );
		} elseif ($item['type'] == 'array') {
			$server->wsdl->addComplexType(
				$item['name'],
				'complexType',
				'array',
				'',
				'SOAP-ENC:Array',
				array(),
				array(
					array('ref'=>'SOAP-ENC:arrayType',
					'wsdl:arrayType'=>'tns:'.$item['definition'].'[]')
				),
				'tns:'.$item['definition']
			);
		}
	}
}

?>