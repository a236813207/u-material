package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.web.query.MaterialIndexQueryParam;
import com.ken.material.entity.Material;
import com.ken.material.vo.MaterialIndexVo;

/**
 * <p>
 * 素材表 服务类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
public interface IMaterialService extends IService<Material> {

    PageVo<MaterialIndexVo> findIndexList(MaterialIndexQueryParam queryParam);

}
