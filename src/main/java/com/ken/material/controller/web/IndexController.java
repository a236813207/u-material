package com.ken.material.controller.web;

import com.ken.material.common.page.PageVo;
import com.ken.material.common.response.ResBody;
import com.ken.material.controller.web.query.MaterialIndexQueryParam;
import com.ken.material.service.IMaterialService;
import com.ken.material.vo.MaterialIndexVo;
import com.ken.material.vo.UserVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-03
 */
@RequestMapping("/")
@Slf4j
@Api(value = "首页", tags = "首页")
public class IndexController extends BaseController {

    private IMaterialService materialService;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request) {
        MaterialIndexQueryParam queryParam = new MaterialIndexQueryParam();
        PageVo<MaterialIndexVo> indexList = this.materialService.findIndexList(queryParam);
        model.addAttribute("list", indexList.getContent());
        // 获取用户信息
        UserVo user = this.getUser(request);
        model.addAttribute("user", user);
        return "/web/index";
    }

    @GetMapping("/search")
    public String search(Model model, HttpServletRequest request) {
        MaterialIndexQueryParam queryParam = new MaterialIndexQueryParam();
        PageVo<MaterialIndexVo> indexList = this.materialService.findIndexList(queryParam);
        model.addAttribute("list", indexList.getContent());
        // 获取用户信息
        UserVo user = this.getUser(request);
        model.addAttribute("user", user);
        return "/web/search";
    }

    @PostMapping("/search")
    public ResBody<?> search(MaterialIndexQueryParam queryParam) {
        PageVo<MaterialIndexVo> indexList = this.materialService.findIndexList(queryParam);
        return ResBody.success(indexList);
    }

    @GetMapping("/rank")
    public String rank() {
        return "/web/rank";
    }

    @GetMapping("/udesign")
    public String udesign() {
        return "/web/udesign";
    }

    @Autowired
    public void setMaterialService(IMaterialService materialService) {
        this.materialService = materialService;
    }

}
