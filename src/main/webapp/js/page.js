/**
 * 加载分页信息
 * @param currentPage
 * @param totalPage
 * @param methodName
 */
function page(currentPage,totalPage,methodName){
	$("#page").html(pageInfo(currentPage,totalPage,methodName));
}		

function pageInfo(currentPage,totalPage,methodName){
	if(methodName==null||methodName==''){
		methodName='queryListInfo';
	}
	var liClass = "";
	var linkItem = "";
	var firClass = "";
	var lasClass = "";
	var flag = "false";
	if(currentPage == 1 && currentPage != totalPage){
		firClass = " z-disable";
	}else if(currentPage == totalPage && currentPage != 1){
		lasClass = " z-disable";
	}else if(currentPage == 1 && currentPage == totalPage){
		firClass = " z-disable";
		lasClass = " z-disable";
	} 
	if(totalPage == 0){
		firClass = " z-disable";
		lasClass = " z-disable";
	}
	linkItem += "<a href='javascript:void(0);' class='first"+firClass+"' onclick='javascript:"+firstPage(methodName)+"'>第一页</a>";
	if(totalPage>=2&&currentPage!=1) {
		linkItem  += "<a href='javascript:void(0);' onclick='javascript:"+methodName+"("+(currentPage-1)+")'>&lt;</a>";
	}
	if(totalPage < 8){
		for(var i=1;i<=totalPage;i++){
			if(i==currentPage){
				liClass=" class='z-active'";
			}else{
				liClass=" ";
			}
			linkItem  += "<a href='#' "+liClass+" onclick='javascript:"+methodName+"("+i+")' alt='"+i+"'>"+i+"</a>";
		}
	}else{
		for(var i=1;i<=totalPage;i++){
			if(i==currentPage){
				liClass=" class='z-active'";
			}else{
				liClass=" ";
			}
			if(i > 5 && i < totalPage){
				if((i == currentPage)||(i == (parseInt(currentPage,10)+1))||(i == (parseInt(currentPage,10)-1))){
					linkItem  += "<a href='#' "+liClass+" onclick='javascript:"+methodName+"("+i+")' alt='"+i+"'>"+i+"</a>";
				}else{
					if(flag=="false"){
						linkItem  += "<span>...</span>";
						flag = "true;"
					}
				}
			}else{
				if(i > 2 && i < totalPage){
					if((i == currentPage)||(i == (parseInt(currentPage,10)+1))||(i == (parseInt(currentPage,10)-1))){
						linkItem  += "<a href='#' "+liClass+" onclick='javascript:"+methodName+"("+i+")' alt='"+i+"'>"+i+"</a>";
					}else{
						if(flag=="false"){
							linkItem  += "<span>...</span>";
							flag = "true;"
						}
					}
				}else{
					linkItem  += "<a href='#' "+liClass+" onclick='javascript:"+methodName+"("+i+")' alt='"+i+"'>"+i+"</a>";
				}
			}
		}
	}	
	
	if(totalPage>=2&&currentPage!=totalPage){
		var pgindex=parseInt(currentPage)+1;
		linkItem  += "<a href='javascript:void(0);' onclick='javascript:"+methodName+"("+pgindex+")'>&gt;</a>";
	}
	if(totalPage == 0){
		linkItem += "<a href='javascript:void(0);' class='first"+lasClass+"' onclick='javascript:"+lastPage(methodName,1)+"'>最末页</a>"
	}else{
		linkItem += "<a href='javascript:void(0);' class='first"+lasClass+"' onclick='javascript:"+lastPage(methodName,totalPage)+"'>最末页</a>"
	}
	
	return linkItem;
}

function firstPage(methodName){
	currentPage = 1;
	var first = methodName+"(1)";
	return first;
}

function lastPage(methodName,totalPage){
	currentPage = totalPage;
	var last = methodName+"("+totalPage+")";
	return last;
}

function isEmpty(param){
	if(typeof(param)=='undefined'){
	    return '';
	}
	return param;
}