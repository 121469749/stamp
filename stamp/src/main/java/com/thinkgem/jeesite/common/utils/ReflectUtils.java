package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.modules.log.dao.ModifyInfoLogDao;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import com.thinkgem.jeesite.modules.log.service.ModifyInfoLogService;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by xucaikai on 2018\8\1 0001.
 */
public class ReflectUtils {
    /**
     * Description: 获取修改内容
     */



    private static ModifyInfoLogDao modifyInfoLogDao = SpringContextHolder.getBean(ModifyInfoLogDao.class);
    private static StampDao stampDao = SpringContextHolder.getBean(StampDao.class);
    private static ModifyInfoLogService modifyInfoLogService = SpringContextHolder.getBean(ModifyInfoLogService.class);
    private static AttachmentService attachmentService = SpringContextHolder.getBean(AttachmentService.class);

    /**
     * @author 许彩开
     * @TODO (注：修改“信息”日志保存)
     * @param source
     * @DATE: 2018\8\8 0008 9:28
     */
    public static void packageModifyContent(Object source, Object target,StampRecord stampRecord,String tableName) {

        if(null == source || null == target) {
            return ;
        }
        //取出source类
        Class<?> sourceClass = source.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        for(Field srcField : sourceFields) {
            String srcName = srcField.getName();
            //获取srcField值
            String srcValue = getFieldValue(source, srcName) == null ? "" : getFieldValue(source, srcName).toString();
            //获取对应的targetField值
            String targetValue = getFieldValue(target, srcName) == null ? "" : getFieldValue(target, srcName).toString();
            if(StringUtils.isEmpty(srcValue) || StringUtils.isEmpty(targetValue)) {  //注意：此处判断为或，当其中一个为空，则不比较
                continue;
            }
            if(!srcValue.equals(targetValue)) {
                //从数据库查出srcName 的字段注释
                System.out.println(tableName);
                //默认：数据库定义的字段名和实体类的属性名一致
                String columnCommnet = modifyInfoLogService.findColComment(tableName,srcName,modifyInfoLogService.returnDbName());
                //第二种：数据库字段名类似为：legal_certCode
                if("".equals(columnCommnet)||columnCommnet==null){
                    columnCommnet = modifyInfoLogService.findColComment(tableName,getString(srcName),modifyInfoLogService.returnDbName());
                    if("".equals(columnCommnet)||columnCommnet==null){
                        //第三种：数据库字段名类似为：legal_cert_code
                        columnCommnet = modifyInfoLogService.findColComment(tableName,changString(srcName),modifyInfoLogService.returnDbName());
                    }
                }
                ModifyInfoLog tLogDetail = new ModifyInfoLog();
                if(stampRecord.getStamp().getStampState().getKey().equals("3")){//值为3是 是已制作 否则是待刻

                    tLogDetail.setBusinessName("印章办理-已制作印章-修改信息");

                }else{
                    tLogDetail.setBusinessName("印章办理-待刻印章-修改信息");
                }

                tLogDetail.setColumnName(srcName);
                tLogDetail.setOperationType("修改/更新");
                tLogDetail.setTableName(modifyInfoLogDao.findTableComment(tableName,modifyInfoLogService.returnDbName()));
                tLogDetail.setColumnText(columnCommnet);
                tLogDetail.setOldValue(targetValue);
                tLogDetail.setType("modifyCompanyInfo_before");
                tLogDetail.setNewValue(srcValue);
                tLogDetail.setCompanyName(stampRecord.getUseComp().getId());
                tLogDetail.setMakeCom(stampRecord.getMakeComp().getId());

                tLogDetail.preInsert();

                modifyInfoLogDao.insert(tLogDetail);

//                detailId.append(LogDetail.getId()+",");

//                System.out.println(srcName + "由‘" + targetValue + "’修改为‘" + srcValue + "’;");
            }

        }
    }

    public static void modifyInfoRecord(Object source, Object target ,ModifyInfoLog modifyInfoLog) {
        if(null == source || null == target) {
            return ;
        }
        //取出source类
        String tableName = modifyInfoLog.getTableName();
        Class<?> sourceClass = source.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();
        for(Field srcField : sourceFields) {
            String srcName = srcField.getName();
            String srcValue = getFieldValue(source, srcName) == null ? "" : getFieldValue(source, srcName).toString();
            String targetValue = getFieldValue(target, srcName) == null ? "" : getFieldValue(target, srcName).toString();
            if(StringUtils.isEmpty(srcValue) || StringUtils.isEmpty(targetValue)) {  //注意：此处判断为或，当其中一个为空，则不比较
                continue;
            }
            if(!srcValue.equals(targetValue)) {
                //默认：数据库定义的字段名和实体类的属性名一致
                String columnCommnet = modifyInfoLogService.findColComment(tableName,srcName,modifyInfoLogService.returnDbName());
                //第二种：数据库字段名类似为：legal_certCode
                if("".equals(columnCommnet)||columnCommnet==null){
                    columnCommnet = modifyInfoLogService.findColComment(tableName,getString(srcName),modifyInfoLogService.returnDbName());
                    if("".equals(columnCommnet)||columnCommnet==null){
                        //第三种：数据库字段名类似为：legal_cert_code
                        columnCommnet = modifyInfoLogService.findColComment(tableName,changString(srcName),modifyInfoLogService.returnDbName());
                    }
                }
                if(StringUtils.isEmpty(modifyInfoLog.getOperationType())){
                    modifyInfoLog.setOperationType("修改/更新");
                }
                // 如果数据库读出来的表注释不符合要求,可调用方法前传进来替换读取出来的,例如 t_company_2读出来的表注释为("公司,企业,(用章)"),可在调用前传入用章企业信息
                if(StringUtils.isEmpty(modifyInfoLog.getTableText())){// 如果已经没有值,数据库读取表注释
                    modifyInfoLog.setTableName(modifyInfoLogDao.findTableComment(modifyInfoLog.getTableName(),modifyInfoLogService.returnDbName()));
                }else{
                    modifyInfoLog.setTableName(modifyInfoLog.getTableText()); // 调用时已传入
                }
                modifyInfoLog.setColumnName(srcName);
                modifyInfoLog.setColumnText(columnCommnet);
                modifyInfoLog.setOldValue(targetValue);
                modifyInfoLog.setNewValue(srcValue);
                modifyInfoLog.preInsert();
                modifyInfoLogDao.insert(modifyInfoLog);
            }
        }
    }







