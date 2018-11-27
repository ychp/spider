package com.ychp.code.builder;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
public class FileService {


    public void writeToLocal(String path, String fileSuf, String content){
        String fileName = path + (StringUtils.isEmpty(fileSuf) ? "" : fileSuf);
        writeToLocal(fileName, content);
    }

    public void writeToLocal(String path, String content){
        BufferedWriter bufferedWriter = null;
        try{
            File file = new File(path);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
