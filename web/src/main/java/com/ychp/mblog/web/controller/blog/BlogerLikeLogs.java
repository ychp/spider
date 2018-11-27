package com.ychp.spider.web.controller.blog;

import com.google.common.collect.Lists;
import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.blog.bean.query.LikeLogCriteria;
import com.ychp.blog.model.Article;
import com.ychp.blog.model.LikeLog;
import com.ychp.blog.service.ArticleReadService;
import com.ychp.blog.service.LikeLogReadService;
import com.ychp.blog.service.LikeLogWriteService;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.ip.component.IPServer;
import com.ychp.spider.web.async.log.DislikeEvent;
import com.ychp.spider.web.async.log.LikeEvent;
import com.ychp.spider.web.controller.bean.LogConverter;
import com.ychp.spider.web.controller.bean.request.log.LikeLogRequest;
import com.ychp.spider.web.controller.bean.response.log.LikeLogVO;
import com.ychp.user.model.DeviceInfo;
import com.ychp.user.model.IpInfo;
import com.ychp.user.api.service.DeviceInfoReadService;
import com.ychp.user.api.service.DeviceInfoWriteService;
import com.ychp.user.api.service.IpInfoReadService;
import com.ychp.user.api.service.IpInfoWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Api(description = "点赞记录")
@RestController
@RequestMapping("/api/bloger/like-log")
public class BlogerLikeLogs {

	@Autowired
	private LikeLogReadService likeLogReadService;

	@Autowired
	private ArticleReadService articleReadService;

	@Autowired
	private IpInfoReadService ipInfoReadService;

	@Autowired
	public DeviceInfoReadService deviceInfoReadService;

	@Autowired
	private IpInfoWriteService ipInfoWriteService;

	@Autowired
	private DeviceInfoWriteService deviceInfoWriteService;

	@Autowired
	private LikeLogWriteService likeLogWriteService;

	@Autowired
	private IPServer ipServer;

	@Autowired
	private LogConverter logConverter;

	@Autowired
	private AsyncPublisher publisher;

	@ApiOperation(value = "点赞接口", httpMethod = "POST")
	@PostMapping
	public void like(@RequestBody LikeLogRequest likeLogRequest, HttpServletRequest request) {
		Long userId = SessionContextUtils.getUserId();
		String ip = ipServer.getIp(request);

		LikeLog exist = likeLogReadService.findByAimAndUserId(likeLogRequest.getAimId(),
				likeLogRequest.getType(), userId);

		if(exist != null) {
			throw new ResponseException("aim.has.liked");
		}

		LikeLog log = makeLikeLog(likeLogRequest, ip, userId);

		IpInfo ipInfo = ipInfoReadService.findByIp(ip);

		if(ipInfo == null) {
			ipInfo = logConverter.getIpInfo(ip);
			ipInfoWriteService.create(ipInfo);
		}

		DeviceInfo deviceInfo = logConverter.getDeviceInfo(request);

		DeviceInfo existDevice = deviceInfoReadService.findByUniqueInfo(deviceInfo);
		if(existDevice != null) {
			log.setDeviceId(existDevice.getId());
		} else {
			deviceInfoWriteService.create(deviceInfo);
		}

		likeLogWriteService.create(log);
		publisher.post(new LikeEvent(likeLogRequest.getAimId(), likeLogRequest.getType()));
	}

	private LikeLog makeLikeLog(LikeLogRequest likeLogRequest, String ip, Long userId) {
		LikeLog log = new LikeLog();
		log.setIp(ip);
		log.setUserId(userId);
		log.setAimId(likeLogRequest.getAimId());
		log.setType(likeLogRequest.getType());
		return log;
	}

	@ApiOperation(value = "取消点赞", httpMethod = "DELETE")
	@DeleteMapping
	public void cancel(@RequestBody LikeLogRequest likeLogRequest) {
		Long userId = SessionContextUtils.getUserId();
		LikeLog exist = likeLogReadService.findByAimAndUserId(likeLogRequest.getAimId(),
				likeLogRequest.getType(), userId);

		if(exist == null) {
			throw new ResponseException("aim.not.liked");
		}

		likeLogWriteService.delete(exist.getId());
		publisher.post(new DislikeEvent(likeLogRequest.getAimId(), likeLogRequest.getType()));
	}

	@ApiOperation("点赞记录分页接口 - 目前仅为文章点赞")
	@GetMapping("paging")
	public Paging<LikeLogVO> paging(LikeLogCriteria criteria) {
		criteria.setUserId(SessionContextUtils.getUserId());
		Paging<LikeLog> likeLogPaging;
		try {
			likeLogPaging = likeLogReadService.paging(criteria);
		} catch (Exception e) {
			throw new ResponseException("like.log.paging.fail", e.getMessage(), e.getCause());
		}

		return makePagingResult(likeLogPaging);
	}


	private Paging<LikeLogVO> makePagingResult(Paging<LikeLog> likeLogPaging) {
		if(likeLogPaging.isEmpty()) {
			return Paging.empty();
		}

		List<LikeLog> likeLogs = likeLogPaging.getDatas();

		List<String> ips = likeLogs.stream().map(LikeLog::getIp).collect(Collectors.toList());
		List<IpInfo> ipInfos;
		try {
			ipInfos = ipInfoReadService.findByIps(ips);
		} catch (Exception e) {
			throw new ResponseException("ip.info.find.fail", e.getMessage(), e.getCause());
		}
		Map<String, IpInfo> ipInfoByIp = ipInfos.stream()
				.collect(Collectors.toMap(IpInfo::getIp, ipInfo -> ipInfo));

		List<Long> deviceIds = likeLogs.stream().map(LikeLog::getDeviceId).collect(Collectors.toList());
		List<DeviceInfo> deviceInfos;
		try {
			deviceInfos = deviceInfoReadService.findByIds(deviceIds);
		} catch (Exception e) {
			throw new ResponseException("device.info.find.fail", e.getMessage(), e.getCause());
		}
		Map<Long, DeviceInfo> deviceInfoById = deviceInfos.stream()
				.collect(Collectors.toMap(DeviceInfo::getId, deviceInfo -> deviceInfo));

		List<Long> aimIds = likeLogs.stream().map(LikeLog::getAimId).collect(Collectors.toList());
		List<Article> articles;
		try {
			articles = articleReadService.findByIds(aimIds);
		} catch (Exception e) {
			throw new ResponseException("article.find.fail", e.getMessage(), e.getCause());
		}
		Map<Long, Article> articleById = articles.stream()
				.collect(Collectors.toMap(Article::getId, article -> article));

		List<LikeLogVO> result = Lists.newArrayListWithCapacity(likeLogs.size());

		for (LikeLog likeLog : likeLogs) {
			result.add(new LikeLogVO(likeLog,
					articleById.get(likeLog.getAimId()),
					ipInfoByIp.get(likeLog.getIp()),
					deviceInfoById.get(likeLog.getDeviceId())));
		}
		return new Paging<>(likeLogPaging.getTotal(), result);
	}


}