    /**
     * @author 许彩开
     * @TODO (注：删除“附件”日志保存)
      * @param attachment
     * @DATE: 2018\8\8 0008 9:31
     */

    public static void deleteAttachsLog(Attachment attachment , StampRecord stampRecord){

        Attachment attachment2 = attachmentService.getAttachment(attachment);
        String columnText = DictUtils.getDictLabel(attachment2.getAttachType(),"file_type","");

        ModifyInfoLog tLogDetail = new ModifyInfoLog();
        if(stampRecord.getStamp().getStampState().getKey().equals("3")){//值为3是 是已制作 否则是待刻

            tLogDetail.setBusinessName("印章办理-已制作印章-修改附件");

        }else{
            tLogDetail.setBusinessName("印章办理-待刻印章-修改附件");
        }

        tLogDetail.setTableName(modifyInfoLogDao.findTableComment("t_stamprecord_"+stampRecord.getWorkType().getKey(),modifyInfoLogService.returnDbName()));
        tLogDetail.setColumnName("file_type");
        tLogDetail.setColumnText(columnText);
        tLogDetail.setOperationType("删除");
        tLogDetail.setType("modifyCompanyInfo_before");
        tLogDetail.setCompanyName(stampRecord.getUseComp().getId());
        tLogDetail.setMakeCom(stampRecord.getMakeComp().getId());
        tLogDetail.setOldValue(attachment2.getAttachPath());

        tLogDetail.preInsert();

        modifyInfoLogDao.insert(tLogDetail);
    }

    /**
     * @author 许彩开
     * @TODO (注：添加“附件”日志保存)
     * @param attachment
     * @DATE: 2018\8\8 0008 9:31
     */

    public static void addAttachsLog(Attachment attachment,StampRecord stampRecord){
        String columnText = DictUtils.getDictLabel(attachment.getAttachType(),"file_type","");

        ModifyInfoLog tLogDetail = new ModifyInfoLog();

        if(stampRecord.getStamp().getStampState().getKey().equals("3")){//值为3是 是已制作 否则是待刻

            tLogDetail.setBusinessName("印章办理-已制作印章-修改附件");

        }else{
            tLogDetail.setBusinessName("印章办理-待刻印章-修改附件");
        }

        tLogDetail.setTableName(modifyInfoLogDao.findTableComment("t_stamprecord_"+stampRecord.getWorkType().getKey(),modifyInfoLogService.returnDbName()));
        tLogDetail.setColumnName("file_type");
        tLogDetail.setColumnText(columnText);
        tLogDetail.setOperationType("添加");
        tLogDetail.setType("modifyCompanyInfo_before");
        tLogDetail.setCompanyName(stampRecord.getUseComp().getId());
        tLogDetail.setMakeCom(stampRecord.getMakeComp().getId());
        tLogDetail.setNewValue(attachment.getAttachPath());
        tLogDetail.preInsert();
        modifyInfoLogDao.insert(tLogDetail);
    }



    /**
     * Description: 获取Obj对象的fieldName属性的值
     */
    private static Object getFieldValue(Object obj, String fieldName) {
        Object fieldValue = null;
        if(null == obj) {
            return null;
        }
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if(!methodName.startsWith("get")) {
                continue;
            }
            if(methodName.startsWith("get") && methodName.substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                try {
                    fieldValue = method.invoke(obj, new Object[] {});
                } catch (Exception e) {
                    System.out.println("取值出错，方法名 " + methodName);
                    continue;
                }
            }
        }
        return fieldValue;
    }

/**
 * @author 许彩开
 * @TODO (注：此方法为了将类似字符串 compAddress 转为 comp_address)
  * @param word
 * @DATE: 2018\8\3 0003 11:44
 */

    public static String changString (String word){
        StringBuffer s = new StringBuffer();
        for (int i=0;i<word.length();i++){
            char c = word.charAt(i);

            if(Character.isUpperCase(c)){
                s.append("_"+Character.toLowerCase(c));//改为小写
            }else {
                s.append(c);
            }
        }
        System.out.println("转换后："+s.toString());
        return s.toString();
    }


    /**
     * @author 许彩开
     * @TODO (注：处理特殊字符串（数据库里不规范命名）数据库字段名类似为：legal_certCode)
      * @param word
     * @DATE: 2018\8\6 0006 16:04
     */

    public static String getString (String word){
        int index = -1;
        char[] chars = word.toCharArray();
        for(int i=0;i<chars.length;i++){
            if((chars[i] >= 'A')&&(chars[i] <='Z')){
                index = i;
                break;
            }
        }
        return word.substring(0,index)+"_"+word.substring(index);
    }

}
