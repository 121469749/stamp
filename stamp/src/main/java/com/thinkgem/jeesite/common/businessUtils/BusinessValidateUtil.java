package com.thinkgem.jeesite.common.businessUtils;

/**
 * Created by hjw-pc on 2017/5/21.
 */

import com.sun.org.apache.regexp.internal.RE;

/**
 * 业务验证工具类
 */
public class BusinessValidateUtil {

    //规则匹配会用到的固定参数列表
    private static String letter = "ABCDEFGHJKLMNPQRTUWXY";
    //规则匹配会用到的固定参数列表
    private static int[] weighting1 = new int[]{3, 7, 9, 10, 5, 8, 4, 2};
    //规则匹配会用到的固定参数列表
    private static int[] weighting2 = new int[]{1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};

    //18位社会信用统一码表达式匹配
    public static String newCodePattern1 = "[1-9ABCDEFGV]{1}[1239]{1}[0-9]{6}[0-9A-Z]{9}[0-9ABCDEFGHJKLMNPQRTCUWXY]{1}";

    //10位组织结构代码正则匹配
    public static String newCodePattern2 = "[0-9A-Z]{8}-[0-9A-Z]{1}";

    //9位组织结构代码正则匹配
    public static String newCodePattern3 = "[0-9A-Z]{8}[0-9A-Z]{1}";

    //15位组织结构代码正则匹配
    public static String newCodePattern4  = "[0-9]{15}";

    /**
     * 根据编码规则来验证统一社会代码
     *
     * @param checkCode
     * @return true-校验成功
     * false-校验失败
     */
    public static boolean checkSoleCode(String checkCode, int length) {

        if (length == 18) {

            int sum = countCW(checkCode.toCharArray(), weighting2);


            sum = 31 - sum % 31;



            if(sum == 31){

               char lastChar= checkCode.charAt(checkCode.length()-1);



               return '0'==lastChar ? true:false;

            }

            if (!(sum <= 9)) {

                //先获得 checkCode最后一位的数值
                int lastIndex = letter.indexOf(checkCode.charAt(length-1)) + 10;


                return lastIndex == sum ? true : false;
            }



            return sum == (byte) checkCode.charAt(checkCode.length() - 1) - '0' ? true : false;
        }

        if (length == 9) {

            int sum = countCW(checkCode.toCharArray(), weighting1);

            sum = 11 - sum % 11;

            if (!(sum <= 9)) {

                //先获得 checkCode最后一位的数值
                int lastIndex = (byte) checkCode.charAt(checkCode.length() - 1) - 'A'+10;

                return lastIndex == sum ? true : false;
            }

            return sum == (byte) checkCode.charAt(checkCode.length() - 1) - '0' ? true : false;
        }


        if(length == 15){

            return countPS(checkCode.toCharArray());

        }

        return false;
    }

    /**
     * 计算 SUM(Ci*Wi)
     *
     * @param codes
     * @return 计算出来的Sum值
     */
    private static int countCW(char[] codes, int[] weighting) {

        int sum = 0;


        for (int i = 0; i < codes.length - 1; i++) {

            byte now = (byte) codes[i];

            //数字
            if (now <= '9') {
                sum += weighting[i] * (now - '0');
                //System.out.println(codes[i]+":"+now+","+(now-48));
            }
            //字母
            if (now >= 'A') {
                //位数不同 处理的字码表也不同
                if (codes.length == 18) {
                    int letterIndex = letter.indexOf(codes[i]) + 10;
                    // System.out.println(codes[i]+":"+letterIndex);
                    sum += weighting[i] * letterIndex;
                } else {
                    sum += weighting[i] * (now - 'A'+10);
                    // System.out.println(codes[i]+":"+now+","+(now-55));
                }
            }
        }

        return sum;
    }

    /**
     * 15位工商码
     * @param codes
     * @return
     */
    private static boolean countPS(char[] codes){

        //p1初始化为10
        int p = 10;

        int s = 0;

        for(int i = 0;i<codes.length-1;i++){

            s = p + ((byte)codes[i])-48;

            //System.out.println("s:"+s);

            p = ((s%10) == 0 ? 10 : (s%10) )* 2;

            //System.out.println("p:"+p);

            p = p%11;

            //System.out.println("p:"+p);

        }

        System.out.println("p:"+p);

        System.out.println(((byte)codes[14]-48));

        s=p+ (((byte)codes[14])-48);

        System.out.println("s:"+s);

        return (s%10) == 1 ? true:false;
    }


    public static void main(String[] args){

        String soleCode = "440400aa0ABA077007";

        //System.out.println(((byte)'0'));

        System.out.println(soleCode.substring(0, 6));

        //System.out.println(soleCode+" is "+checkSoleCode(soleCode,15));

    }

}
