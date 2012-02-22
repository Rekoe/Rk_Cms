/*
 * Media Plugin for FCKeditor 2.5 SVN
 * Copyright (C) 2007 Riceball LEE (riceballl@hotmail.com)
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Plugin to insert "Media" in the editor.
 */

// Register the related command.
FCKCommands.RegisterCommand( 'Media', new FCKDialogCommand( 'Media', FCKLang.DlgMediaTitle, FCKPlugins.Items['Media'].Path + 'fck_media.html', 450, 428 ) ) ;

// Create the "Media" toolbar button.
var oMediaItem = new FCKToolbarButton( 'Media', FCKLang.MediaBtn,  FCKLang.MediaBtnTooltip) ;
oMediaItem.IconPath = FCKPlugins.Items['Media'].Path + 'images/media.gif' ;

FCKToolbarItems.RegisterItem( 'Media', oMediaItem ) ;

//--------------------------------------------------
function _Import(aSrc) {
   //document.write('<scr'+'ipt type=\"text/javascript\" src=\"' + aSrc + '\"></sc' + 'ript>');

  var vElement = document.createElement("script");
  vElement.type= "text/javascript";
  vElement.src= aSrc;

  var head=document.getElementsByTagName("head")[0];
  head.appendChild(vElement);

};

function _ImportCSS(aSrc) {
  var vElement = document.createElement("link");
  vElement.rel   = "stylesheet";
  vElement.type  = "text/css";
  vElement.href  = aSrc;
  //vElement.title= 'ddd';
  //vElement.disabled=false;

  var head=document.getElementsByTagName("head")[0];
  head.appendChild(vElement);
};

_Import(FCKPlugins.Items['Media'].Path + 'js/fck_media_inc.js');


var FCKMediaProcessor = FCKDocumentProcessor.AppendNew() ;

//hack to add the media css to FCKEditingArea.
FCKMediaProcessor.EditingArea_StartBefore = function ( html, secondCall )
{
  var sHeadExtra = '<link href="' + FCKPlugins.Items['Media'].Path + 'css/fck_media.css" rel="stylesheet" type="text/css" _fcktemp="true" />' ;
  html = html.replace( FCKRegexLib.HeadCloser, sHeadExtra + '$&' ) ;
  return arguments;
}
FCKEditingArea.prototype.Start =  Inject(FCKEditingArea.prototype.Start, FCKMediaProcessor.EditingArea_StartBefore);

FCKEmbedAndObjectProcessor.AddCustomHandler( function( el, fakeImg ) {
	if ( ! ( el.nodeName.IEquals( 'object' ) && el.mediatype))
		return ;
	fakeImg.className = 'FCK__Media_' + el.mediatype;
	fakeImg.setAttribute( '_fckmedia', 'true', 0 );
} ) ;
	
//hack and rewrite FCKConfig.ProtectedSource.Protect, for i do not wanna walk through twice.
FCKConfig.ProtectedSource.Protect = function( html )
{
  var codeTag = this._CodeTag ;
  function _Replace( protectedSource )
  {
    //check if it is the media object:
    if (protectedSource.match(/<object[\s\S]+?<\/object>/gi) && (protectedSource.indexOf(cMediaTypeAttrName+'=') >=0)) {
      var regexEmbed = new RegExp('<embed\\s+(.+?)><\/embed>', 'i');
      return '<'+cFckMediaElementName + ' '+ protectedSource.match(regexEmbed)[1] + '></'+cFckMediaElementName+'>';
    }

    var index = FCKTempBin.AddElement( protectedSource ) ;
    return '<!--{' + codeTag + index + '}-->' ;

  }

  for ( var i = 0 ; i < this.RegexEntries.length ; i++ )
  {
    html = html.replace( this.RegexEntries[i], _Replace ) ;
  }

  return html ;
}

//hacked FCKConfig.ProtectedSource.Revert
FCKMediaProcessor.ProtectedSource_RevertBefore = function ( html, clearBin )
{
  html = WrapObjectToMedia(html, cFckMediaElementName);
  return arguments;
}
FCKConfig.ProtectedSource.Revert = Inject(FCKConfig.ProtectedSource.Revert, FCKMediaProcessor.ProtectedSource_RevertBefore)

