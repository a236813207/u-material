package com.ken.material.controller.admin;

import com.ken.material.common.page.PageVo;
import com.ken.material.common.response.ResBody;
import com.ken.material.controller.admin.query.AdminMaterialQueryParam;
import com.ken.material.service.IMaterialService;
import com.ken.material.service.ITagService;
import com.ken.material.vo.AdminMaterialAddVo;
import com.ken.material.vo.MaterialListVo;
import com.ken.material.vo.TagSelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 素材表 前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-01-23
 */
@Controller
@RequestMapping("/admin/material")
public class AdminMaterialController {

    private IMaterialService materialService;
    private ITagService tagService;

    @GetMapping("")
    public String index(Model model) {
        List<TagSelectVo> tags = this.tagService.searchList();
        model.addAttribute("tags", tags);
        return "/admin/material/material";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(AdminMaterialQueryParam queryParam) {
        PageVo<MaterialListVo> pageVo = this.materialService.searchPage(queryParam);
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
    public ResBody<?> add(@Validated AdminMaterialAddVo addVo) {
        this.materialService.adminAdd(addVo);
        return ResBody.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public ResBody<?> update(@Validated AdminMaterialAddVo addVo) {
        this.materialService.adminUpdate(addVo);
        return ResBody.success();
    }

    @PostMapping("/update/status/{id}")
    @ResponseBody
    public ResBody<?> updateStatus(@PathVariable Long id, int status) {
        this.materialService.updateStatus(id, status);
        return ResBody.success();
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResBody<?> delete(@PathVariable Long id) {
        this.materialService.removeById(id);
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

