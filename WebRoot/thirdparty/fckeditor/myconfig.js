FCKConfig.FontNames	= '宋体;黑体;楷体_GB2312;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
//TODO 读取cookie本地化信息
FCKConfig.AutoDetectLanguage = false ;
FCKConfig.DefaultLanguage = 'zh-cn' ;
FCKConfig.StartupFocus = false ;

FCKConfig.Plugins.Add('simplecommands');
FCKConfig.Plugins.Add('Media','zh-cn');
//FCKConfig.Plugins.Add('flvPlayer','zh-cn');
//FCKConfig.Plugins.Add('CharsCounter');
FCKConfig.ToolbarSets["My"] = [
	['Source','FitWindow','Preview','Templates'],
	['PasteText','PasteWord'],
	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink','Anchor'],
	['Image','Flash','Media','Table','SpecialChar'],
	['StyleSimple','FontFormatSimple','FontNameSimple','FontSizeSimple'],
	['TextColor','BGColor'],
	['PageBreak']
];
FCKConfig.ToolbarSets["Simple"] = [
	['Bold','Italic','Underline','StrikeThrough'],
	['StyleSimple','FontFormatSimple','FontNameSimple','FontSizeSimple'],
	['TextColor','BGColor','RemoveFormat'],
	['OrderedList','UnorderedList','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink','Image'],
	['Source']
];