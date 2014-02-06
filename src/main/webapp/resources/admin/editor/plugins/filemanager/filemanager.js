/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

KindEditor.plugin("filemanager", function(K) {

	var self = this;
	var name = "filemanager";
	var fileManagerJson = K.undef(self.fileManagerJson, self.basePath + "php/file_manager_json.php");
	var imagePath = self.pluginsPath + name + "/images/";
	var lang = self.lang(name + ".");
	
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"H+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S" : this.getMilliseconds()
		}
		
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		
		for (var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}

	self.plugin.filemanagerDialog = function(options) {
		var width = K.undef(options.width, 650);
		var height = K.undef(options.height, 510);
		var fileType = K.undef(options.fileType, "");
		var viewType = K.undef(options.viewType, "view");
		var clickFn = options.clickFn;
		
		var html = [
			'<div style="padding:10px 20px;">',
			'<div class="ke-plugin-filemanager-header">',
			'<div class="ke-left">',
			'<img class="ke-inline-block" name="moveupImg" src="' + imagePath + 'go-up.gif" width="16" height="16" border="0" alt="" /> ',
			'<a class="ke-inline-block" name="moveupLink" href="javascript:;">' + lang.moveup + '</a>',
			'</div>',
			'<div class="ke-right">',
			lang.viewType + ' <select class="ke-inline-block" name="viewType">',
			'<option value="view">' + lang.viewImage + '</option>',
			'<option value="list">' + lang.listImage + '</option>',
			'</select> ',
			lang.orderType + ' <select class="ke-inline-block" name="orderType">',
			'<option value="name">' + lang.fileName + '</option>',
			'<option value="size">' + lang.fileSize + '</option>',
			'<option value="type">' + lang.fileType + '</option>',
			'</select>',
			'</div>',
			'<div class="ke-clearfix"></div>',
			'</div>',
			'<div class="ke-plugin-filemanager-body"></div>',
			'</div>'
		].join("");
		
		var dialog = self.createDialog({
			name : name,
			width : width,
			height : height,
			title : self.lang(name),
			body : html
		}),
		
		div = dialog.div,
		bodyDiv = K(".ke-plugin-filemanager-body", div),
		moveupImg = K("[name='moveupImg']", div),
		moveupLink = K("[name='moveupLink']", div),
		viewServerBtn = K("[name='viewServer']", div),
		viewTypeBox = K("[name='viewType']", div),
		orderTypeBox = K("[name='orderType']", div);
		
		function reloadPage(path, orderType, func) {
			var param = "path=" + path + "&fileType=" + fileType + "&orderType=" + orderType;
			dialog.showLoading(self.lang("ajaxLoading"));
			K.ajax(K.addParam(fileManagerJson, param + "&" + new Date().getTime()), function(data) {
				dialog.hideLoading();
				func(path, data);
			});
		}
		
		var elList = [];
		function bindEvent(el, path, data, fileInfo, createFunc) {
			if (fileInfo.isDirectory) {
				el.click(function(e) {
					reloadPage(path + fileInfo.name + "/", orderTypeBox.val(), createFunc);
				});
			} else {
				el.click(function(e) {
					clickFn.call(this, fileInfo.url, fileInfo.name);
				});
			}
			elList.push(el);
		}
		
		function createCommon(path, data, createFunc) {
			K.each(elList, function() {
				this.unbind();
			});
			moveupLink.unbind();
			viewTypeBox.unbind();
			orderTypeBox.unbind();
			
			if (path != "/") {
				var parentPath = path.substr(0, path.replace(/\/$/, "").lastIndexOf("/") + 1);
				moveupLink.click(function(e) {
					reloadPage(parentPath, orderTypeBox.val(), createFunc);
				});
			}
			
			function changeFunc() {
				if (viewTypeBox.val() == "view") {
					reloadPage(path, orderTypeBox.val(), createView);
				} else {
					reloadPage(path, orderTypeBox.val(), createList);
				}
			}
			
			viewTypeBox.change(changeFunc);
			orderTypeBox.change(changeFunc);
			bodyDiv.html("");
		}
		
		function createList(path, data) {
			createCommon(path, data, createList);
			var table = document.createElement("table");
			table.className = "ke-table";
			table.cellPadding = 0;
			table.cellSpacing = 0;
			table.border = 0;
			bodyDiv.append(table);
			for (var i = 0; i < data.length; i++) {
				var fileInfo = data[i], row = K(table.insertRow(i));
				row.mouseover(function(e) {
					K(this).addClass("ke-on");
				})
				.mouseout(function(e) {
					K(this).removeClass("ke-on");
				});
				var iconUrl = imagePath + (fileInfo.isDirectory ? "folder-16.gif" : "file-16.gif");
				var img = K('<img src="' + iconUrl + '" width="16" height="16" alt="' + fileInfo.name + '" align="absmiddle" />');
				var cell0 = K(row[0].insertCell(0)).addClass("ke-cell ke-name").append(img).append(document.createTextNode(" " + fileInfo.name));
				row.css("cursor", "pointer");
				cell0.attr("title", fileInfo.name);
				bindEvent(cell0, path, data, fileInfo, createList);
				K(row[0].insertCell(1)).addClass("ke-cell ke-size").html(fileInfo.isDirectory ? "-" : Math.ceil(fileInfo.size / 1024) + "KB");
				K(row[0].insertCell(2)).addClass("ke-cell ke-datetime").html(fileInfo.lastModified ? new Date(fileInfo.lastModified).format("yyyy-MM-dd HH:mm") : "-");
			}
		}
		
		function createView(path, data) {
			createCommon(path, data, createView);
			for (var i = 0; i < data.length; i++) {
				var fileInfo = data[i];
				var div = K('<div class="ke-inline-block ke-item"></div>');
				bodyDiv.append(div);
				var photoDiv = K('<div class="ke-inline-block ke-photo"></div>')
					.mouseover(function(e) {
						K(this).addClass("ke-on");
					})
					.mouseout(function(e) {
						K(this).removeClass("ke-on");
					});
				div.append(photoDiv);
				var iconUrl = fileInfo.isDirectory ? imagePath + "folder-64.gif" : (new RegExp("^\\S.*\\.(jpg|jpeg|bmp|gif|png)$", "i").test(fileInfo.name) ? fileInfo.url : imagePath + "file-64.gif");
				var img = K('<img src="' + iconUrl + '" width="80" height="80" alt="' + fileInfo.name + '" />');
				photoDiv.css("cursor", "pointer");
				photoDiv.attr("title", fileInfo.isDirectory ? fileInfo.name : fileInfo.name + " (" + Math.ceil(fileInfo.size / 1024) + "KB, " + new Date(fileInfo.lastModified).format("yyyy-MM-dd HH:mm") + ")");
				bindEvent(photoDiv, path, data, fileInfo, createView);
				photoDiv.append(img);
				div.append('<div class="ke-name" title="' + fileInfo.name + '">' + fileInfo.name + '</div>');
			}
		}
		viewTypeBox.val(viewType);
		reloadPage("/", orderTypeBox.val(), viewType == "view" ? createView : createList);
		return dialog;
	}

});