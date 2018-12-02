package com.ychp.spider.web.views;

import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.bean.request.ParserCriteria;
import com.ychp.spider.bean.request.RuleCriteria;
import com.ychp.spider.dto.AlbumDto;
import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.model.ParserNode;
import com.ychp.spider.model.Rule;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.service.ParserNodeReadService;
import com.ychp.spider.service.RuleReadService;
import com.ychp.spider.service.SpiderDataReadService;
import com.ychp.user.api.bean.query.UserCriteria;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Controller
public class Managers {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private ParserNodeReadService parserNodeReadService;

    @Autowired
    private RuleReadService ruleReadService;

    @Autowired
    private SpiderDataReadService spiderDataReadService;

    /**
     * 会员管理分页
     */
    @GetMapping("users")
    public String users(Model model, @RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "20") Integer pageSize) {
        UserCriteria criteria = new UserCriteria();
        criteria.setPageNo(pageNo);
        criteria.setPageSize(pageSize);
        Paging<User> pagingResult = userReadService.paging(criteria);

        model.addAttribute("users", pagingResult);
        return "users";
    }

    /**
     * 爬虫解析管理
     */
    @GetMapping("parsers")
    public String parsers(Model model, ParserCriteria criteria) {
        Paging<ParserNode> parserNodePaging = parserNodeReadService.paging(criteria);

        model.addAttribute("parsers", parserNodePaging);
        return "parsers";
    }

    /**
     * 爬虫规则管理
     */
    @GetMapping("rules")
    public String rules(Model model, RuleCriteria criteria) {
        Paging<Rule> rulePaging = ruleReadService.paging(criteria);
        model.addAttribute("rules", rulePaging);

        List<ParserNode> parserNodes = parserNodeReadService.list(new ParserCriteria());
        model.addAttribute("parsers", parserNodes);

        model.addAttribute("status", criteria.getStatus());
        return "rules";
    }

    /**
     * 数据管理
     */
    @GetMapping("datas")
    public String result(Model model, DataCriteria criteria) {
        Paging<SpiderData> spiderDataPaging = spiderDataReadService.paging(criteria);
        spiderDataPaging.getDatas().forEach(data -> {
            String content = data.getContent();
            if (content.contains("&filename=")) {
                data.setContent(
                        content.substring(0, content.indexOf("&filename="))
                );
            }
        });
        model.addAttribute("datas", spiderDataPaging);
        model.addAttribute("type", criteria.getType());
        return "datas";
    }

    /**
     * 相册管理
     */
    @GetMapping("albums")
    public String albums(Model model) {
        List<AlbumDto> albumDtos = spiderDataReadService.findAlbum();
        model.addAttribute("albums", albumDtos);
        return "albums";
    }

    /**
     * 数据管理
     */
    @GetMapping("data-urls")
    public String detail(Model model, DataCriteria criteria) {
        criteria.setPageSize(Integer.MAX_VALUE);
        Paging<SpiderData> spiderDataPaging = spiderDataReadService.paging(criteria);
        List<SpiderData> spiderDatas = spiderDataPaging.getDatas();
        List<SpiderData> videos = spiderDatas.stream()
                .filter(data -> Objects.equals(data.getType(), DataType.VIDEO.getValue()))
                .sorted(Comparator.comparingInt(SpiderData::getLevel)).collect(Collectors.toList());
        model.addAttribute("videos", videos);

        List<SpiderData> images = spiderDatas.stream()
                .filter(data -> !Objects.equals(data.getStatus(), DataStatus.WRONG.getValue()))
                .filter(data -> Objects.equals(data.getType(), DataType.IMAGE.getValue()))
                .sorted(Comparator.comparingInt(SpiderData::getLevel)).collect(Collectors.toList());

        model.addAttribute("images", images);

        Rule rule = ruleReadService.findOneById(criteria.getRuleId());
        model.addAttribute("rule", rule);
        return "data-details";
    }
}
