package service;

import java.io.File;

public class NewName {
    public static String PasterName(String theFilePath,String name){
        String newName = name;//为新名字中的中间名字部分
        System.out.println(newName);
        File fatherPathFile=new File(theFilePath);//获取该文件的父文件
        File[] filesInFatherPath=fatherPathFile.listFiles();
        //获取该父文件的所有文件
        for(File fileInFatherPath: filesInFatherPath){
            String fileName=fileInFatherPath.getName();
            if(newName.compareTo(fileName)==0){
                //如果该文件夹下已存在了改名字，则在图片名字后新添副本与数字编号
                String str=null;
                int end=newName.lastIndexOf("."),start=newName.lastIndexOf("_副本");
                if(start!=-1){//如果当前已经有副本的存在时，要为其更换后面的数字编号
                    str=newName.substring(start,end);
                    int num=1;
                    num=Integer.parseInt(str.substring(str.lastIndexOf("_副本")+3))+1;
                    int cnt=0,d=num-1;
                    while(d!=0){
                        d/=10;
                        cnt++;
                    }
                    newName=newName.substring(0,end-cnt)+num+newName.substring(end);
                }else{//如果没有该名字的副本，则直接在后面加上副本，拼出文件路径
                    newName=newName.substring(0,end)+"_副本1"+newName.substring(end);
                }
            }
        }
        return newName;
    }//生成新的图片路径
}
