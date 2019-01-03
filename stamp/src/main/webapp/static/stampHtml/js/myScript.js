$(function () {
    //增删操作
    var item = $("#tbody tr:first").html();
    $(".add").click(function () {
        //最多只能备案五个印模
        if ($("#tbody tr").length >= 5)
            return;
        $("#tbody").append("<tr>" + item + "</tr>");
        var count = $("#tbody tr").length;
        $("#tbody tr:last th:eq(0)").addClass("remove").attr("title", "删除本项").append("<span class='fa fa-remove'></span>");
        $("#tbody tr:last th:eq(1)").html(count);
        count = Number(count) - 1;
        $("#tbody tr:last td:eq(0) select").attr("name", "stamps[" + count + "].stampType");
        $("#tbody tr:last td:eq(1) span:first input").attr("class", "chosestamp");
        $("#tbody tr:last td:eq(1) span:first input").attr("id", "stamps" + count + ".stampShape1");
        $("#tbody tr:last td:eq(1) span:first label").attr("for", "stamps" + count + ".stampShape1");
        $("#tbody tr:last td:eq(1) span:last input").attr("id", "stamps" + count + ".stampShape2");
        $("#tbody tr:last td:eq(1) span:last label").attr("for", "stamps" + count + ".stampShape2");
        $("#tbody tr:last td:eq(1) input").attr("name", "stamps[" + count + "].stampShape")/*attr("id","physical-seal"+count).attr("onclick","part(" + count + ",this" + ")")*/;
        $("#tbody tr:last td:last select").attr("name", "stamps[" + count + "].stampTexture").attr("id", "zhangcai" + count);
        count = Number(count) + 1;
        $(".remove").click(function () {
            $(this).parent("tr").remove();
            for (var i = 0; i < count; i++) {
                var j = i + 1;
                console.log(count + " ");
                $("#tbody tr:eq(" + i + ") th:eq(1)").html(j);
                $("#tbody tr:eq(" + i + ") td:eq(0) select").attr("name", "stamps[" + i + "].stampType");
                $("#tbody tr:last td:eq(1) span:first input").attr("class", "chosestamp");
                $("#tbody tr:eq(" + i + ") td:eq(1) span:first input").attr("id", "stamps" + i + ".stampShape1");
                $("#tbody tr:eq(" + i + ") td:eq(1) span:first label").attr("for", "stamps" + i + ".stampShape1");
                $("#tbody tr:eq(" + i + ") td:eq(1) span:last input").attr("id", "stamps" + i + ".stampShape2");
                $("#tbody tr:eq(" + i + ") td:eq(1) span:last label").attr("for", "stamps" + i + ".stampShape2");
                $("#tbody tr:eq(" + i + ") td:eq(1) input").attr("name", "stamps[" + i + "].stampShape")/*attr("id","physical-seal"+count).attr("onclick","part(" + count + ",this" + ")")*/;
                $("#tbody tr:eq(" + i + ") td:last select").attr("name", "stamps[" + i + "].stampTexture").attr("id", "zhangcai" + count);
            }
        });
    })
});

