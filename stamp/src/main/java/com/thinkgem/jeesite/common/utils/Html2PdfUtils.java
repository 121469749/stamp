package com.thinkgem.jeesite.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Html2PdfUtils {
    public static void html2Pdf(String htmlPath,String pdfPath) {
        ProcessBuilder pb = new ProcessBuilder("D:\\stamp\\wkhtmltopdf\\bin\\wkhtmltopdf", htmlPath, pdfPath);
        Process process;
        BufferedReader errStreamReader =null;
        try {
            process = pb.start();
            //注意，调用process.getErrorStream()而不是process.getInputStream()
            errStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            System.out.println("read errstreamreader");
            String line = null;
            line = errStreamReader.readLine();
            while(line != null) {
                System.out.println(line);
                line = errStreamReader.readLine();
            }
            errStreamReader.close();
            process.destroy();
            System.out.println("destroyed process");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                errStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
