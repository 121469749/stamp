<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="google" content="notranslate">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>签章准备页面</title>
    <link rel="stylesheet" href="${ctxStatic}/sign/pdfjs/web/viewer.css?id=2">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/sign/css/xcConfirm.css?id=13"/>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/sign/js/xcConfirm.js?id=20" charset="utf-8"></script>
    <link rel="resource" type="application/l10n" href="locale/locale.properties">
    <script>
        if (${document.fileSinaturePath ne '' && document.fileSinaturePath ne null}){
            var DEFAULT_URL = '/img/sign/${document.fileSinaturePath}';
        } else {
            var DEFAULT_URL = '/img/sign/${document.fileOrgPath}';
        }

        var pdfWorkerURL = '${ctxStatic}/sign/pdfjs/build/pdf.worker.js'

        var isLogin = false;//是否登录
        //当离开当前页面或者刷新时退出登录，释放资源
        window.onbeforeunload = function(){
            //关闭预签章modal
            //window.parent.closeModal();

            if (isLogin){
                var objSeal = document.getElementById("SignerCtrl");
                try{
                    objSeal.SOF_Logout();
//                    alert("退出成功");
                }catch(e){
//                    alert("退出登录失败，原因："+ e);
                }
            }
        };
    </script>
    <script src="${ctxStatic}/sign/pdfjs/web/l10n.js?id=2"></script>
    <script src="${ctxStatic}/sign/pdfjs/build/pdf.js?id=2"></script>
    <script src="${ctxStatic}/sign/pdfjs/web/viewer.js?id=2"></script>

    <style type="text/css">
        .btn-sign{
            width: 40%;
            padding: 4px 8px;
            font-size: 20px;
            background-color: #6b6b6b;
            color: #fff;
            border-radius: 3px;
            font-weight: bold;
            border: none;
            cursor: pointer;
        }
        .sealImg{
            height: 100px;
            float: left;
            margin-right: 10px;
        }
        .sealname{
            line-height: 100px;
            font-size: 20px;
            float: left;
            overflow:hidden;
            text-overflow:ellipsis;
            white-space:nowrap;
            color: #000;
        }
        .black_overlay{
            display: none;
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: #666666;
            z-index:1001;
            -moz-opacity: 0.8;
            opacity:.80;
            filter: alpha(opacity=50);
        }
        .seal_list_box {display: none;position: fixed; left: 50%; top: 20%; background-color: #ffffff; z-index: 2147000001; width: 570px; height: auto; margin-left: -285px; margin-top: -150px; border-radius: 5px; font-weight: bold; color: #535e66;}
        .seal_list_box .ttBox{height: 30px; line-height: 30px; padding: 14px 30px; border-bottom: solid 1px #eef0f1;}
        .seal_list_box .ttBox .tt{font-size: 18px; display: block; float: left; height: 30px; position: relative;}
        .seal_list_box .ttBox .clsBtn{display: block; cursor: pointer; width: 12px; height: 12px; position: absolute; top: 22px; right: 30px; color: #91928e;}
        .seal_list_box .txtBox{margin: 10px 50px 20px 50px;border-bottom: solid 1px #eef0f1;cursor: pointer;}
        .seal_list_box .txtBox:active{background: #D1EEEE; }
        .seal_list_box .txtBox .stampName{position: relative; left: 8%; bottom: 25px;font-size: 20px;}
        .seal_list_box .txtBox p{height: 84px; margin-top: 16px; font-size: 15px; line-height: 26px; overflow-x: hidden; overflow-y: auto;}
        .seal_list_box .txtBox p input{width: 364px; height: 30px; border: solid 1px #eef0f1; font-size: 18px; margin-top: 6px;}
        .seal_list_box .txtBox p input:focus{border: 1px solid #91928e;box-shadow: 0 0 5px #91928e;outline: none;}
    </style>
</head>

<body tabindex="1" class="loadingInProgress">
<div id="outerContainer">

    <div id="sidebarContainer">
        <div id="toolbarSidebar">
            <div class="splitToolbarButton toggled">
                <button id="viewThumbnail" class="toolbarButton toggled" title="Show Thumbnails" tabindex="2"
                        data-l10n-id="thumbs">
                    <span data-l10n-id="thumbs_label">Thumbnails</span>
                </button>
                <button id="viewOutline" class="toolbarButton"
                        title="Show Document Outline (double-click to expand/collapse all items)" tabindex="3"
                        data-l10n-id="document_outline">
                    <span data-l10n-id="document_outline_label">Document Outline</span>
                </button>
                <button id="viewAttachments" class="toolbarButton" title="Show Attachments" tabindex="4"
                        data-l10n-id="attachments">
                    <span data-l10n-id="attachments_label">Attachments</span>
                </button>
            </div>
        </div>
        <div id="sidebarContent">
            <div id="thumbnailView">
            </div>
            <div id="outlineView" class="hidden">
            </div>
            <div id="attachmentsView" class="hidden">
            </div>
        </div>
    </div>  <!-- sidebarContainer -->

    <div id="mainContainer">
        <div class="findbar hidden doorHanger" id="findbar">
            <div id="findbarInputContainer">
                <input id="findInput" class="toolbarField" title="Find" placeholder="Find in document…" tabindex="91"
                       data-l10n-id="find_input">
                <div class="splitToolbarButton">
                    <button id="findPrevious" class="toolbarButton findPrevious"
                            title="Find the previous occurrence of the phrase" tabindex="92"
                            data-l10n-id="find_previous">
                        <span data-l10n-id="find_previous_label">Previous</span>
                    </button>
                    <div class="splitToolbarButtonSeparator"></div>
                    <button id="findNext" class="toolbarButton findNext" title="Find the next occurrence of the phrase"
                            tabindex="93" data-l10n-id="find_next">
                        <span data-l10n-id="find_next_label">Next</span>
                    </button>
                </div>
            </div>

            <div id="findbarOptionsContainer">
                <input type="checkbox" id="findHighlightAll" class="toolbarField" tabindex="94">
                <label for="findHighlightAll" class="toolbarLabel" data-l10n-id="find_highlight">Highlight all</label>
                <input type="checkbox" id="findMatchCase" class="toolbarField" tabindex="95">
                <label for="findMatchCase" class="toolbarLabel" data-l10n-id="find_match_case_label">Match case</label>
                <span id="findResultsCount" class="toolbarLabel hidden"></span>
            </div>

            <div id="findbarMessageContainer">
                <span id="findMsg" class="toolbarLabel"></span>
            </div>
        </div>  <!-- findbar -->

        <div id="secondaryToolbar" class="secondaryToolbar hidden doorHangerRight">
            <div id="secondaryToolbarButtonContainer">
                <button id="secondaryPresentationMode" class="secondaryToolbarButton presentationMode visibleLargeView"
                        title="Switch to Presentation Mode" tabindex="51" data-l10n-id="presentation_mode">
                    <span data-l10n-id="presentation_mode_label">Presentation Mode</span>
                </button>

                <button id="secondaryOpenFile" class="secondaryToolbarButton openFile visibleLargeView"
                        title="Open File" tabindex="52" data-l10n-id="open_file">
                    <span data-l10n-id="open_file_label">Open</span>
                </button>

                <button id="secondaryPrint" class="secondaryToolbarButton print visibleMediumView" title="Print"
                        tabindex="53" data-l10n-id="print">
                    <span data-l10n-id="print_label">Print</span>
                </button>

                <button id="secondaryDownload" class="secondaryToolbarButton download visibleMediumView"
                        title="Download" tabindex="54" data-l10n-id="download">
                    <span data-l10n-id="download_label">Download</span>
                </button>

                <a href="#" id="secondaryViewBookmark" class="secondaryToolbarButton bookmark visibleSmallView"
                   title="Current view (copy or open in new window)" tabindex="55" data-l10n-id="bookmark">
                    <span data-l10n-id="bookmark_label">Current View</span>
                </a>

                <div class="horizontalToolbarSeparator visibleLargeView"></div>

                <button id="firstPage" class="secondaryToolbarButton firstPage" title="Go to First Page" tabindex="56"
                        data-l10n-id="first_page">
                    <span data-l10n-id="first_page_label">Go to First Page</span>
                </button>
                <button id="lastPage" class="secondaryToolbarButton lastPage" title="Go to Last Page" tabindex="57"
                        data-l10n-id="last_page">
                    <span data-l10n-id="last_page_label">Go to Last Page</span>
                </button>

                <div class="horizontalToolbarSeparator"></div>

                <button id="pageRotateCw" class="secondaryToolbarButton rotateCw" title="Rotate Clockwise" tabindex="58"
                        data-l10n-id="page_rotate_cw">
                    <span data-l10n-id="page_rotate_cw_label">Rotate Clockwise</span>
                </button>
                <button id="pageRotateCcw" class="secondaryToolbarButton rotateCcw" title="Rotate Counterclockwise"
                        tabindex="59" data-l10n-id="page_rotate_ccw">
                    <span data-l10n-id="page_rotate_ccw_label">Rotate Counterclockwise</span>
                </button>

                <div class="horizontalToolbarSeparator"></div>

                <button id="toggleHandTool" class="secondaryToolbarButton handTool" title="Enable hand tool"
                        tabindex="60" data-l10n-id="hand_tool_enable">
                    <span data-l10n-id="hand_tool_enable_label">Enable hand tool</span>
                </button>

                <div class="horizontalToolbarSeparator"></div>

                <button id="documentProperties" class="secondaryToolbarButton documentProperties"
                        title="Document Properties…" tabindex="61" data-l10n-id="document_properties">
                    <span data-l10n-id="document_properties_label">Document Properties…</span>
                </button>
            </div>
        </div>  <!-- secondaryToolbar -->

        <div class="toolbar">
            <div id="toolbarContainer">
                <div id="toolbarViewer">
                    <div id="toolbarViewerLeft">
                        <button id="sidebarToggle" class="toolbarButton" title="Toggle Sidebar" tabindex="11"
                                data-l10n-id="toggle_sidebar">
                            <span data-l10n-id="toggle_sidebar_label">Toggle Sidebar</span>
                        </button>
                        <div class="toolbarButtonSpacer"></div>
                        <button id="viewFind" style="display: none" class="toolbarButton" title="Find in Document"
                                tabindex="12" data-l10n-id="findbar">
                            <span data-l10n-id="findbar_label">Find</span>
                        </button>
                        <div class="splitToolbarButton hiddenSmallView">
                            <button class="toolbarButton pageUp" title="Previous Page" id="previous" tabindex="13"
                                    data-l10n-id="previous">
                                <span data-l10n-id="previous_label">Previous</span>
                            </button>
                            <div class="splitToolbarButtonSeparator"></div>
                            <button class="toolbarButton pageDown" title="Next Page" id="next" tabindex="14"
                                    data-l10n-id="next">
                                <span data-l10n-id="next_label">Next</span>
                            </button>
                        </div>
                        <input type="number" id="pageNumber" class="toolbarField pageNumber" title="Page" value="1"
                               size="4" min="1" tabindex="15" data-l10n-id="page">
                        <span id="numPages" class="toolbarLabel"></span>
                    </div>
                    <div id="toolbarViewerRight">
                        <button id="presentationMode" class="toolbarButton presentationMode hiddenLargeView"
                                title="Switch to Presentation Mode" tabindex="31" data-l10n-id="presentation_mode">
                            <span data-l10n-id="presentation_mode_label">Presentation Mode</span>
                        </button>

                        <button id="openFile" style="display: none" class="toolbarButton openFile hiddenLargeView"
                                title="Open File" tabindex="32" data-l10n-id="open_file">
                            <span data-l10n-id="open_file_label">Open</span>
                        </button>

                        <button id="print" class="toolbarButton print hiddenMediumView" title="Print" tabindex="33"
                                data-l10n-id="print">
                            <span data-l10n-id="print_label">Print</span>
                        </button>

                        <button id="download" class="toolbarButton download hiddenMediumView" title="Download"
                                tabindex="34" data-l10n-id="download">
                            <span data-l10n-id="download_label">Download</span>
                        </button>
                        <a href="#" id="viewBookmark" style="display: none"
                           class="toolbarButton bookmark hiddenSmallView"
                           title="Current view (copy or open in new window)" tabindex="35" data-l10n-id="bookmark">
                            <span data-l10n-id="bookmark_label">Current View</span>
                        </a>

                        <div class="verticalToolbarSeparator hiddenSmallView"></div>

                        <button id="secondaryToolbarToggle" class="toolbarButton" title="Tools" tabindex="36"
                                data-l10n-id="tools">
                            <span data-l10n-id="tools_label">Tools</span>
                        </button>
                    </div>
                    <div id="toolbarViewerMiddle">
                        <div class="splitToolbarButton">
                            <button id="zoomOut" class="toolbarButton zoomOut" title="Zoom Out" tabindex="21"
                                    data-l10n-id="zoom_out">
                                <span data-l10n-id="zoom_out_label">Zoom Out</span>
                            </button>
                            <div class="splitToolbarButtonSeparator"></div>
                            <button id="zoomIn" class="toolbarButton zoomIn" title="Zoom In" tabindex="22"
                                    data-l10n-id="zoom_in">
                                <span data-l10n-id="zoom_in_label">Zoom In</span>
                            </button>
                        </div>
                        <span id="scaleSelectContainer" class="dropdownToolbarButton">
                  <select id="scaleSelect" title="Zoom" tabindex="23" data-l10n-id="zoom">
                    <option id="pageAutoOption" title="" value="auto" selected="selected"
                            data-l10n-id="page_scale_auto">Automatic Zoom</option>
                    <option id="pageActualOption" title="" value="page-actual" data-l10n-id="page_scale_actual">Actual Size</option>
                    <option id="pageFitOption" title="" value="page-fit" data-l10n-id="page_scale_fit">Fit Page</option>
                    <option id="pageWidthOption" title="" value="page-width"
                            data-l10n-id="page_scale_width">Full Width</option>
                    <option id="customScaleOption" title="" value="custom" disabled="disabled" hidden="true"></option>
                    <option title="" value="0.5" data-l10n-id="page_scale_percent"
                            data-l10n-args='{ "scale": 50 }'>50%</option>
                    <option title="" value="0.75" data-l10n-id="page_scale_percent"
                            data-l10n-args='{ "scale": 75 }'>75%</option>
                    <option title="" value="1" data-l10n-id="page_scale_percent"
                            data-l10n-args='{ "scale": 100 }'>100%</option>
                    <option title="" value="1.25" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 125 }'>125%</option>
                    <option title="" value="1.5" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 150 }'>150%</option>
                    <option title="" value="2" data-l10n-id="page_scale_percent"
                            data-l10n-args='{ "scale": 200 }'>200%</option>
                    <option title="" value="3" data-l10n-id="page_scale_percent"
                            data-l10n-args='{ "scale": 300 }'>300%</option>
                    <option title="" value="4" data-l10n-id="page_scale_percent"
                            data-l10n-args='{ "scale": 400 }'>400%</option>
                  </select>
                </span>
                    </div>
                </div>
                <div id="loadingBar">
                    <div class="progress">
                        <div class="glimmer">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <menu type="context" id="viewerContextMenu">
            <menuitem id="contextFirstPage" label="First Page"
                      data-l10n-id="first_page"></menuitem>
            <menuitem id="contextLastPage" label="Last Page"
                      data-l10n-id="last_page"></menuitem>
            <menuitem id="contextPageRotateCw" label="Rotate Clockwise"
                      data-l10n-id="page_rotate_cw"></menuitem>
            <menuitem id="contextPageRotateCcw" label="Rotate Counter-Clockwise"
                      data-l10n-id="page_rotate_ccw"></menuitem>
        </menu>

        <div id="viewerContainer" tabindex="0">
            <div id="viewer" class="pdfViewer"></div>
        </div>

        <div id="errorWrapper" hidden='true'>
            <div id="errorMessageLeft">
                <span id="errorMessage"></span>
                <button id="errorShowMore" data-l10n-id="error_more_info">
                    More Information
                </button>
                <button id="errorShowLess" data-l10n-id="error_less_info" hidden='true'>
                    Less Information
                </button>
            </div>
            <div id="errorMessageRight">
                <button id="errorClose" data-l10n-id="error_close">
                    Close
                </button>
            </div>
            <div class="clearBoth"></div>
            <textarea id="errorMoreInfo" hidden='true' readonly="readonly"></textarea>
        </div>
    </div> <!-- mainContainer -->

    <div id="overlayContainer" class="hidden">
        <div id="passwordOverlay" class="container hidden">
            <div class="dialog">
                <div class="row">
                    <p id="passwordText" data-l10n-id="password_label">Enter the password to open this PDF file:</p>
                </div>
                <div class="row">
                    <!-- The type="password" attribute is set via script, to prevent warnings in Firefox for all http:// documents. -->
                    <input id="password" class="toolbarField">
                </div>
                <div class="buttonRow">
                    <button id="passwordCancel" class="overlayButton"><span data-l10n-id="password_cancel">Cancel</span>
                    </button>
                    <button id="passwordSubmit" class="overlayButton"><span data-l10n-id="password_ok">OK</span>
                    </button>
                </div>
            </div>
        </div>
        <div id="documentPropertiesOverlay" class="container hidden">
            <div class="dialog">
                <div class="row">
                    <span data-l10n-id="document_properties_file_name">File name:</span>
                    <p id="fileNameField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_file_size">File size:</span>
                    <p id="fileSizeField">-</p>
                </div>
                <div class="separator"></div>
                <div class="row">
                    <span data-l10n-id="document_properties_title">Title:</span>
                    <p id="titleField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_author">Author:</span>
                    <p id="authorField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_subject">Subject:</span>
                    <p id="subjectField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_keywords">Keywords:</span>
                    <p id="keywordsField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_creation_date">Creation Date:</span>
                    <p id="creationDateField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_modification_date">Modification Date:</span>
                    <p id="modificationDateField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_creator">Creator:</span>
                    <p id="creatorField">-</p>
                </div>
                <div class="separator"></div>
                <div class="row">
                    <span data-l10n-id="document_properties_producer">PDF Producer:</span>
                    <p id="producerField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_version">PDF Version:</span>
                    <p id="versionField">-</p>
                </div>
                <div class="row">
                    <span data-l10n-id="document_properties_page_count">Page Count:</span>
                    <p id="pageCountField">-</p>
                </div>
                <div class="buttonRow">
                    <button id="documentPropertiesClose" class="overlayButton"><span
                            data-l10n-id="document_properties_close">Close</span></button>
                </div>
            </div>
        </div>
        <div id="printServiceOverlay" class="container hidden">
            <div class="dialog">
                <div class="row">
                    <span data-l10n-id="print_progress_message">Preparing document for printing…</span>
                </div>
                <div class="row">
                    <progress value="0" max="100"></progress>
                    <span data-l10n-id="print_progress_percent" data-l10n-args='{ "progress": 0 }'
                          class="relative-progress">0%</span>
                </div>
                <div class="buttonRow">
                    <button id="printCancel" class="overlayButton"><span
                            data-l10n-id="print_progress_close">Cancel</span></button>
                </div>
            </div>
        </div>
    </div>  <!-- overlayContainer -->

    <%--签章按钮--%>
    <div style="text-align: center;position: fixed;bottom: 55px;left: calc((100% - 100px)/2);z-index: 999">
        <button id="toSign" class="btn-sign" style="width: 120px;z-index: 999;
        background-color: #FF4500">
            签章
        </button>
        <button id="wait-btn" class="btn-sign" type="button"
                style="width: 200px;z-index: 999;display:none;background-color: #FF4500">
            <img width="18px" height="" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif">
            &nbsp;签署中,请稍后...
        </button>
    </div>
    <%--控件参数--%>
    <div style="display: none">
        <OBJECT CLASSID="CLSID:1F4A7405-5BAD-49CF-90B8-9B5A276154B5"
                ID="SignerCtrl" STYLE="LEFT:0;TOP:0" width="0" height="0">
        </OBJECT>
    </div>
    <%--签章人参数--%>
    <input type="hidden" id="signName" value=""/>
    <input type="hidden" id="signPwd" value=""/>
    <!-- 模态框（查看印模列表） -->
    <div id="fade" class="black_overlay"></div>
    <div id="MyDiv" class="seal_list_box">
        <div class="ttBox">
            <a class="clsBtn" onclick="CloseDiv('MyDiv','fade')">X</a>
            <span class="tt">请选择电子印章：</span>
        </div>
        <%--<div class="txtBox">
            <img src="${ctxStatic}/images/testseal.png"><span class="stampName">公章</span><br>
        </div>--%>
    </div>


</div> <!-- outerContainer -->
<div id="printContainer"></div>
<script type="text/javascript">

    $(function () {
        $("#toSign").click(function(){
            //签章前判断是否登录，已登录的话要先退出再登录
            if (isLogin){
                var objSeal = document.getElementById("SignerCtrl");
                try{
                    objSeal.SOF_Logout();
//                    alert("退出成功");
                }catch(e){
//                    alert("退出登录失败，原因："+ e);
                }
            }

            var txt=  "请输入Ukey的密码：";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.input,{
                onOk:function(v){
                    // 获取底层控件
                    var objSeal = document.getElementById("SignerCtrl");
                    // 登录Ukey
                    var signName = window.parent.userId;//证书名称
                    var signPwd = v;//Ukey密码

                    var ary2,ary3 = "";
                    try{
                        objSeal.SOF_Login(signName,signPwd); //登录
                        isLogin = true;

                        var txt= "登录成功";
                        window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.success,{
                            onOk:function(){

                                ShowDiv('MyDiv','fade');

                                $("#signName").attr("value",signName);
                                $("#signPwd").attr("value",signPwd);

                                //获取印章列表
                                var seals = objSeal.GetSealList();
                                var ary  = seals.split(";");
                                for(var i = 0;i < ary.length-1;i++){
                                    ary2 = ary[i].split(",");
                                    ary3 += ary2[1] + ",";
                                }

                                $.ajax({
                                    url:"${ctx}/seal/list?id=" + ary3,
                                    type:"POST",
                                    dataType:"json",
                                    success:function(result){
                                        //重复登录移除之前的缓存
                                        $(".txtBox").html("");
                                        $(".txtBox").removeClass();

                                        for(var i = 0;i < result.length;i++){
                                            var a = "location.href='${ctx}/singal/real/singatrue?userName="+ $('#signName').val() +"&password="+ $('#signPwd').val() +
                                                "&document.id=${document.id}&seal.id=" + result[i].id +"'";
                                            $("#MyDiv").append("<div class='txtBox' onclick=\"" + a + "\"><img src='/img" + result[i].eleModel +
                                                "'><span class='stampName'>" + result[i].stampName + "</span></div>");
                                        }
                                    }
                                })
                            }
                        });


                    } catch(e){
                        // 输出出错信息
                        alert(e+"登录失败！请确认已插入Ukey或者已输入正确的密码");
                    }
                }
            });
        });

        $("#toTest").click(function () {
            alert(222);
window.parent.closeModal();

        })
        

    });

    //弹出隐藏层
    function ShowDiv(show_div,bg_div){
        document.getElementById(show_div).style.display='block';
        document.getElementById(bg_div).style.display='block' ;
        var bgdiv = document.getElementById(bg_div);
        bgdiv.style.width = document.body.scrollWidth;
// bgdiv.style.height = $(document).height();
        $("#"+bg_div).height($(document).height());
        $('#imgQRcode').attr("src","${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif");

    };
    //关闭弹出层
    function CloseDiv(show_div,bg_div)
    {
        document.getElementById(show_div).style.display='none';
        document.getElementById(bg_div).style.display='none';
    };

</script>
</body>
</html>

