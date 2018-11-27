package com.ychp.file.cos.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.ychp.file.FileServer;
import com.ychp.file.bean.request.MultipartFileRequest;
import com.ychp.file.cos.token.CosToken;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
public class CosFileServerImpl implements FileServer {

	public CosFileServerImpl(CosToken token) {
		// 1 初始化用户身份信息(secretId, secretKey)
		COSCredentials cred = new BasicCOSCredentials(token.getSecretId(), token.getSecretKey());
		// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		ClientConfig clientConfig = new ClientConfig(new Region(token.getRegion()));
		// 3 生成cos客户端
		cosClient = new COSClient(cred, clientConfig);
		// bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
		bucketName = token.getBucketName() + "-" + token.getAppId();

		host = clientConfig.getHttpProtocol() + "://" + bucketName + ".cossh.myqcloud.com";
	}

	private String bucketName;

	private COSClient cosClient;

	@Getter
	private String host;

	@Override
	public String write(MultipartFileRequest request) throws IOException {
		MultipartFile file = request.getFile();
		String basePath = request.getBasePath();
		String fullFilePath = StringUtils.isEmpty(basePath) ? file.getOriginalFilename()
				: basePath + "/" + file.getOriginalFilename();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				fullFilePath,
				file.getInputStream(),
				metadata);
		cosClient.putObject(putObjectRequest);
		return host + "/" + fullFilePath;
	}

	@Override
	public String write(File file) {
		throw new RuntimeException("not.implement");
	}
}
