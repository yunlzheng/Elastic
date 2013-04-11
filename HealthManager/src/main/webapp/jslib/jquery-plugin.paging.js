/**
 * 前端分页插件
 * @author zheng
 * @version 0.0.1
 */
jQuery.fn.qucikPage = function(options) {

	var defaults = {
		pagesize : 5,
		currentpage : 1,
		target : "body"
	}

	var options = $.extend(defaults, options);
	var element = $(this);
	var paggerCounter = 1;
	var totalCounter = 0;

	$.proxy(element.children().each(function(i) {

		if (i < paggerCounter * options.pagesize) {

			$(this).addClass("page" + paggerCounter);

		} else {

			$(this).addClass("page" + (paggerCounter + 1));
			paggerCounter++;

		}
		totalCounter++;

	}), this);
	
	element.children().hide();
	element.children(".page" + options.currentpage).show();
	$(options.target).empty();
	
	$(options.target).append('<div class="table-data-summary">第 '+ ((options.currentpage - 1) * options.pagesize + 1)+ ' - ' + (options.currentpage * options.pagesize)+ '条记录 / 共 ' + totalCounter + '条记录</div>');
	
	$(options.target)
			.append(
					'<div class="table-pagination">'
							+ '<span class="pagination-info">第'
							+ options.currentpage
							+ '页 / 共 '
							+ paggerCounter
							+ '页 </span>'
							+ '<a  class="pagination-button" rel="first"><img src="../assets/images/pagination-first-arrow.png" /></a> '
							+ '<a  class="pagination-button" rel="brfore"><img src="../assets/images/pagination-prev-arrow.png" /></a>'
							+ '<a  class="pagination-button" rel="after"><img src="../assets/images/pagination-next-arrow.png" /></a>'
							+ '<a  class="pagination-button" rel="last"><img src="../assets/images/pagination-last-arrow.png" /></a>'
							+ '</div>');

	
	
	updateview();

	$(options.target).children().find(".pagination-button").bind("click",
			function() {

				if (!$(this).hasClass("disable")) {

					var rel = $(this).attr("rel");
					switch (rel) {
					case "first":
						first();
						break;
					case "brfore":
						previous();
						break;
					case "after":
						next();
						break;
					case "last":
						last();
						break;
					}

				} else {
				}

			});

	function previous() {

		if (options.currentpage > 1) {
			options.currentpage--;
			element.children().hide();
			element.children(".page" + options.currentpage).slideDown(200);

			updateview()

		}
	}

	function next() {
		if (options.currentpage < paggerCounter) {

			options.currentpage++;
			element.children().hide();
			element.children(".page" + options.currentpage).slideDown(200);

			updateview()

		}
	}

	function first() {
		options.currentpage = 1;
		element.children().hide();
		element.children(".page" + options.currentpage).slideDown(200);
		updateview()

	}

	function last() {
		options.currentpage = paggerCounter;
		element.children().hide();
		element.children(".page" + options.currentpage).slideDown(200);
		updateview()
	}

	function updateview() {
		
		var endcount = (options.currentpage * options.pagesize);
		if(endcount>totalCounter){
			
			endcount = totalCounter
		}
		
		if(totalCounter == 0){
			$(options.target).find(".table-data-summary").replaceWith(
					'<div class="table-data-summary">第 '+ ((options.currentpage - 1) * options.pagesize)+ ' - ' + endcount+ '条记录 / 共 ' + totalCounter + '条记录</div>');
		}else{
			$(options.target).find(".table-data-summary").replaceWith(
					'<div class="table-data-summary">第 '+ ((options.currentpage - 1) * options.pagesize + 1)+ ' - ' + endcount+ '条记录/ 共 ' + totalCounter + '条记录</div>');
		}
		$(options.target).children().find(".pagination-info").replaceWith('<span class="pagination-info">第'+options.currentpage+'页 / 共 ' + paggerCounter + '页 </span>');

		$(options.target).children().find(".pagination-button").each(
				function() {

					$(this).removeClass("disable");

					if (options.currentpage == 1) {

						var rel = $(this).attr("rel");
						switch (rel) {
							case "first":
								$(this).addClass("disable");
								break;
							case "brfore":
								$(this).addClass("disable");
								break;

						}
					}
					
					if(paggerCounter == 1){
						
						var rel = $(this).attr("rel");
						switch (rel) {
							case "first":
								$(this).addClass("disable");
								break;
							case "brfore":
								$(this).addClass("disable");
								break;
							case "after":
								$(this).addClass("disable");
								break;
							case "last":
								$(this).addClass("disable");
								break;

						}
					}
					
					if (options.currentpage == paggerCounter) {

						var rel = $(this).attr("rel");
						switch (rel) {
						case "after":
							$(this).addClass("disable");
							break;
						case "last":
							$(this).addClass("disable");
							break;

						}
					}
					
				});

	}

	return this;
}