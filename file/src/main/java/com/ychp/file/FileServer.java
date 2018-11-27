package com.ychp.file;

import com.ychp.file.bean.request.MultipartFileRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
public interface FileServer {

	String write(MultipartFileRequest request) throws IOException;

	String write(File file);


}
