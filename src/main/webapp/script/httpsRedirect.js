/*
 * When full release, remove the comment tags
 */



function httpsRedirect(){
	if (window.location.protocol != "https:")
		window.location.href = "https:" + window.location.href.substring(window.location.protocol.length);
};

httpsRedirect();
