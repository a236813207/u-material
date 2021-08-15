package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.admin.query.AdminMaterialQueryParam;
import com.ken.material.controller.web.query.MaterialIndexQueryParam;
import com.ken.material.entity.Material;
import com.ken.material.vo.AdminMaterialAddVo;
import com.ken.material.vo.MaterialAddVo;
import com.ken.material.vo.MaterialIndexVo;
import com.ken.material.vo.MaterialListVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 素材表 服务类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
public interface IMaterialService extends IService<Material> {

    PageVo<MaterialListVo> searchPage(AdminMaterialQueryParam queryParam);

    PageVo<MaterialIndexVo> findIndexList(MaterialIndexQueryParam queryParam);

    void add(MaterialAddVo addVo, HttpServletRequest request);

    void adminAdd(AdminMaterialAddVo addVo);

    void adminUpdate(AdminMaterialAddVo addVo);

    void updateStatus(Long id, int status);

    List<Material> countByTagId(Long tagId);
}
