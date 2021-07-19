package com.ken.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ken.material.entity.Tag;
import com.ken.material.enums.Status;
import com.ken.material.mapper.TagMapper;
import com.ken.material.service.ITagService;
import com.ken.material.vo.TagListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-07-19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public List<TagListVo> searchList() {
        List<Tag> list = this.list(new QueryWrapper<Tag>().lambda()
                .eq(Tag::getStatus, Status.enable).orderByDesc(Tag::getSortNo));
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(tag -> {
            TagListVo vo = new TagListVo();
            BeanUtils.copyProperties(tag, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
