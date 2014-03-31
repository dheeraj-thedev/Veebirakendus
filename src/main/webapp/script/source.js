var editing = false;

var code = {
	id : "",
	text : ""
};

var source = {
	objectify: function(t1,t2){
		return{
			id : t1,
			text : t2,
			SID : getCookie()
		};
	}
};

function evaluate() {
	code.text = $("#content").html();
	code.id = gup("id");
	code.lang = $("#content").attr("class");
}

function edit(){
	var button = $("#edit");
	if(editing){
		button.html("Edit");
		code.text = $("#codearea").val();
		var position = $(".content");
		position.hide();
		position.empty();
		position.append("<pre class=\"" + code.lang + "\" id=\"content\">" + code.text + "</pre>");
		SyntaxHighlighter.highlight();
		position.fadeToggle();
		editing = false;
		var objekt = source.objectify(code.id,code.text);
		$.ajax('/edit', {
            type: 'POST',
            data: JSON.stringify(objekt), // pack the bid object into json string
            success: function(objekt) {
                //comment
            },
            error: function(req, text) {
				console.log(text);
            }
        });
	}
	else{
		button.html("Save");
		var position = $(".content");
		position.hide();
		position.empty();
		position.append("<textarea id=\"codearea\"></textarea>");
		$("#codearea").val(code.text);
		enableTab("codearea");
		position.fadeToggle();
		editing = true;
	}
};

$(function() {
	evaluate();
	SyntaxHighlighter.highlight();
	$("#edit").click(function(){
		edit();
	});
});