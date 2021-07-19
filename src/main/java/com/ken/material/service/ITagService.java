package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.entity.Tag;
import com.ken.material.vo.TagListVo;

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

    List<TagListVo> searchList();

}
