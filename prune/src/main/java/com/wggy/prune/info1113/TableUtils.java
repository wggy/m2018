package com.wggy.prune.info1113;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class TableUtils {
    public static void myGraphicsGeneration(String cellsValue[][], String path, String title) {

        // 字体大小
        int fontTitileSize = 8;
        // 横线的行数
        int totalrow = cellsValue.length + 1;
        // 竖线的行数
        int totalcol = 0;
        if (cellsValue[0] != null) {
            totalcol = cellsValue[0].length;
        }
        // 图片宽度
        int imageWidth = 620;
         // 行高
        int rowheight = 14;
        // 图片高度
        int imageHeight = 350;
        // 起始高度
        int startHeight = 5;
        // 起始宽度
        int startWidth = 10;
        // 单元格宽度
        int colwidth = (int) ((imageWidth - 15 - 20) / totalcol);//51
        int colwidth1 = (int) ((imageWidth - 15) / totalcol);
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(new Color(220, 240, 240));

        //画横线
        for (int j = 0; j < totalrow; j++) {
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, startWidth + colwidth * totalcol + 20, startHeight + (j + 1) * rowheight);
        }
        //画竖线
        for (int k = 0; k < totalcol + 1; k++) {
            if (k == totalcol) {
                graphics.drawLine(startWidth + k * colwidth + 20, startHeight + rowheight, startWidth + k * colwidth + 20, startHeight + rowheight * totalrow);
                continue;
            }
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight, startWidth + k * colwidth, startHeight + rowheight * totalrow);
        }

        //设置字体
        Font font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
        graphics.setFont(font);
        //写标题
        //graphics.drawString(title, startWidth, startHeight+rowheight-10);
        System.out.println(title.length() / 2);
        graphics.drawString(title, imageWidth / 2 - (title.length() / 2) * 5, startHeight + rowheight - 10);

        //写入内容
        for (int n = 0; n < cellsValue.length; n++) {
            for (int l = 0; l < cellsValue[n].length; l++) {
                if (n == 0) {
                    font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
                    graphics.setFont(font);
                } else {
                    font = new Font("微软雅黑", Font.PLAIN, fontTitileSize);
                    graphics.setFont(font);
//                    graphics.setColor(Color.BLACK);
                }

                graphics.drawString(cellsValue[n][l].toString(), startWidth + colwidth * l + 5, startHeight + rowheight * (n + 2) - 5);
            }
        }
        // 保存图片
        createImage(image, path);
    }

    /**
     *      * 将图片保存到指定位置
     *      *
     *      * @param image        缓冲文件类
     *      * @param fileLocation 文件位置
     *     
     */
    public static void createImage(BufferedImage image, String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        TableUtils cg = new TableUtils();
        try {
            String tableData1[][] = {{"10年", "20年", "50年", "100年", "200年", "历 史 最 大"}, {
                    "125", "165", "201", "207", "392", "541<1890>"
            }};
            TableUtils.myGraphicsGeneration(tableData1, "F:\\myPic.png", "三不同重现期雨量表");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
