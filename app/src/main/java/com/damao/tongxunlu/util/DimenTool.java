package com.damao.tongxunlu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * http://blog.csdn.net/chenzujie/article/details/9874859
 * http://www.bubuko.com/infodetail-832794.html
 */
public class DimenTool {
    private static void gen() {
        
        /** 
         * Androidstudio： ./app/src/main/res/values/dimens.xml 
         * Eclipse： ./res/values/dimens.xml 
         * 
         * /F:/work/MyWorkSpace/TestYouku/ 
         * */  
        //获取当前Classes 的绝对路径  
        String filePath=Class.class.getClass().getResource("/").getPath();
        System.out.println(filePath);
          
        System.out.println(System.getProperty("user.dir"));
        File file = new File("./app/src/main/res/values/dimens.xml");//这里如果找不到,使用绝对路径即可
//        File file = new File("./res/values/dimens.xml");//这里如果找不到,使用绝对路径即可
        BufferedReader reader = null;
        //添加分辨率  
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw270  = new StringBuilder();
        StringBuilder sw320  = new StringBuilder();
        StringBuilder sw360  = new StringBuilder();
        StringBuilder sw500  = new StringBuilder();
        StringBuilder sw600  = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        
        StringBuilder w820 = new StringBuilder();
  
        try {    
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;    
            // 一次读入一行，直到读入null为文件结束    
  
            while ((tempString = reader.readLine()) != null) {    
  
                if (tempString.contains("</dimen>")) {    
                    //tempString = tempString.replaceAll(" ", "");    
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    int num = Integer.parseInt(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));
  
                    // sw-xxxx-dp的计算公式是  sw *160/dpi 
                    // 分辨率1280*720, dpi 213, 720*160/213=540.84 =       sw340dp
                    //主要核心就再这里了，如下3种分辨率分别缩小 0.6、0.75、0.9倍(根据实际情况自己随意DIY)  
                    sw360.append(start).append((int) Math.round(num * 1)).append(end).append("\n");
                    sw480.append(start).append((int) Math.round(num * 1.2)).append(end).append("\n");
                    sw240.append(start).append((int) Math.round(num * 0.667)).append(end).append("\n");
                    sw270.append(start).append((int) Math.round(num * 0.75)).append(end).append("\n");
                    sw320.append(start).append((int) Math.round(num * 0.889)).append(end).append("\n");
                    sw500.append(start).append((int) Math.round(num * 1.389)).append(end).append("\n");
                    sw600.append(start).append((int) Math.round(num * 1.667)).append(end).append("\n");
                    sw720.append(start).append((int) Math.round(num * 2)).append(end).append("\n");
//                    w820.append(tempString).append("\n");    
  
                } else {    
                    sw720.append(tempString).append("\n");    
                    sw480.append(tempString).append("\n");    
                    sw240.append(tempString).append("\n");    
                    sw360.append(tempString).append("\n");    
                    sw270.append(tempString).append("\n");
                    sw500.append(tempString).append("\n");
                    sw600.append(tempString).append("\n");
                    sw320.append(tempString).append("\n");
//                    w820.append(tempString).append("\n");    
                }    
                line++;    
            }    
            reader.close();    
//            System.out.println("<!--  sw720 -->");
//            System.out.println(sw720);
//            System.out.println("<!--  sw480 -->");
//            System.out.println(sw480);
//
//            System.out.println("<!--  sw240 -->");
//            System.out.println(sw240);
//            System.out.println("<!--  sw360 -->");
//            System.out.println(sw360);
//            System.out.println("<!--  sw270 -->");
//            System.out.println(sw270);
//
//            System.out.println("<!--  sw500 -->");
//            System.out.println(sw500);
//            System.out.println("<!--  sw600 -->");
//            System.out.println(sw600);
  
//           Eclipse使用这部分  
//            String sw720file = "./res/values-sw720dp-port/dimens.xml";
//            String sw480file = "./res/values-sw480dp-port/dimens.xml";
//            String sw240file = "./res/values-sw240dp-port/dimens.xml";
//            String sw360file = "./res/values-sw360dp-port/dimens.xml";
//
//            String sw270file = "./res/values-sw270dp-port/dimens.xml";
//            String sw500file = "./res/values-sw500dp-port/dimens.xml";
//            String sw600file = "./res/values-sw600dp-port/dimens.xml";
//            String sw320file = "./res/values-sw320dp-port/dimens.xml";
//            String w820file  = "./res/values-w820dp/dimens.xml";    
              
//            Android Studio 使用 这部分
            String sw720file = "./app/src/main/res/values-sw720dp-port/dimens.xml";
            String sw480file = "./app/src/main/res/values-sw480dp-port/dimens.xml";
            String sw240file = "./app/src/main/res/values-sw240dp-port/dimens.xml";
            String sw360file = "./app/src/main/res/values-sw360dp-port/dimens.xml";

            String sw270file = "./app/src/main/res/values-sw270dp-port/dimens.xml";
            String sw500file = "./app/src/main/res/values-sw500dp-port/dimens.xml";
            String sw600file = "./app/src/main/res/values-sw600dp-port/dimens.xml";
            String sw320file = "./app/src/main/res/values-sw320dp-port/dimens.xml";

//            writeFile(sw720file, sw720.toString());
            writeFile(sw480file, sw480.toString());    
//            writeFile(sw240file, sw240.toString());
//            writeFile(sw360file, sw360.toString());
//
//            writeFile(sw270file, sw270.toString());
//            writeFile(sw500file, sw500.toString());
//            writeFile(sw600file, sw600.toString());
//            writeFile(sw320file, sw320.toString());
//            writeFile(w820file, w820.toString());    
        } catch (IOException e) {
            e.printStackTrace();    
        } finally {    
            if (reader != null) {    
                try {    
                    reader.close();    
                } catch (IOException e1) {
                    e1.printStackTrace();    
                }    
            }    
        }    
    }    
  
    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {    
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);    
        } catch (IOException e) {
            e.printStackTrace();    
        }     
        out.close();    
    }    
  
    public static void main(String[] args) {
        gen();    
    }
}
