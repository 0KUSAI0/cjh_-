package service;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;
public class FileCopy {


    public static void copyFile(File fromFile, File toFile) throws IOException {
        DataInputStream dis= new DataInputStream( new BufferedInputStream(new FileInputStream(fromFile)));
        DataOutputStream dos= new DataOutputStream( new BufferedOutputStream(new FileOutputStream(toFile)));
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = dis.read(buffer))!= -1) {
            dos.write(buffer, 0, bytesRead);
        }

        dis.close();
        dos.close();
    }//将原图片进行复制
}
