package com.ychp.spider.web.controller.common.file;

import com.ychp.common.exception.ResponseException;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.file.FileServer;
import com.ychp.file.bean.request.MultipartFileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Api(description = "文件服务")
@RestController
@RequestMapping("/api/file")
public class Files {

	@Autowired
	private FileServer fileServer;

	@ApiOperation("文件上传")
	@PostMapping("upload")
	public String upload(MultipartFile file) {
		try {
			Long userId = SessionContextUtils.getUserId();
			String basePath = "tmp";
			if(userId != null) {
				basePath = userId.toString();
			}
			return fileServer.write(new MultipartFileRequest(file, basePath));
		} catch (IOException e) {
			throw new ResponseException("file.upload.fail", e.getMessage(), e.getCause());
		}
	}
}
