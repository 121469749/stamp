1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
92
93
94
95
96
97
98
99
100
101
102
103
104
105
106
107
108
109
110
111
112
113
114
115
116
117
118
119
120
121
122
123
124
125
126
127
128
129
130
131
132
133
134
135
136
137
138
139
140
141
142
143
144
145
146
147
148
149
150
151
152
153
154
155
156
157
158
159
160
161
162
163
164
165
166
167
168
169
170
171
172
173
174
175
176
177
178
179
180
181
182
183
184
185
186
187
188
189
190
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/17
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>润城印章生产数量控制</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <style>
        th, td {
            text-align: center;
        }
        label{
            color: red;
            font-size: 14px;
        }
    </style>
    <script>
        $(document).ready(function () {
            <%--保存按钮点击事件--%>
            $("#save").click(function () {
                <%--按钮变为不可点击--%>
                $(this).attr("disabled", "disabled");
                $('#submitForm').ajaxSubmit({
                    dataType: 'json',
                    type: "post",
                    url: "${ctx}/dealer/countSet/rc/save",
                    success: function (result) {
                        console.log(result);
                        <%--提示信息，成功后跳转，失败后按钮重新变为可点击--%>
                        alert(result.message);
                        if (result.code == 200) {
                            window.location = "${ctx}/dealer/countSet/rc/list";
                        } else {
                            $("#save").removeAttr("disabled");
                        }

                    },
                    error: function () {
                        $("#save").removeAttr("disabled");
                        alert("出错了！请联系管理员！");
                    }
                });
            });

            <%--表单验证--%>
            $("#phy").blur(function () {
                var s = true;
                if($(this).val()==""){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class = 'p1'>印章数不能为空</label>");
                }else if($(this).val() < 0){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class = 'p2'>不能填负数</label>");
                }else if(parseInt($(this).val()) > 10000000){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class = 'p3'>数值不能大于一千万</label>");
                }else{
                    $(this).next().remove();
                }
                if($("#ele").val() < 0){
                    s = false;
                }else if(parseInt($('#ele').val()) > 10000000){
                    s = false;
                }
                if(s){
                    $("#save").removeAttr("disabled");
                }else{
                    $("#save").attr("disabled", "disabled");
                }
            });
            $("#ele").blur(function () {
                var s = true;
                if($(this).val()==""){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class='e1'>印章数不能为空</label>");
                }else if($(this).val() < 0){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class='e2'>不能填负数</label>");
                }else if(parseInt($(this).val()) > 10000000){
                    $("label").remove();
                    s = false;
                    $(this).after("<label class='e3'>数值不能大于一千万</label>");
                }else{
                    $(this).next().remove();
                }
                if($("#phy").val() < 0){
                    s = false;
                }else if(parseInt($('#phy').val()) > 10000000){
                    s = false;
                }
                if(s){
                    $("#save").removeAttr("disabled");
                }else{
                    $("#save").attr("disabled", "disabled");
                }
            });
        });

    </script>
</head>
<body style="width: 80%;margin: 25px auto">
<%--method="post" action="${ctx}/dealer/countSet/rc/save"--%>
<form:form id="submitForm" modelAttribute="agencyCompany" enctype="multipart/form-data">
    <table class="table-bordered table" style="width: 55%;margin: 0 auto">
        <tbody id="tbody">
        <tr class="table-tr-title">
            <th colspan="10">
                <b style="color:blue;font-size: 18px">${currentCompany.companyName}</b>
                正在对
                <b style="color: blue;font-size: 18px">${agencyCompany.companyName}(${agencyCompany.area.name})</b>
                进行印章生产控制设定
            </th>
            <!--收费公司-->
            <input type="hidden" name="id" value="${agencyCompany.id}">
            <!--缴费区域-->
            <input type="hidden" name="area.id" value="${agencyCompany.area.id}">
        </tr>
        <tr class="table-tr-title">
            <th colspan="10">
                    ${agencyCompany.companyName}(${agencyCompany.area.name})还剩有
                <c:if test="${agencyCompany.phyCountSet != null}">
                    ${agencyCompany.phyCountSet.count}
                </c:if>
                <c:if test="${agencyCompany.phyCountSet == null}">
                    0
                </c:if>
                (个)物理印章和
                <c:if test="${agencyCompany.eleCountSet != null}">
                    ${agencyCompany.eleCountSet.count}
                </c:if>
                <c:if test="${agencyCompany.eleCountSet == null} ">
                    0
                </c:if>
                (个)电子印章
            </th>
        </tr>
        <tr class="table-tr-title">
            <th colspan="10">印章生产控制设定</th>
        </tr>
        <tr>
            <th>印章类型</th>
            <th>分配数量(个)</th>
        </tr>
        <tr>
            <td>
                物理印章
                <form:hidden  value="PHYSTAMP" path="phyCountSet.stampShape"/>
                <form:hidden path="phyCountSet.id" />
            </td>
            <td>
                <form:input path="phyCountSet.count" id="phy" type="number" value="0" onfocus="this.value=''"></form:input>
            </td>
        </tr>
        <tr>
            <td>
                电子印章
                <form:hidden path="eleCountSet.stampShape" value="ELESTAMP"/>
                <form:hidden path="eleCountSet.id" />
            </td>
            <td>
                <form:input path="eleCountSet.count" id="ele" type="number" value="0" onfocus="this.value=''"></form:input>
            </td>
        </tr>
    </table>
    <div style="text-align: center;margin-top: 10px">
        <button id="save" type="button" class="btn btn-primary">保存</button>
        <button type="button" onclick="window.history.go(-1)" class="btn btn-primary">返 回</button>
    </div>
</form:form>
</body>
</html>
