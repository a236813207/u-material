package com.ken.material.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ken.material.entity.Material;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 素材表 Mapper 接口
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
public interface MaterialMapper extends BaseMapper<Material> {

    @Select("select id, user_id, username, url, brand, keywords, tags, file_format, downloads, copyright, sort_no, " +
            "status, deleted, create_by, create_time, modify_by, modify_Time from tb_material where find_in_set(#{tagId},tags)")
    List<Material> countByTagId(Long tagId);

}
