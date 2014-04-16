$(document).ready(function () {
    var close = true;
    var timerId = null;

    $(".main_menu_item").mouseover(function () {
        var uid = $(this).attr("id").split("_")[1];
        var sdata = [];

        if (uid) {
            $.ajax({
                async: false,
                url: "/categories-json/" + uid,
                dataType: "json",
                success: function (data, textStatus) {
                    console.log(data);
                    sdata = data;
                }
            });
        }

        $("#sm_popup").empty();
        if (sdata.length) {
            $.each(sdata, function (i, val) {
                $("#sm_popup").append("<div class='sm_popup_link'><a href='/categories/" + val.url + "'>" + val.name + "</a></div>");
            });

            $("#sm_popup").show();
            close = false;
        } else {
            $("#sm_popup").hide();
            close = true;
        }

    });

    $("#sm_popup").mouseenter(function () {
        close = false;
    });

    $(".main_menu_item").mouseout(function () {
        close = true;
        clearTimeout(timerId);
        timerId = setTimeout(function () {
            if (close)
                $("#sm_popup").hide();
        }, 333);
    });

    $("#sm_popup").mouseleave(function () {
        $("#sm_popup").hide();
    });


    //***********************
    $("#main_menu_head").click(function (e) {
        var _this = $(this);
        if (!document.menuIsOpen) {
            _this.removeClass("bottom_radius");
        }

        $("#main_menu").slideToggle("slow", function () {
            // Animation complete.
            if (document.menuIsOpen) {
                _this.addClass("bottom_radius");
            }
            document.menuIsOpen = !document.menuIsOpen;
        });
    });
});