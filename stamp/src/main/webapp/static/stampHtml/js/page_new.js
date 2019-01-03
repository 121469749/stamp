/**
 * 清除节点下的内容
 * @param nodeName
 */
function removeSonHtml(nodeName) {
    $(nodeName).empty()
}

/**
 * 清除节点
 * @param nodeName
 */
function removeHtml(nodeName) {
    $(nodeName).remove()
}

/**
 * 增加节点下的内容
 * @param nodeName
 * @param data
 */
function appendHtml(nodeName,data) {
    $(nodeName).append(data)
}

/**
 * 替换节点的内容
 * @param nodeName
 * @param data
 */
function replaceHtml(nodeName,data) {
    $(nodeName).replaceWith(data)
}

/**
 * 替换节点下的内容
 * @param nodeName
 * @param data
 */
function replaceSonHtml(nodeName,data) {
    $(nodeName).html(data)
}

/**
 * 居中
 * @param parentNodeName 父div
 * @param sonNodeName 子div
 */
function centerHtml(parentNodeName,sonNodeName) {

        $(sonNodeName).css({
            position: 'absolute',
            top: ($(parentNodeName).height() - $(sonNodeName).outerHeight())/2
        });

        $(parentNodeName).css({
            position: 'relative'
        })
}

function addLiHtml(nodeName,dataArray) {
    var data = '';
    for(var i = 0; i < dataArray.length; i++){
        data += "<li class=\"listContentSingle\" >" +
            "                                    <div class=\"listContentSingleCenter\">" +
            "                                        <div class=\"listContentSingleText changeHand\" id=\""+"li"+i+"\">" +
            "                                            <p class=\"listContentSingleUnChoose\">" +
            "                                                "+dataArray[i].liName+"" +
            "                                            </p>" +
            "                                        </div>" +
            "                                    </div>" +
            "                                </li>"
    }
    replaceSonHtml(nodeName,data)
}


function addSearchResultListHtml(nodeName,dataArray) {
    var data = '';
    for(var i = 0; i < dataArray.length; i++){
        data += "<div class=\"SearchResultBlock\">" +
            "                            <div class=\"SearchResultShow\">" +
            "                                <div class=\"SearchResultInfo\">" +
            "                                    <div class=\"SearchResultInfoSpan\">" +
            "                                        <p class=\"SearchResultInfoSpanTitle\">" +
            "                                            印章名称" +
            "                                        </p>" +
            "                                        <p class=\"SearchResultInfoSpanContent\">" +
            "                                            "+dataArray[i].stampName+"" +
            "                                        </p>" +
            "                                    </div>" +
            "                                    <div class=\"SearchResultInfoSpan\">" +
            "                                        <p class=\"SearchResultInfoSpanTitle\">" +
            "                                            印章编码" +
            "                                        </p>" +
            "                                        <p class=\"SearchResultInfoSpanContent\">" +
            "                                            "+dataArray[i].stampCode+"" +
            "                                        </p>" +
            "                                    </div>" +
            "                                    <div class=\"SearchResultInfoSpan\">" +
            "                                        <p class=\"SearchResultInfoSpanTitle\">" +
            "                                            印模类型" +
            "                                        </p>" +
            "                                        <p class=\"SearchResultInfoSpanContent\">" +
            "                                            "+searchSealType(dataArray[i].stampShape)+"" +
            "                                        </p>" +
            "                                    </div>" +
            "                                    <div class=\"SearchResultInfoSpan\">" +
            "                                        <p class=\"SearchResultInfoSpanTitle\">" +
            "                                            公司名称" +
            "                                        </p>" +
            "                                        <p class=\"SearchResultInfoSpanContent\">" +
            "                                            "+dataArray[i].useComp.companyName+"" +
            "                                        </p>" +
            "                                    </div>" +
            "                                    <div class=\"SearchResultInfoSpan\">" +
            "                                        <p class=\"SearchResultInfoSpanTitle\">" +
            "                                            法人名字" +
            "                                        </p>" +
            "                                        <p class=\"SearchResultInfoSpanContent\">" +
            "                                            "+dataArray[i].useComp.legalName+"" +
            "                                        </p>" +
            "                                    </div>" +
            "                                </div>" +
            "                                <div class=\"SearchResultImage\">" +
            "                                    <img class=\"SearchResultImageShow\" src=\""+searchSealImage('2',dataArray[i])+"\">" +
            "                                </div>" +
            "                            </div>" +
            "                            <div class=\"SearchBlockSplitLine\">" +
            "                            </div>" +
            "                        </div>"
    }
    replaceSonHtml(nodeName,data)
}

/**
 * 识别印模
 * @param type
 * @returns {*}
 */
function searchSealType(type) {
    switch (type){
        case '1':
            return '物理印章'
            break
        case '2':
            return '电子印章';
            break
        default:
            return'';
    }
}

/**
 * 取印模图片
 * @param type
 * @returns {*}
 */
function searchSealImage(type,data) {
    var path
    switch (type){
        case '1':
            path = data.phyModel
            break
        case '2':
            path = data.eleModel
            break
        default:
            break;
    }
    return '/img'+ path
}

/**
 *  表单验证错误提示
 * @param nodeName
 * @param data
 */

function errorLoginHtml(nodeName,data) {
    var html = "<div id=\"errorLogin\" class=\"errorLogin\">"+data+"</div>"
    replaceSonHtml(nodeName,html)
}