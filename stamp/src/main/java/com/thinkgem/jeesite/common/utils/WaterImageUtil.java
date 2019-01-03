package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeService;
import org.omg.CORBA.StringHolder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片水印处理
 * <p>
 * Created by Locker on 2017/11/18.
 */
public class WaterImageUtil {


    // 水印透明度
    private static float alpha = 0.5f;
    // 水印横向位置
    private static int positionWidth = 150;
    // 水印纵向位置
    private static int positionHeight = 300;
    // 水印文字字体
    private static Font font = new Font("微软雅黑", Font.BOLD, 100);
    // 水印文字颜色
    private static Color color = Color.gray;

    /**
     * @param alpha          水印透明度
     * @param positionWidth  水印横向位置
     * @param positionHeight 水印纵向位置
     * @param font           水印文字字体
     * @param color          水印文字颜色
     */
    public static void setImageMarkOptions(float alpha, int positionWidth,
                                           int positionHeight, Font font, Color color) {
        if (alpha != 0.0f)
            WaterImageUtil.alpha = alpha;
        if (positionWidth != 0)
            WaterImageUtil.positionWidth = positionWidth;
        if (positionHeight != 0)
            WaterImageUtil.positionHeight = positionHeight;
        if (font != null)
            WaterImageUtil.font = font;
        if (color != null)
            WaterImageUtil.color = color;
    }

    /**
     * 给图片添加水印图片
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath) {
        markImageByIcon(iconPath, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree     水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath, Integer degree) {
        OutputStream os = null;
        try {

            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            // 3、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }

            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 5、得到Image对象。
            Image img = imgIcon.getImage();

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 6、水印图片的位置
            g.drawImage(img, positionWidth, positionHeight, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();

            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加水印图片");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText   水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath) {
        markImageByText(logoText, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     * @param degree
     */
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加水印文字");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String WaterImage(String srcImgPath, String stampShape, String companyName, String stampName) throws Exception {
        try {
            String[] suffixs = srcImgPath.split("\\.");

            String logoText = "润 城 科 技";
            //String iconPath = "d:/2.jpg";//水印图

            //中转地址
//        String targerTextPath = Global.getConfig("attachment.waterImageChangeTranferRealPath")+companyName+"-"+stampName+"-"+stampShape+"."+suffixs[suffixs.length-1];
            //最后实际地址
            String targerTextPath = Global.getConfig("attachment.waterImageFinalRealPath") + companyName + "/" + companyName + "-" + stampName + "-" + stampShape + "." + suffixs[suffixs.length - 1];
            //System.out.printf(targerTextPath2);
            String virtualPath = Global.getConfig("attachment.waterImageVirtualPath") + companyName + "/" + companyName + "-" + stampName + "-" + stampShape + "." + suffixs[suffixs.length - 1];
            String dir = Global.getConfig("attachment.waterImageFinalRealPath") + companyName;
            StampMakeService.createDir(dir);
//            System.out.printf(virtualPath);
//            System.out.printf(targerTextPath);
            System.out.println("给图片添加水印文字开始...");
            float alpha = 0.8F;
            String  font = "微软雅黑";
            int fontStyle = Font.PLAIN;
            int fontSize = 25;
            Color color = Color.BLACK;
            int x = 0;
            int y = 80;
            String imageFormat = suffixs[suffixs.length-1];
            WaterImageUtil.WordsToImage(srcImgPath, alpha, font, fontStyle,
                    fontSize, color, logoText, x, y, imageFormat, targerTextPath);
            System.out.println("给图片添加水印文字结束...");
            return virtualPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }
    public static void WordsToImage(String srcImagePath, float alpha,
                                    String font, int fontStyle, int fontSize, Color color,
                                    String inputWords, int x, int y, String imageFormat, String toPath) throws IOException {
        FileOutputStream fos = null;
        try {
            //读取图片
            BufferedImage image = ImageIO.read(new File(srcImagePath));
            System.out.println("srcImagePath:"+srcImagePath);
            //创建java2D对象
            Graphics2D g2d = image.createGraphics();
            //用源图像填充背景
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);

            //!!!!
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            //为 Graphics2D 上下文设置 Composite。 Composite 用于所有绘制方法中，如 drawImage、
            //drawString、draw 和 fill。 它指定新的像素如何在呈现过程中与图形设备上的现有像素组合。
            g2d.setComposite(ac);

            //设置文字字体名称、样式、大小
            g2d.setFont(new Font(font, fontStyle, fontSize));
            g2d.setColor(color);//设置字体颜色
            g2d.drawString(inputWords, x, y); //输入水印文字及其起始x、y坐标
            g2d.dispose();
            //将水印后的图片写入toPath路径中
            fos = new FileOutputStream(toPath);
            ImageIO.write(image, imageFormat, fos);
        }
        //文件操作错误抛出
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

//    public static void main(String[] args){
//
//        String  test = "/seal/广东省/珠海市//广东美满集团珠海美满电器有限公司/广东美满集团珠海美满电器有限公司_单位专用印章-3_01_1507879947045.seal";
//
//        String[] suffixs = test.split("\\.");
//
//        for(String string : suffixs){
//
//            System.out.println(string);
//        }
//
//    }

}
