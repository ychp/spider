package com.ychp.spider.web.views;

import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.bean.request.ParserTypeCriteria;
import com.ychp.spider.bean.response.ParserTypeInfo;
import com.ychp.spider.dto.AlbumDto;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.service.ParserTypeReadService;
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

import java.util.List;

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
    private ParserTypeReadService parserTypeReadService;

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
        model.addAttribute("criteria", criteria);
        return "users";
    }

    /**
     * 爬虫类型管理
     */
    @GetMapping("parser-types")
    public String parserTypes(Model model, ParserTypeCriteria criteria) {
        Paging<ParserTypeInfo> parserNodePaging = parserTypeReadService.paging(criteria);
        model.addAttribute("criteria", criteria);
        model.addAttribute("parserTypes", parserNodePaging);
        return "parser-types";
    }

    /**
     * 数据管理
     */
    @GetMapping("datas")
    public String result(Model model, DataCriteria criteria) {
        Paging<SpiderData> spiderDataPaging = spiderDataReadService.paging(criteria);
        model.addAttribute("datas", spiderDataPaging);
        model.addAttribute("criteria", criteria);
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

}
