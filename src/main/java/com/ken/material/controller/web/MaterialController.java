package com.ken.material.controller.web;


import com.ken.material.common.response.ResBody;
import com.ken.material.entity.Material;
import com.ken.material.service.IMaterialService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
public class MaterialController {

    private IMaterialService materialService;

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        Material material = this.materialService.getById(id);
        model.addAttribute("material", material);
        return "/web/material";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResBody<?> list(String key) {
        return ResBody.success();
    }

    @Autowired
    public void setMaterialService(IMaterialService materialService) {
        this.materialService = materialService;
    }
}

