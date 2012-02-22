Cms={};
Cms.lmenu = function(id) {
	var m = $('#' + id);
	m.addClass('lmenu');
	m.children().each(function() {
		$(this).children('a').bind('click', function() {
			$(this).parent().addClass("lmenu-focus");
			$(this).blur();
			var li = m.focusElement;
			if (li && li!=this) {
				$(li).parent().removeClass("lmenu-focus");
			}
			m.focusElement=this;
		});
	});
}