package com.ken.material.controller.web;

import com.ken.material.common.page.PageVo;
import com.ken.material.common.response.ResBody;
import com.ken.material.controller.web.query.MaterialIndexQueryParam;
import com.ken.material.service.IMaterialService;
import com.ken.material.vo.MaterialIndexVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-03
 */
@Controller
@RequestMapping("/")
@Slf4j
@Api(value = "首页", tags = "首页")
public class IndexController {

    private IMaterialService materialService;

    @GetMapping("")
    public String index(Model model) {
        MaterialIndexQueryParam queryParam = new MaterialIndexQueryParam();
        PageVo<MaterialIndexVo> indexList = this.materialService.findIndexList(queryParam);
        model.addAttribute("list", indexList.getContent());
        // 获取用户信息

        return "/web/index";
    }

    @PostMapping("/search")
    public ResBody<?> search(MaterialIndexQueryParam queryParam) {
        PageVo<MaterialIndexVo> indexList = this.materialService.findIndexList(queryParam);
        return ResBody.success(indexList);
    }


    @Autowired
    public void setMaterialService(IMaterialService materialService) {
        this.materialService = materialService;
    }
}
