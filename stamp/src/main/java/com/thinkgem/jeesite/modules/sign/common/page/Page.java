package com.thinkgem.jeesite.modules.sign.common.page;

/**
 * Created by Locker on 2017/5/2.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 分页 插件
 *
 * @param <T>
 */
public class Page<T> {

    //总记录数
    private long totality;

    //当前页数
    private int pageNo;
    //页面大小 如果是-1 则说明不分页
    private int pageSize = 5;

    private long count;// 总记录数，设置为“-1”表示不查询总数
    //首页索引
    private int first;
    //尾页索引
    private int last;
    //下一页索引
    private int next;
    //上一页索引
    private int prev;

    //检测当前页是否是第一页
    private boolean firstPage;
    //检测当前页是否是最后一页
    private boolean lastPage;

    private int length = 8;// 显示页面长度
    private int slider = 1;// 前后显示页面长度

    //查询回来的对象列表
    private List<T> list = new ArrayList<T>();

    private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc

    private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。

    private String funcParam = ""; // 函数的附加参数，第三个参数值。

    private String message = ""; // 设置提示消息，显示在“共n条”之后

    //无参数构造方法 代表不需要分页
    public Page() {
        this.pageSize = -1;
    }

    /**
     *
     *  传入request 说明需要进行分页
     * 并且记录当前请求的页码
     * response  是用作设置cookie 暂时不去涉及cookies
     * @param request
     */
    public Page(HttpServletRequest request) {

        //设置page对象的想要请求的页面
        String wantPageNo = request.getParameter("pageNo");
        if (StringUtils.isNumeric(wantPageNo)) {
            this.setPageNo(Integer.parseInt(wantPageNo));
        }

        //设置page对象想要请求每页数目的大小
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isNumeric(pageSize)) {
            this.setPageSize(Integer.parseInt(pageSize));
        }

        //设置page对象的排序元素名
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotBlank(orderBy)) {
            this.orderBy = orderBy;
        }

    }

    /**
     * 构造方法
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     */
    public Page(int pageNo, int pageSize){
        this(pageNo,pageSize,0);
    }


    /**
     * 构造方法
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     * @param count 数据条数
     */
    public Page(int pageNo, int pageSize, long count) {
        this(pageNo, pageSize, count, new ArrayList<T>());
    }

    /**
     * 构造方法
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     * @param totality 数据条数
     * @param list 本页数据对象列表
     */
    public Page(int pageNo, int pageSize, long totality, List<T> list) {
        this.setTotality(totality);
        this.setPageNo(pageNo);
        setPageSize(pageSize);
        this.list = list;
    }

    /**
     * 初始化Page对象各类参数
     */
    public void initPageParameters() {

        //定义Page对象的基本信息 -begin

        //定义 首页页码
        this.first = 1;

        //定义 尾页页码 begin
        this.last = (int)(count / (this.pageSize < 1 ? 5 : this.pageSize) + first - 1);
        //总体除于每页记录数 会有剩余的话则 this.last 需要+1
        //又或者当前只有一页的话
        if (this.count % this.pageSize != 0 || this.last == 0) {
            this.last++;
        }
        //定义 尾页页码 end

        //防止 页码错乱
        if (this.last < this.first) {
            this.last = this.first;
        }

        //定义Page对象的基本信息 -end

        //对页码跳转的页码做判别

        //如果 当前页码 小于 1
        // 则 跳转到第一页
        //并更改 第一页的状态
        if (this.pageNo <= 1) {
            this.pageNo = this.first;
            this.firstPage = true;
        }

        //如果当前页码大于尾页页码
        // 则条状到最后一页
        //并更改尾页状态
        if (this.pageNo > this.last) {
            this.pageNo = this.last;
            this.lastPage = true;
        }


        //当前想要跳转的页面 小于尾页的话
        //更新其他next 的状态信息
        if (this.pageNo < this.last - 1) {
            this.next = this.pageNo + 1;
        } else {
            this.next = this.last;
        }

        //同样更新prev的信息
        if (this.pageNo > 1) {
            this.prev = this.pageNo - 1;
        } else {
            this.prev = this.first;
        }

        //考虑跳转的页数超出了范围
        //所以有如下两种操作
        // 如果当前页小于首页
        if (this.pageNo < this.first) {
            this.pageNo = this.first;
        }
        // 如果当前页大于尾页
        if (this.pageNo > this.last) {
            this.pageNo = this.last;
        }

    }

    public long getTotality() {
        return totality;
    }

    public void setTotality(long totality) {
        this.totality = totality;
        if (pageSize >= totality) {
            pageNo = 1;
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List<T> getList() {
        return list;
    }

    /**
     * 设置本页数据对象列表
     * @param list
     */
    public Page<T> setList(List<T> list) {
        this.list = list;
        initPageParameters();
        return this;
    }


    @JsonIgnore
    public String getOrderBy() {

        // SQL过滤，防止注入
        String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        if (sqlPattern.matcher(orderBy).find()) {
            return "";
        }
        return orderBy;

    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 默认输出当前分页标签
     * <div class="page">${page}</div>
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (pageNo == first) {// 如果是首页
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+prev+","+pageSize+",'"+funcParam+"');\">&#171; 上一页</a></li>\n");
        }

        int begin = pageNo - (length / 2);

        if (begin < first) {
            begin = first;
        }

        int end = begin + length - 1;

        if (end >= last) {
            end = last;
            begin = end - length + 1;
            if (begin < first) {
                begin = first;
            }
        }

        if (begin > first) {
            int i = 0;
            for (i = first; i < first + slider && i < begin; i++) {
                sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
                        + (i + 1 - first) + "</a></li>\n");
            }
            if (i < begin) {
                sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
            }
        }

        for (int i = begin; i <= end; i++) {
            if (i == pageNo) {
                sb.append("<li class=\"active\"><a href=\"javascript:\">" + (i + 1 - first)
                        + "</a></li>\n");
            } else {
                sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
                        + (i + 1 - first) + "</a></li>\n");
            }
        }

        if (last - end > slider) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
            end = last - slider;
        }

        for (int i = end + 1; i <= last; i++) {
            sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
                    + (i + 1 - first) + "</a></li>\n");
        }

        if (pageNo == last) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+next+","+pageSize+",'"+funcParam+"');\">"
                    + "下一页 &#187;</a></li>\n");
        }

        sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 第 ");
        sb.append("<input type=\"text\" value=\""+pageNo+" 页\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)");
        sb.append(funcName+"(this.value,"+pageSize+",'"+funcParam+"');\" onclick=\"this.select();\"/> , 一页 ");
        sb.append("<input type=\"text\" value=\""+pageSize+"\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)");
        sb.append(funcName+"("+pageNo+",this.value,'"+funcParam+"');\" onclick=\"this.select();\"/> 条，");
        sb.append("共 " + count + " 条"+(message!=null?message:"")+"</a></li>\n");

        sb.insert(0,"<ul class=\"pagination\" style=\"border: 0\">\n").append("</ul>\n");

        sb.append("<div style=\"clear:both;\"></div>");

//		sb.insert(0,"<div class=\"page\">\n").append("</div>\n");

        return sb.toString();
    }


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
