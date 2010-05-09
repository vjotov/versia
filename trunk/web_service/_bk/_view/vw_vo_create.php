<?php

class vw_vo_create {
	function show($parameter_in) { 
		?>
		sucessfull
		<a href="?request=ws_open&ws_id=<?php echo $parameter_in['ws_id']; ?>">back to workspace</a>
		<?php
	}
}

?>