/* Create modal form element 
 * Sample input :
 * var option = {
 * 					"id" 		: "formModal",
 * 					"title" 	: "formModalTitle",
 * 					"caption" 	: "Form",
 * 					"forms" 	: [
 * 									{
 * 										"id"	: "inputForm",
 * 										"data"	: [
 * 													{"type"	: "input", 		"name" : "title", 	"id" : "title", 	"value" : "", 	"caption" : "Title", 	"required" : true},
 * 													{"type"	: "input", 		"name" : "author", 	"id" : "author", 	"value" : "", 	"caption" : "Author", 	"required" : true},
 * 													{"type"	: "input", 		"name" : "subtitle","id" : "subtitle", 	"value" : "", 	"caption" : "Subtitle", "required" : true},
 * 													{"type"	: "textarea",	"name" : "summary", "id" : "summary", 	"value" : "", 	"caption" : "Summary", 	"required" : true},
 * 													{"type"	: "textarea",	"name" : "content", "id" : "content", 	"value" : "", 	"caption" : "Content", 	"required" : true},
 * 												],
 * 									},
 * 									{
 * 										"id"	: "fileForm",
 * 										"data"	: [
 * 													{"type"	: "image", 		"name" : "mainImage","id" : "mainImage", "value" : "", 	"caption" : "Image", 	"required" : false},
 * 												],
 * 									}
 * 								],
 * 					"action"	: {
 * 								    "yes" 		: "doSubmit()",
 *                                  "yesTitle	: "Submit"
 * 								}
 * 			  }
 */
var createModalForm = function(option){
	
	var el  = '';
	    el += '<div id="'+option.id+'" class="modal" aria-hidden="true" tabindex="-1" aria-labelledby="'+option.title+'">';
	    el += '    <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">';
    	el += '        <div class="modal-content">';
		el += '             <div class="modal-header">';
		el += '                 <h5 class="modal-title" id="'+option.title+'">'+option.caption+'</h5>';
		el += '                 <button type="button" class="close" data-dismiss="modal" aria-label="Close">';
		el += '                     <span aria-hidden="true">&times;</span>';
		el += '                 </button>';
		el += '             </div>';
		el += '             <div class="modal-body">';
		
		for(formKey in option.forms){
			el += '                 <form id="'+option.forms[formKey].id+'" name="'+option.forms[formKey].id+'" enctype="multipart/form-data">';
			
			var formData = option.forms[formKey].data;
			for(key in formData){
				el += '<div class="form-row">';
				el += '    <div class="col-md-12 mb-3">';
				
				if(formData[key].type == 'input'){
					el += '        <label>'+formData[key].caption+'</label>';
					el += '        <input type="text" class="form-control"'; 
					el += '               name="'+formData[key].name+'"';
					el += '               id="'+formData[key].id+'"';
					el += '               value="'+formData[key].value+'"';
				}else if(formData[key].type == 'textarea'){
					el += '        <label>'+formData[key].caption+'</label>';
					el += '        <textarea class="form-control"'; 
					el += '               name="'+formData[key].name+'"';
					el += '               id="'+formData[key].id+'"';
					el += '               value="'+formData[key].value+'"';
					el += '               rows="5"';
				}else if(formData[key].type == 'image'){
					el += '        <div class="row">';
					el += '            <div class="col-md-3 mb-3" id="'+formData[key].id+'ConGroup">';
					el += '                <img id="'+formData[key].id+'Con" src="" class="img-thumbnail">';					
					el += '            </div>';
					el += '        </div>';
					el += '        <input type="file"'; 
					el += '               name="'+formData[key].name+'"';
					el += '               id="'+formData[key].id+'"';
				}
				
				if(formData[key].required == true){
					el += ' required>';
				}else{
					el += '>';
				}
				el += '    </div>';
				el += '</div>';
			}
			
			el += '                 </form>';
		}
		
		el += '             </div>';
		el += '             <div class="modal-footer">';
		
		if(option.action.no != undefined){
			el += '	                <button class="btn btn-primary" type="submit" onclick="'+option.action.no+'">'+option.action.noTitle+'</button>&nbsp;';
		}
		
		if(option.action.yes != undefined){
			el += '	                <button class="btn btn-primary" type="submit" onclick="'+option.action.yes+'">'+option.action.yesTitle+'</button>';
		}
		
		el += '             </div>';
		el += '        </div>';
		el += '    </div>';
		el += '</div>';
		
	return el;
};