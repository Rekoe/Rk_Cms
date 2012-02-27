//v2 www.xd.com fish 2011.10.11
var imgThumb = $('#img_thumb span');//指针
var stage = $("#index_stage");//舞台
var stageLink = $('#stage_link');
var loadingContent = '<img src="/imgs/v2/img_loading.gif" class="img_loading"/>';//载入中
var intv;

$(document).ready(function(){
	
});
function clearIntv(){//清除自动轮播
    clearInterval(intv);
}


stageLink.hover(function(){
	clearIntv();
},function(){
	setIntv();
});
imgThumb.hover(function(){
	clearIntv();
},function(){
	setIntv();
});