package com.thinkgem.jeesite.test;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService.transferAlpha;

/**
 * @Auther: bb
 * @Date: 2018-09-18
 * @Description:
 */
public class testMain {

    public static void main(String[] args) throws Exception {
        /*String str = "/moulageEle_Scan/广东省/肇庆市/珠海市金山有限公司_发票专用章-1_4_1537175133174.png";
        int index = str.lastIndexOf(".");
        String target = str.substring(index+1,str.length());
        System.out.println(target);*/

/*
        // 保存PNG
        BufferedImage bi = ImageIO.read(new File("C:\\Users\\bb\\Pictures\\珠海横琴新区润成科技股份有限公司.bmp"));
        ImageIO.write(bi,"png",new File("C:\\Users\\bb\\Pictures\\toPNG.png"));
        bi.flush();// 清空缓冲区数据
        transferAlpha("C:\\Users\\bb\\Pictures\\toPNG.png","C:\\Users\\bb\\Pictures\\toPNG.png");// 将png的印模变透明
*/
        testMain testMain = new testMain();
        testMain.binaryImage();


    }

    public void binaryImage() throws IOException{
        Image img = ImageIO.read(new File("C:\\Users\\bb\\Pictures\\toPNG.png"));
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(img, 0, 0, null);
        g2D.dispose();
        int alpha = 0;
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int pixel = bufferedImage.getRGB(x, y);
                int r = ((pixel & 0x00ff0000) >>> 16);
                int g = ((pixel & 0x0000ff00) >>> 8);
                int b = ((pixel & 0x000000ff));
                if (r != 255 && g != 255 && b != 255) {
                    //非白色像素改为红色
                    int red = Color.RED.getRGB();
                    bufferedImage.setRGB(x, y, red);
                } else {
                    //白色像素改为透明
                    /*pixel = (alpha << 24) | (pixel & 0x00ffffff);
                    bufferedImage.setRGB(x, y, pixel);*/
                }
            }
        }
        ImageIO.write(bufferedImage, "png", new File("C:\\Users\\bb\\Pictures\\out1.png"));
    }


}
