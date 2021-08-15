package com.ken.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.exception.BizException;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.admin.query.AdminTagQueryParam;
import com.ken.material.entity.Material;
import com.ken.material.entity.Tag;
import com.ken.material.enums.Status;
import com.ken.material.mapper.TagMapper;
import com.ken.material.service.IMaterialService;
import com.ken.material.service.ITagService;
import com.ken.material.vo.TagAddVo;
import com.ken.material.vo.TagListVo;
import com.ken.material.vo.TagSelectVo;
import com.ken.material.vo.TagUpdateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    private IMaterialService materialService;

    @Override
    public PageVo<TagListVo> searchPage(AdminTagQueryParam queryParam) {
        Page<Tag> page = new Page<>(queryParam.getPage(), queryParam.getRows());
        page.setOrders(Lists.newArrayList(OrderItem.asc("sort_no")));
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(queryParam.getName())) {
            wrapper.lambda().like(Tag::getName, queryParam.getName());
        }
        if (queryParam.getStatus() != null) {
            wrapper.lambda().eq(Tag::getStatus, queryParam.getStatus());
        }
        Page<Tag> tagPage = this.page(page, wrapper);
        if (CollectionUtils.isEmpty(tagPage.getRecords())) {
            return PageVo.create(queryParam.getPage(), queryParam.getRows(), 0, Lists.newArrayList());
        }
        List<TagListVo> result = tagPage.getRecords().stream().map(tag -> {
            TagListVo vo = new TagListVo();
            BeanUtils.copyProperties(tag, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageVo.create(queryParam.getPage(), queryParam.getRows(), tagPage.getTotal(), result);
    }

    @Override
    public void addTag(TagAddVo addVo) {
        this.checkName(null, addVo.getName());
        Tag tag = new Tag();
        tag.setName(addVo.getName());
        tag.setSortNo(addVo.getSortNo());
        tag.setStatus(addVo.getStatus());
        if (!this.save(tag)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("添加失败！"));
        }
    }

    @Override
    public void updateTag(TagUpdateVo updateVo) {
        this.checkName(updateVo.getId(), updateVo.getName());
        Tag tag = new Tag();
        tag.setId(updateVo.getId());
        tag.setName(updateVo.getName());
        tag.setSortNo(updateVo.getSortNo());
        tag.setStatus(updateVo.getStatus());
        if (!this.updateById(tag)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("修改失败！"));
        }
    }

    @Override
    public void deleteTag(Long id) {
        List<Material> materials = this.materialService.countByTagId(id);
        if (!CollectionUtils.isEmpty(materials)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("标签存在关联的素材"));
        }
        if (!this.removeById(id)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("删除失败！"));
        }
    }

    @Override
    public List<TagSelectVo> searchList() {
        List<Tag> list = this.list(new QueryWrapper<Tag>().lambda()
                .eq(Tag::getStatus, Status.enable).orderByDesc(Tag::getSortNo));
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(tag -> {
            TagSelectVo vo = new TagSelectVo();
            BeanUtils.copyProperties(tag, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void checkName(Long id, String name) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Tag::getName, name);
        if (id != null) {
            wrapper.lambda().ne(Tag::getId, id);
        }
        Tag tag = this.getOne(wrapper);
        if (tag != null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("存在相同名称的标签"));
        }
    }

    @Autowired
    public void setMaterialService(IMaterialService materialService) {
        this.materialService = materialService;
    }
}
