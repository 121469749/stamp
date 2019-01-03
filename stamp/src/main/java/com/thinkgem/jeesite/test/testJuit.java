package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.supcan.common.properties.Properties;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;

import com.thinkgem.jeesite.common.utils.FileUtils;


import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.poi.hpsf.Thumbnail;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-*.xml"})
public class testJuit {

    @Test
    public void testLog() {
//        InputStream inputStream = testJuit.class.getClassLoader().getResourceAsStream("stamp.properties");
//        Properties properties = new Properties();
//        properties.lo
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        System.out.println("数据库链接："+ Global.getConfig("jdbc.url"));
        String dbUrl = Global.getConfig("jdbc.url");

        for(int i=0;i<3;i++){
            dbUrl=dbUrl.substring(dbUrl.indexOf("/")+1);
            System.out.println("dbUrl:"+dbUrl);
        }
        System.out.println("dbUrl:"+dbUrl.substring(0,dbUrl.indexOf("?")));
    }

    @Test
    public void testCopyFile(){
//        //临时文件夹实际路径
//        StringBuffer realPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));
//
////        StringBuffer realPathAtt = new StringBuffer(Global.getConfig("attachment.realPath"));
////
////        String srcFileName = realPath.append("1535096900268.jpg").toString();
////
////        String descFileName =realPathAtt.append("1535096900268.jpg").toString();
////
////        FileUtils.copyFile(srcFileName,descFileName);
//
//        FileUtils.deleteAllFile(realPath.toString());
        System.out.println((int)((Math.random()*9+1)*100000));
    }

/**
 * @author 许彩开
 * @TODO (注：测试压缩图片工具)
  * @param
 * @DATE: 2018\8\30 0030 10:01
 */

    @Test
    public void testCompressedPic(){
        //临时文件夹实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));
        StringBuffer realPath2 = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));
        StringBuffer waterRealPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));
        String srcFileName = realPath.append("珠海市老大科技_发票专用章-4_3_1536302108866.png").toString();

        String descFileName =realPath2.append("压缩后.bmp").toString();

        String waterPath =waterRealPath.append("picWater.png").toString();

        try {
            Thumbnails.of(srcFileName)//源路径
//                    .size(2736,3648)
                    .scale(1f)//指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽。
                    .outputFormat("bmp")
//                    .watermark(Positions.CENTER, ImageIO.read(new File(waterPath)),0.5f)//watermark(位置，水印图，透明度)
//                    .outputQuality(0.25f)//是图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差。
                    .toFile(descFileName);//输出路径
        }catch (IOException E)
        {
            E.printStackTrace();
        }



    }
@Test
    public void test3(){
    String string = "{month=4, year=2017, num=9}, " +
            "{month=5, year=2017, num=140}, {month=6, year=2017, num=1519}, {month=7, year=2017, num=3104}, " +
            "{month=8, year=2017, num=3993}, {month=9, year=2017, num=3853}, {month=10, year=2017, num=3028}," +
            " {month=11, year=2017, num=4242}, {month=12, year=2017, num=4383}, {month=1, year=2018, num=4381}, " +
            "{month=2, year=2018, num=1897}, {month=3, year=2018, num=4031}, {month=4, year=2018, num=4230}, {month=5, year=2018, num=3278}," +
            " {month=6, year=2018, num=8}, {month=7, year=2018, num=39}, {month=8, year=2018, num=61}, " +
            "{month=9, year=2018, num=10}, {month=10, year=2018, num=1}";
    String [] strings = string.split("},");
    List<String> list2017 = new ArrayList<>();
    List<String> list2018 = new ArrayList<>();
    List<Integer> listNum = new ArrayList<>();
    for (String strings1 : strings){
        if (strings1.contains("year=2017")){
            list2017.add(strings1);
        }else {
            list2018.add(strings1);
        }
    }
    int j=0;
    for (int i=1;i<=12;i++){
        String myMonth = "month="+i;
        String currentList = "";
        if (j<list2017.size()) {
            currentList = list2017.get(j);
        }
        if (currentList.contains(myMonth)){
            String currentList1= currentList.replace("}","");
            listNum.add(Integer.parseInt(currentList1.substring(currentList1.indexOf("num=")).replaceAll("num=","")));
            j++;
        }else{
            listNum.add(0);
        }
    }
    System.out.println(Arrays.toString(listNum.toArray()));


}
}
