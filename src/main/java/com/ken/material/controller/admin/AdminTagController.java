package com.ken.material.controller.admin;


import com.ken.material.common.page.PageVo;
import com.ken.material.common.response.ResBody;
import com.ken.material.controller.admin.query.AdminTagQueryParam;
import com.ken.material.service.ITagService;
import com.ken.material.vo.TagAddVo;
import com.ken.material.vo.TagListVo;
import com.ken.material.vo.TagUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-07-19
 */
@Controller
@RequestMapping("/admin/tag")
public class AdminTagController {

    private ITagService tagService;

    @GetMapping("")
    public String index() {
        return "/admin/tag/tag";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(AdminTagQueryParam queryParam) {
        PageVo<TagListVo> pageVo = this.tagService.searchPage(queryParam);
        Map<String, Object> map = new HashMap<>(8);
        map.put("recordsTotal", pageVo.getTotal());
        map.put("recordsFiltered", pageVo.getTotal());
        map.put("data", pageVo.getContent());
        map.put("code", 200);
        map.put("draw", queryParam.getDraw());
        return map;
    }

    @PostMapping("/add")
    @ResponseBody
    public ResBody<?> add(@Validated TagAddVo addVo) {
        this.tagService.addTag(addVo);
        return ResBody.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public ResBody<?> update(@Validated TagUpdateVo updateVo) {
        this.tagService.updateTag(updateVo);
        return ResBody.success();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResBody<?> delete(@PathVariable Long id) {
        this.tagService.deleteTag(id);
        return ResBody.success();
    }

    @Autowired
    public void setTagService(ITagService tagService) {
        this.tagService = tagService;
    }

}

