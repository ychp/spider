package com.ychp.spider.web.views;

import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.UserCriteria;
import com.ychp.user.api.service.UserReadService;
import com.ychp.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Controller
@RequestMapping("/")
public class Managers {

    @Autowired
    private UserReadService userReadService;

    /**
     * 会员管理分页
     */
    @GetMapping("users")
    public String users(Model model, @RequestParam(defaultValue = "1") Integer pageNo,
                        @RequestParam(defaultValue = "20") Integer pageSize){
        UserCriteria criteria = new UserCriteria();
        criteria.setPageNo(pageNo);
        criteria.setPageSize(pageSize);
        Paging<User> pagingResult = userReadService.paging(criteria);

        model.addAttribute("users", pagingResult);
        return "users";
    }

}
