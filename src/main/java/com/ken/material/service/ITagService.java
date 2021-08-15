package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.admin.query.AdminTagQueryParam;
import com.ken.material.entity.Tag;
import com.ken.material.vo.TagAddVo;
import com.ken.material.vo.TagListVo;
import com.ken.material.vo.TagSelectVo;
import com.ken.material.vo.TagUpdateVo;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author ken
 * @since 2021-07-19
 */
public interface ITagService extends IService<Tag> {

    PageVo<TagListVo> searchPage(AdminTagQueryParam queryParam);

    void addTag(TagAddVo addVo);

    void updateTag(TagUpdateVo updateVo);

    void deleteTag(Long id);

    List<TagSelectVo> searchList();

    void checkName(Long id, String name);
}
