package com.ken.material.controller.web;


import cn.hutool.core.util.StrUtil;
import com.ken.material.common.response.ResBody;
import com.ken.material.entity.Material;
import com.ken.material.interceptor.TokenAuth;
import com.ken.material.service.IMaterialService;
import com.ken.material.service.ITagService;
import com.ken.material.vo.MaterialAddVo;
import com.ken.material.vo.TagSelectVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 素材表 前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
@Controller
@RequestMapping("/material")
@Api(value = "素材", tags = "素材")
public class MaterialController extends BaseController{

    private IMaterialService materialService;
    private ITagService tagService;

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        Material material = this.materialService.getById(id);
        model.addAttribute("material", material);
        model.addAttribute("user", StrUtil.hide(material.getUsername(), 3,7));
        List<TagSelectVo> tagList = this.tagService.searchList();
        String[] tags = material.getTags().split(",");
        Iterator<TagSelectVo> iterator = tagList.iterator();
        while (iterator.hasNext()) {
            TagSelectVo vo = iterator.next();
            boolean isEqual = false;
            for (String tag : tags) {
                if (String.valueOf(vo.getId()).equals(tag)) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) {
                iterator.remove();
            }
        }
        model.addAttribute("tags", tagList);
        return "/web/material";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResBody<?> list(String key) {
        return ResBody.success();
    }

    @GetMapping("/add")
    @TokenAuth
    public String add(Model model) {
        List<TagSelectVo> tags = this.tagService.searchList();
        model.addAttribute("tags", tags);
        return "/web/add";
    }

    @PostMapping("/add")
    @ResponseBody
    @TokenAuth
    public ResBody<?> add(@Validated MaterialAddVo addVo, HttpServletRequest request) {
        this.materialService.add(addVo, request);
        return ResBody.success();
    }

    @Autowired
    public void setMaterialService(IMaterialService materialService) {
        this.materialService = materialService;
    }

    @Autowired
    public void setTagService(ITagService tagService) {
        this.tagService = tagService;
    }
}