FCKMediaProcessor.ProcessDocument = function( aDocument )
{
  /* convert the source to WSWG design
  Sample code:
  This is some <embed src="/UserFiles/Flash/Yellow_Runners.swf" mediatype="0"></embed><strong>sample text</strong>. You are&nbsp;<a name="fred"></a> using <a href="http://www.fckeditor.net/">FCKeditor</a>.
  */

  var aEmbeds = aDocument.getElementsByTagName( cFckMediaElementName ) ;

  var oEmbed ;
  var i = aEmbeds.length - 1 ;

  while ( i >= 0 && ( oEmbed = aEmbeds[i--] ) )
  {
    if (typeof(oEmbed.attributes[ cMediaTypeAttrName ]) != 'undefined') {

      var vTypeId = oEmbed.attributes[ cMediaTypeAttrName ].value ;
  
      if (isInt(vTypeId))
      {
        var oCloned = oEmbed.cloneNode( true ) ;
  
        var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Media_'+vTypeId, oCloned ) ;
        oImg.setAttribute( '_fckmedia', 'true', 0 ) ;
  
        FCKMediaProcessor.RefreshView( oImg, oEmbed ) ;
  
        oEmbed.parentNode.replaceChild(oImg, oEmbed);
      }
    }
  }
}

FCKMediaProcessor.RefreshView = function( placeHolderImage, originalEmbed )
{
  if ( originalEmbed.getAttribute( 'width' ) > 0 )
    placeHolderImage.style.width = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.getAttribute( 'width' ) ) ;

  if ( originalEmbed.getAttribute( 'height' ) > 0 )
    placeHolderImage.style.height = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.getAttribute( 'height' ) ) ;
}

// Open the Media Properties dialog on double click.
FCKMediaProcessor.OnDoubleClick = function( e )
{
  if ( e.tagName == 'IMG' && e.getAttribute('_fckmedia') == 'true' )
    FCKCommands.GetCommand( 'Media' ).Execute() ;
}

FCK.RegisterDoubleClickHandler( FCKMediaProcessor.OnDoubleClick, 'IMG' ) ;


FCK.ContextMenu.RegisterListener( {
        AddItems : function( menu, tag, tagName )
        {
                // under what circumstances do we display this option
                if ( tagName == 'IMG' && tag.getAttribute( '_fckmedia' ) )
                {
                        // when the option is displayed, show a separator  the command
                        menu.AddSeparator() ;
                        // the command needs the registered command name, the title for the context menu, and the icon path
                        menu.AddItem( 'Media', FCKLang.DlgMediaTitle, oMediaItem.IconPath ) ;
                }
        }}
);


FCKMediaProcessor.GetRealElementAfter = function( fakeElement, Result ) 
{
  if ( fakeElement.getAttribute('_fckmedia') )
  {
    if ( fakeElement.style.width.length > 0 ) {
        Result.width = FCKTools.ConvertStyleSizeToHtml( fakeElement.style.width ) ;
        if(Result.childNodes.length>0) {
			Result.childNodes[Result.childNodes.length-1].width = Result.width;
        }
    }

    if ( fakeElement.style.height.length > 0 ) {
        Result.height = FCKTools.ConvertStyleSizeToHtml( fakeElement.style.height ) ;
        if(Result.childNodes.length>0) {
			Result.childNodes[Result.childNodes.length-1].height = Result.height;	
        }
    }
  }

  return Result ;
}
FCK.GetRealElement = Inject(FCK.GetRealElement, undefined, FCKMediaProcessor.GetRealElementAfter)

/*
  @desc  inject the function
  @param aOrgFunc     the original function to be injected.
  @param aBeforeExec  this is called before the execution of the aOrgFunc.
                      you must return the arguments if you wanna modify the value of the aOrgFunc's arguments .
  @param aAtferExec   this is called after the execution of the aOrgFunc.
                      you must add a result argument at the last argument of the aAtferExec function if you wanna 
                      get the result value of the aOrgFunc.
                      you must return the result if you wanna modify the result value of the aOrgFunc .

  @Usage  Obj.prototype.aMethod = Inject(Obj.prototype.aMethod, aFunctionBeforeExec[, aFunctionAtferExec]);
  @author  Aimingoo&Riceball

  eg:
  var doTest = function (a) {return a};
  function beforeTest(a) { alert('before exec: a='+a); a += 3; return arguments;};
  function afterTest(a, result) { alert('after exec: a='+a+'; result='+result); return result+5;};
  
  doTest = Inject(doTest, beforeTest, afterTest);
  
  alert (doTest(2));
  the result should be 10.

*/
function Inject( aOrgFunc, aBeforeExec, aAtferExec ) {
  return function() {
    if (typeof(aBeforeExec) == 'function') arguments = aBeforeExec.apply(this, arguments) || arguments;
    //convert arguments object to array
    var Result, args = [].slice.call(arguments); 
    args.push(aOrgFunc.apply(this, args));
    if (typeof(aAtferExec) == 'function') Result = aAtferExec.apply(this, args);
    return (typeof(Result) != 'undefined')?Result:args.pop();
  }
}
