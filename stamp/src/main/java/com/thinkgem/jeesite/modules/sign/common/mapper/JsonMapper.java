package com.thinkgem.jeesite.modules.sign.common.mapper; /**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 * @author ThinkGem
 * @version 2013-11-15
 */
public class JsonMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private static JsonMapper mapper;


    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static JsonMapper getInstance() {
        if (mapper == null){
            mapper = new JsonMapper().enableSimple();
        }
        return mapper;
    }


    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     *
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return this.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     * @see #createCollectionType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) this.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }





    /**
     * 允许单引号
     * 允许不带引号的字段名称
     */
    public JsonMapper enableSimple() {
        this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return this;
    }

    /**
     * 对象转换为JSON字符串
     * @param object
     * @return
     */
    public static String toJsonString(Object object){
        return JsonMapper.getInstance().toJson(object);
    }

    /**
     * JSON字符串转换为对象
     * @param jsonString
     * @param clazz
     * @return
     */
    public static Object fromJsonString(String jsonString, Class<?> clazz){
        return JsonMapper.getInstance().fromJson(jsonString, clazz);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 1);
        map.put("pId", -1);
        map.put("name", "根节点");
        list.add(map);
        map = Maps.newHashMap();
        map.put("id", 2);
        map.put("pId", 1);
        map.put("name", "你好");
        map.put("open", true);
        list.add(map);
        String json = JsonMapper.getInstance().toJson(list);
        System.out.println(json);
    }

}
