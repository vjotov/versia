<?php

class vw_vo_publish {
	function show($parameter_in) {
		?>
		Successful 
		<a href="?request=ws_open&ws_id=<?php echo $parameter_in['ws_id']; ?>" >Go to workspace</a>
		<?php
	}
}
?>