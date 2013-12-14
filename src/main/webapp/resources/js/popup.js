$(document)
		.ready(
				function() {
					$('#thumbs_image img')
							.click(
									function(e) {
										var imgId = $(this).attr("itemid");
										$('#view_image img')
												.attr(
														{
															src : "resources/img/jqzoom/imgProd/triumph_small"
																	+ imgId
																	+ ".jpg",
															title : "triumph"
																	+ imgId
														});
									});

					$('#view_image img').click(function(e) {
						var winH = screen.height; // $(window).height();
						var winW = $(window).width();

						console.log("winH", winH);

						var top = winH / 2 - $("#dialog").height() / 2;
						var left = winW / 2 - $("#dialog").width() / 2;

						if (0 > top)
							top = 0;
						if (0 > left)
							left = 0;

						$("#dialog").css('top', top);
						$("#dialog").css('left', left);

						$("#dialog, #block_light").show();
					});

					$('#dialog_close, #block_light').click(function(e) {
						$("#dialog, #block_light").hide();
					});

				});