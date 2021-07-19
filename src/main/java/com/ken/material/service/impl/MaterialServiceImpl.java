package com.ken.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.web.query.MaterialIndexQueryParam;
import com.ken.material.entity.Material;
import com.ken.material.enums.MaterialStatus;
import com.ken.material.mapper.MaterialMapper;
import com.ken.material.service.IMaterialService;
import com.ken.material.vo.MaterialAddVo;
import com.ken.material.vo.MaterialIndexVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 素材表 服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    @Override
    public PageVo<MaterialIndexVo> findIndexList(MaterialIndexQueryParam queryParam) {
        Page<Material> page = new Page<>(queryParam.getCurrent(), queryParam.getPageSize());
        page.setOrders(Lists.newArrayList(OrderItem.desc("downloads"), OrderItem.desc("create_time")));
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "url");
        wrapper.lambda().eq(Material::getStatus, MaterialStatus.VALID);
        if (StringUtils.hasText(queryParam.getKey())) {
            wrapper.lambda().like(Material::getBrand, queryParam.getKey())
                    .or().like(Material::getTags, queryParam.getKey())
                    .or().like(Material::getKeywords, queryParam.getKey());
        }
        Page<Material> materialPage = this.page(page, wrapper);
        if (CollectionUtils.isEmpty(materialPage.getRecords())) {
            return PageVo.create(queryParam.getCurrent(), queryParam.getPageSize(), 0, Lists.newArrayList());
        }
        List<MaterialIndexVo> list = materialPage.getRecords().stream().map(material -> {
            MaterialIndexVo vo = new MaterialIndexVo();
            vo.setId(material.getId());
            vo.setName(material.getName());
            vo.setUrl(material.getUrl());
            return vo;
        }).collect(Collectors.toList());
        return PageVo.create(queryParam.getCurrent(), queryParam.getPageSize(), materialPage.getTotal(), list);
    }


    @Override
    public void add(MaterialAddVo addVo) {
        Material material = new Material();
        BeanUtils.copyProperties(addVo, material);
        this.save(material);
    }
}
