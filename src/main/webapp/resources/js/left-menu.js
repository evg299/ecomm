$(document).ready(function() {
	var close = true;
	var timerId = null;

	$(".main_menu_item").mouseover(function() {
		$("#sm_popup").empty();
		var uid = $(this).attr("id").split("_")[1];

		$("#sm_popup").append("Синхронная загрузка №" + uid);
		$("#sm_popup").show();
		close = false;
	});

	$("#sm_popup").mouseover(function() {
		close = false;
	});

	$(".main_menu_item").mouseout(function() {
		close = true;
		clearTimeout(timerId);
		timerId = setTimeout(function() {
			if (close)
				$("#sm_popup").hide();
		}, 333);
	});

	$("#sm_popup").mouseout(function() {
		$("#sm_popup").hide();
	});
	
	var isOpen = false;
	$("#main_menu_head").click(function(e) {
		var _this = $(this);
		if (!isOpen) {
			_this.removeClass("bottom_radius");
		}

		$("#main_menu").slideToggle("slow", function() {
			// Animation complete.
			if (isOpen) {
				_this.addClass("bottom_radius");
			}
			isOpen = !isOpen;
		});
	});
});