function gebi(el) {
	return document.getElementById(el);
}

function clickHandler(val)
{
    srcElement = window.event.srcElement;

/*    alert( srcElement.className ); */

    if ( srcElement.className == "Outline")
     { 

         targetId = srcElement.id + "details";
         targetElement = document.all(targetId);
		 if (targetElement.style.display == "none")
		 { 
			targetElement.style.display = ""; 
                 } 
		 else
		 { 
			if (val = 0) { targetElement.style.display = "none";}  
		 }
     }
 document.onclick = clickHandler;

}

/* ADRIVER  IBM */
function ar_getDoc(t,n,l)
{ var doc;
	if(t<100){if(document.all && !window.opera){doc = window.frames['ar_container_'+n].document;}
	else if(document.getElementById){doc=document.getElementById('ar_container_'+n).contentDocument;}
	if(!doc){setTimeout('ar_getDoc('+(++t)+','+n+',"'+l+'")', 100);}
	else {var RndNum4NoCash = Math.round(Math.random() * 1000000000);
	doc.write ('<sc'+'ript>var ar_bnum='+n+';<\/sc'+'ript>');
	doc.write ('<sc'+'ript src="'+l+'&rnd='+RndNum4NoCash+'"><\/sc'+'ript>');}}
}
function ar_putContainer(n){document.write('<div style="visibility:hidden"><iframe id="ar_container_'+n+'" width=1 height=1 marginwidth=0 marginheight=0 scrolling=no frameborder=0><\/iframe><\/div>');}
function ar_putDiv(n, f){document.write('<div id="ad_ph_'+n+'" style="'+(f?'position:absolute;':'')+'display:none"><\/div>');}
