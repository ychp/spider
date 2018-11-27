package com.ychp.file.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipartFileRequest implements Serializable {

	private static final long serialVersionUID = 5656717092841205481L;

	private MultipartFile file;

	private String basePath;
}
