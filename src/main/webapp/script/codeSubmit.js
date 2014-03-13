function enableTab(id){
	var el = document.getElementById(id);

	el.onkeydown = function(e){
		if(e.keyCode === 9){
			console.log("tab");
			var val = this.value,
				start = this.selectionStart,
				end = this.selectionEnd;
			
			this.value = val.substring(0, start) + '\t' + val.substring(end);

			this.selectionStart = this. selectionEnd = start + 1;
			return false;
		}
	}
}

/* Functions */
var submit = {
	objectify: function(t1,t2,t3,t4){
		return {
			name: t1,
			text: t2,
			language: t3,
			privacy: t4
		};
	}
};

var pump = {
    loadSubmitted: function(objekt) {
        var header = $('#resp-name');
		var body = $('#resp-body');
		body.empty();
		header.hide();
		body.hide();
		header.html(objekt.name);
		body.append("<pre class=\"brush: " + objekt.language.toLowerCase() + "\">" + objekt.text + "</pre>");
		SyntaxHighlighter.highlight();
		header.fadeToggle();
		body.fadeToggle();
    },

    newCode: function() {
        var name = $('#codename').val();
		var text = $('#codearea').val();
		var language = $('#language').find(':selected').text();
		var privacy = $('#privacy').find(':selected').text();
		var objekt = submit.objectify(name,text,language,privacy);
        $.ajax('/data', {
            type: 'POST',
            data: JSON.stringify(objekt), // pack the bid object into json string
            success: function(objekt) {
                // server returns the bid with its new generated id
                // syncing js&dom is a pain. angularjs may help
				pump.loadSubmitted(objekt);
				console.log(objekt);
            },
            error: function(req, text) {
				objekt.name = "Uploading failed. Failed to connect to server";
                loadSubmitted(objekt);
            }
        });
    }
};

$(function() {
    $('#submit1').click(function() {
		pump.newCode();
    });
	
	enableTab("codearea");
});



