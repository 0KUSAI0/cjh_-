package service;

import java.io.File;

public class NewName {
    public static String PasterName(String theFilePath,String name){
        String newName = name;
        File fatherPathFile=new File(theFilePath);
        File[] filesInFatherPath=fatherPathFile.listFiles();
        for(File fileInFatherPath: filesInFatherPath){
            String fileName=fileInFatherPath.getName();
            if(newName.compareTo(fileName)==0){
                String str=null;
                int end=newName.lastIndexOf("."),start=newName.lastIndexOf("_副本");
                if(start!=-1){
                    str=newName.substring(start,end);
                    int num=1;
                    num=Integer.parseInt(str.substring(str.lastIndexOf("_副本")+3))+1;
                    int cnt=0,d=num-1;
                    while(d!=0){
                        d/=10;
                        cnt++;
                    }
                    newName=newName.substring(0,end-cnt)+num+newName.substring(end);
                }else{
                    newName=newName.substring(0,end)+"_副本1"+newName.substring(end);
                }
            }

        }
        return newName;
    }//生成新的名字
}
