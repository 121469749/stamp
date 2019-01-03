package com.thinkgem.jeesite.modules.stamp.service.picture;

import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.util.picture.PictureTool;
import com.thinkgem.jeesite.modules.stamp.common.util.picture.PictureTool2;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 图片对比服务层
 * Created by Lokcer on 2017/7/15.
 */
@Service
public class PictureService {

    /**
     *
     * 第一种图片比较算法使用
     *  简单比较
     * @param files
     * @return
     * @throws IOException
     */
    public Condition comparePicture(MultipartFile[] files) throws IOException {

        InputStream inputStream1 = files[0].getInputStream();

        InputStream inputStream2 = files[1].getInputStream();

        BufferedImage image1 = ImageIO.read(inputStream1);
        int image_fingerprint1 = getFingerPrint(image1);

        BufferedImage image2 = ImageIO.read(inputStream2);
        int image_fingerprint2 = getFingerPrint(image2);


        int compareResult = compareFingerPrint(image_fingerprint1, image_fingerprint2);


        return getResult(compareResult);
    }

    /**
     * @param image
     * @return
     */
    private int getFingerPrint(BufferedImage image) {
        final int WIDTH = 8;
        final int HEIGHT = 8;
        image = PictureTool.reduceSize(image, WIDTH, HEIGHT);
        double[][] pixels = PictureTool.getGrayValue(image);
        double avg = PictureTool.getAverage(pixels);
        int fingerprint = PictureTool.getFingerPrint(pixels, avg);
        return fingerprint;
    }


    private int compareFingerPrint(int orgin_fingerprint, int compared_fingerprint) {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            byte orgin = (byte) (orgin_fingerprint & (1 << i));
            byte compared = (byte) (compared_fingerprint & (1 << i));
            if (orgin != compared) {
                count++;
            }
        }
        return count;
    }

    /**
     * 输出比较结果
     *
     * @param num
     */
    private Condition getResult(int num) {

        Condition condition = new Condition(Condition.SUCCESS_CODE);

        if (num >= 10) {

            condition.setMessage("两张图完全不相同");

        } else if (num >= 7 && num < 10) {

            condition.setMessage("两张图很少相似")
            ;
        } else if (num >= 3 && num < 7) {

            condition.setMessage("两张图有些相似");
        } else if (num >= 1 && num < 3) {

            condition.setMessage("两张图极其相似");
        } else if (num == 0) {

            condition.setMessage("两张图完全相同");
        } else {

            condition.setMessage("抱歉,无法对比!");
        }

        return condition;
    }


    /**
     *  谷歌图片比较相似度算法
     * @param files
     * @return
     * @throws IOException
     */
    public double comparePicture2(MultipartFile[] files) throws IOException {

        InputStream inputStream1 = files[0].getInputStream();


        //图一处理过程
        Image image1 =ImageIO.read(inputStream1);
        // 转换至灰度
        image1 = PictureTool2.toGrayscale(image1);
        // 缩小成32x32的缩略图
        image1 = PictureTool2.scale(image1);
        // 获取灰度像素数组
        int[] pixels1 = PictureTool2.getPixels(image1);
        // 获取平均灰度颜色
        int averageColor1 = PictureTool2.getAverageOfPixelArray(pixels1);
        // 获取灰度像素的比较数组（即图像指纹序列）
        pixels1 = PictureTool2.getPixelDeviateWeightsArray(pixels1, averageColor1);



        InputStream inputStream2 = files[1].getInputStream();

        //图一处理过程
        Image image2 =ImageIO.read(inputStream2);

        image2 = PictureTool2.toGrayscale(image2);

        image2 = PictureTool2.scale(image2);

        int[] pixels2 = PictureTool2.getPixels(image2);

        int averageColor2 = PictureTool2.getAverageOfPixelArray(pixels2);

        pixels2 = PictureTool2.getPixelDeviateWeightsArray(pixels2, averageColor2);

        // 获取两个图的汉明距离（假设另一个图也已经按上面步骤得到灰度比较数组）
        int hammingDistance = PictureTool2.getHammingDistance(pixels1, pixels2);
        // 通过汉明距离计算相似度，取值范围 [0.0, 1.0]
        double similarity = PictureTool2.calSimilarity(hammingDistance);


        return similarity;
    }

}
