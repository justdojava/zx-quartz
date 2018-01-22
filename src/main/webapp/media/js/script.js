$(document).ready(function(){

	$('.u-text label').inFieldLabels();

	function tabs(tabsNav, tabsContent, activeClass, eventType){
		$(tabsNav).each(function(){
			$(this).children().eq(0).addClass(activeClass)
		});
		$(tabsContent).each(function(){
			$(this).children().eq(0).show();
		});
		$(tabsNav).children().on(eventType, function(){
			$(this).addClass(activeClass).siblings().removeClass(activeClass);
            var index = $(tabsNav).children().index(this);
            $(tabsContent).children().eq(index).show().siblings().hide();
		});
	};

	$('.fixed-nav .item-top').on('click', function(){
		$('body, html').animate({scrollTop: 0});
	});

	$('.u-select').each(function(){
		var value = $(this).find('.value');
		var list = $(this).find('.list');
		var listLi = list.find('li');
		listLi.each(function(){
			if($(this).text()==value.text()){
				$(this).addClass('z-active').siblings().removeClass('z-active');
			}
		});
		value.on('click',function(e){
			if(list.is(':hidden')){
				$('.u-select').find('.list').hide();
				list.show();
				e.stopPropagation();
			}else{
				list.hide();
			};	
		});
		list.on('click','li',function(){
			$(this).addClass('z-active').siblings().removeClass('z-active');
			value.find('.val').text($(this).text());
			value.find('.val').attr('value',$(this).attr('value'));
			list.hide();
		});
		$(document).on('click',function(){
			$('.u-select').find('.list').hide();
		});
	});

});