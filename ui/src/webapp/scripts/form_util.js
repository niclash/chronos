function checkUncheckAll( theElement )
{
    var theForm = theElement.form, z = 0;
    for( z = 0; z < theForm.length; z++ )
    {
        if( theForm[z].type == 'checkbox' && theForm[z].name != 'checkall' )
        {
            theForm[z].checked = theElement.checked;
        }
    }
}

function setSelectionRange( input, selectionStart, selectionEnd )
{
    if( input.setSelectionRange )
    {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }
    else if( input.createTextRange )
    {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    }
}

function replaceSelection( input, replaceString )
{
    if( input.setSelectionRange )
    {
        var selectionStart = input.selectionStart;
        var selectionEnd = input.selectionEnd;
        input.value = input.value.substring(0, selectionStart) + replaceString + input.value.substring(selectionEnd);

        if( selectionStart != selectionEnd )
        {
            setSelectionRange(input, selectionStart, selectionStart + replaceString.length);
        }
        else
        {
            setSelectionRange(input, selectionStart + replaceString.length, selectionStart + replaceString.length);
        }

    }
    else if( document.selection )
    {
        var range = document.selection.createRange();

        if( range.parentElement() == input )
        {
            var isCollapsed = range.text == '';
            range.text = replaceString;

            if( !isCollapsed )
            {
                range.moveStart('character', -replaceString.length);
                range.select();
            }
        }
    }
}

function catchTab( item, e )
{
    if( navigator.userAgent.match("Gecko") )
    {
        c = e.which;
    }
    else
    {
        c = e.keyCode;
    }
    if( c == 9 )
    {
        replaceSelection(item, String.fromCharCode(9));
        setTimeout("document.getElementById('" + item.id + "').focus();", 0);
        return false;
    }

}


function getElementsByClassName( strClass, strTag, objContElm )
{
    strTag = strTag || "*";
    objContElm = objContElm || document;
    var objColl = objContElm.getElementsByTagName(strTag);
    if( !objColl.length && strTag == "*" && objContElm.all ) objColl = objContElm.all;
    var arr = new Array();
    var delim = strClass.indexOf('|') != -1 ? '|' : ' ';
    var arrClass = strClass.split(delim);
    for( var i = 0, j = objColl.length; i < j; i++ )
    {
        var arrObjClass = objColl[i].className.split(' ');
        if( delim == ' ' && arrClass.length > arrObjClass.length ) continue;
        var c = 0;
        comparisonLoop:
                for( var k = 0, l = arrObjClass.length; k < l; k++ )
                {
                    for( var m = 0, n = arrClass.length; m < n; m++ )
                    {
                        if( arrClass[m] == arrObjClass[k] ) c++;
                        if( ( delim == '|' && c == 1) || (delim == ' ' && c == arrClass.length) )
                        {
                            arr.push(objColl[i]);
                            break comparisonLoop;
                        }
                    }
                }
    }
    return arr;
}

// To cover IE 5.0's lack of the push method
Array.prototype.push = function( value )
{
    this[this.length] = value;
}
