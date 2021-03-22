<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="wrap">
	<div id="grid_paging"></div>
</div>

<script>
var ewdebug = {};
$(function(){
	//define common ajax object for addition, update and delete.
	var ajaxObj = {
        dataType: "JSON",
        method : "POST",
        contentType : "application/json",
        beforeSend: function () {
            this.pqGrid("showLoading");
        },
        complete: function () {
            this.pqGrid("hideLoading");
        },
        error: function () {
            this.pqGrid("rollback");
        }
    };	
	
	//to check whether any row is currently being edited.
	var isEditing = function($grid) {
        var rows = $grid.pqGrid("getRowsByClass", { cls: "pq-row-edit" });
        if (rows.length > 0) {
            var rowIndx = rows[0].rowIndx;
            $grid.pqGrid("goToPage", { rowIndx: rowIndx });
            //focus on editor if any
            $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });
            return true;
        }
        return false;
    };
    
  	//called by add button in toolbar
    var addRow = function($grid) {
        if (isEditing($grid)) {
            return false;
        }
        //append empty row in the first row.                            
        var rowData = { title: "", subtitle: "", summary: "", content: "", author: "ewide" }; //empty row template
        $grid.pqGrid("addRow", { rowIndxPage: 0, rowData: rowData });

        var $tr = $grid.pqGrid("getRow", { rowIndxPage: 0 });
        if ($tr) {
            //simulate click on edit button.
            $tr.find("button.edit_btn").click();
        }
    }	
	
  	//called by delete button.
    var deleteRow = function(rowIndx, $grid) {
        $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: "pq-row-delete" });
        var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx });
        var ans = window.confirm("Are you sure to delete row No " + (rowIndx + 1) + "?");
        if (ans) {
            var id = $grid.pqGrid("getRecId", { rowIndx: rowIndx });
            $grid.pqGrid("deleteRow", { rowIndx: rowIndx });

            $.ajax($.extend({}, ajaxObj, {
                context: $grid,
                url: "/v1/boards/delete",
                data: JSON.stringify({seq: id}),
                success: function () {
                    this.pqGrid("commit");
                    this.pqGrid("refreshDataAndView");
                },
                error: function () {
                    this.pqGrid("removeClass", { rowData: rowData, cls: "pq-row-delete" });
                    this.pqGrid("rollback");
                }            
			}));
        }
        else {
            $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: "pq-row-delete" });
        }
    }
  	
    //called by edit button.
    var editRow = function(rowIndx, $grid) {
        $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: "pq-row-edit" });
        $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });

        //change edit button to update button and delete to cancel.
        var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
            $btn = $tr.find("button.edit_btn");
        $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
            .unbind("click")
            .click(function (evt) {
                evt.preventDefault();
                return update(rowIndx, $grid);
            });
        $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
            .unbind("click")
            .click(function (evt) {
                $grid.pqGrid("quitEditMode")
                    .pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' })
                    .pqGrid("rollback");
            });
    }
    
    //called by update button.
    var update = function(rowIndx, $grid) {
        var grid = $grid.pqGrid("getInstance").grid;
        if (grid.saveEditCell() == false) {
            return false;
        }

        var isValid = grid.isValid({ rowIndx: rowIndx }).valid;
        if (!isValid) {
            return false;
        }

        if (grid.isDirty()) {
            var url,
                rowData = grid.getRowData({ rowIndx: rowIndx }),
                recIndx = grid.option("dataModel.recIndx"),
                type;

            grid.removeClass({ rowIndx: rowIndx, cls: "pq-row-edit" });

            if (rowData[recIndx] == null) {
                type = "add";
                url = "/v1/boards/create";
            }else {
                type = "update";
                url = "/v1/boards/update";
            }
            $.ajax($.extend({}, ajaxObj, {
                context: $grid,
                url: url,
                data: JSON.stringify(rowData),
                success: function (response) {
                	console.log(response);
                    if (type == "add") {
                        rowData[recIndx] = response.recId;
                    }
                    //debugger;
                    grid.commit({ type: type, rows: [rowData] });
                    grid.refreshRow({ rowIndx: rowIndx });
                }
            }));
        } else {
            grid.quitEditMode();
            grid.removeClass({ rowIndx: rowIndx, cls: "pq-row-edit" });
            grid.refreshRow({ rowIndx: rowIndx });
        }
    }  	
  	
	var filterhandler = function(evt, ui) {
        var $toolbar = $grid.find(".pq-toolbar-search"),
            $value = $toolbar.find(".filterValue"),
            value = $value.val(),
            condition = $toolbar.find(".filterCondition").val(),
            dataIndx = $toolbar.find(".filterColumn").val(),
            filterObject;

        filterObject = [{ dataIndx: dataIndx, condition: condition, value: value}];
        $grid.pqGrid("filter", {
            oper: "replace",
            data: filterObject
        });
    };
    
    var colModel 	= [
    	{title:"All", 		width:0, 	editable: false,	dataType:"string",	align:"center",	dataIndx:"all", hidden: true},
    	{title:"Id", 		width:50, 	editable: false,	dataType:"integer",	align:"center",	dataIndx:"seq"},
        {title:"Title", 	width:200, 	editable: true,		align:"left", 	dataIndx:"title",
    		validations: [
                { type: 'minLen', value: 1, msg: "Required" },
                { type: 'maxLen', value: 100, msg: "length should be <= 100" }
            ]
    	},
        {title:"Subtitle", 	width:250, 	editable: true,		align:"left",	dataIndx:"subtitle",
    		validations: [
                { type: 'minLen', value: 1, msg: "Required" },
                { type: 'maxLen', value: 200, msg: "length should be <= 200" }
            ]
    	},
        {title:"Summary", 	width:300, 	editable: true,		align:"left",	dataIndx:"summary",
    		validations: [
                { type: 'minLen', value: 1, msg: "Required" },
                { type: 'maxLen', value: 400, msg: "length should be <= 400" }
            ]
    	},
        {title:"Content", 	width:300, 	editable: true,		align:"left",	dataIndx:"content",
    		validations: [
                { type: 'minLen', value: 1, msg: "Required" },
                { type: 'maxLen', value: 1000, msg: "length should be <= 1000" }
            ]
    	},
        {title:"Author", 	width:100, 	editable: true,		align:"center",	dataIndx:"author",
    		validations: [
                { type: 'minLen', value: 1, msg: "Required" },
                { type: 'maxLen', value: 50, msg: "length should be <= 50" }
            ]
    	},
        {title:"Reg. Date", width:150, 	editable: false,	align:"center", dataIndx:"regDate"},
        {title: "Action", editable: false, align : "center", width: 200, sortable: false, render: function (ui) {
            return "<button type='button' class='edit_btn'>Edit</button>\  <button type='button' class='delete_btn'>Delete</button>";
        }}        
    ];
    var dataModel = {
		location	: "remote",
		sorting		: "remote",
		dataType	: "JSON",
		recIndx		: "seq",
		method		: "GET",
		url 		: "/v1/boards/list",
		getData		: function(json){
			return {
				curPage : json.meta.currentPage,
				totalRecords : json.meta.totalRecords,
				data : json.data.attributes
			}
		}
	};
    
    var obj = { 
		width: 1580,
		flexHeight: true,
		scrollModel: {autoFit: true},
		dataModel: dataModel,
		colModel: colModel,
        selectionModel: {
            type: "cell"
        },
		filterModel: { mode: "OR" },
		columnBorders: false,
		toolbar: {
		    cls: "pq-toolbar-search",
		    items: [
		    	{ type: "button", icon: "ui-icon-plus", label: "Add Item", listener:
	                { "click": function (evt, ui) {
	                    var $grid = $(this).closest(".pq-grid");
	                    addRow($grid);
	                }
	                }
            	},
            	{ type: "<span style='margin:5px;'>Filter</span>" },
		        { type: "textbox", cls: "filterValue", 
		        	listeners: [{ "change": filterhandler}] 
		        },
		        { type: "select", cls: "filterColumn",
		            listeners: [{ "change": filterhandler}],
		            options: function (ui) {
		                var opts = [
		                	{ "all": "All"}, 
		                	{ "title": "Title"}, 
		                	{ "content": "Content"}
		                ];
		                return opts;
		            }
		        }
		    ]
		},
		pageModel: { type: "remote", rPP: 20, strRpp: "{0}", curPage:1 },
		wrap: true, 
		hwrap: false,
		numberCell: { resizable: true, width: 30, title: "No." },
		title: "eW+ Boards",
        scrollModel: {
            autoFit: true
        },
		showBottom: true,
		resizable: true,
		editModel: {
            saveKey: $.ui.keyCode.ENTER,
            onSave: "next"
        },
        editor: { type: "textbox", select: true, style: "outline:none;" },
        validation: {
            icon: "ui-icon-info"
        },
        track: true,
        editable : function (ui) {
            var $grid = $(this);
            var rowIndx = ui.rowIndx;
            if ($grid.pqGrid("hasClass", { rowIndx: rowIndx, cls: "pq-row-edit" }) == true) {
                return true;
            }else {
                return false;
            }
        },
        create: function (evt, ui) {
            $(this).pqTooltip();
        }
	};
    
    var $grid = $("div#grid_paging").pqGrid(obj);  
    
  	//check the changes in grid before navigating to another page or refresh data.
    $grid.find(".pq-pager").on("pqpagerbeforechange pqpagerbeforerefresh", function (evt, ui) {
        if ($grid.pqGrid("isDirty")) {
            var ans = window.confirm("There are unsaved changes. Are you sure?");
            if(!ans){
                return false;
            }
        }
    });
  
    //use refresh & refreshRow events to display jQueryUI buttons and bind events.
    $grid.on("pqgridrefresh pqgridrefreshrow", function () {
        var $grid = $(this);

        //delete button
        $grid.find("button.delete_btn").button({ icons: { primary: "ui-icon-close"} })
        .unbind("click")
        .bind("click", function (evt) {
            if (isEditing($grid)) {
                return false;
            }
            var $tr = $(this).closest("tr"),
                rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
            deleteRow(rowIndx, $grid);
        });
        
        //edit button
        $grid.find("button.edit_btn").button({ icons: { primary: "ui-icon-pencil"} })
        .unbind("click")
        .bind("click", function (evt) {
            if (isEditing($grid)) {
                return false;
            }
            var $tr = $(this).closest("tr"),
                rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
            editRow(rowIndx, $grid);
            return false;
        });

        //rows which were in edit mode before refresh, put them in edit mode again.
        var rows = $grid.pqGrid("getRowsByClass", { cls: "pq-row-edit" });
        if (rows.length > 0) {
            var rowIndx = rows[0].rowIndx;
            editRow(rowIndx, $grid);
        }
    });
});        
</script>