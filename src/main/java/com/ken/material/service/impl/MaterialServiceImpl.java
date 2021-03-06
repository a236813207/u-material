package com.ken.material.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.exception.BizException;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.admin.query.AdminMaterialQueryParam;
import com.ken.material.controller.web.query.MaterialIndexQueryParam;
import com.ken.material.entity.Material;
import com.ken.material.entity.User;
import com.ken.material.enums.MaterialStatus;
import com.ken.material.mapper.MaterialMapper;
import com.ken.material.service.IMaterialService;
import com.ken.material.service.IUserService;
import com.ken.material.utils.ImageUtils;
import com.ken.material.utils.oss.OSSManager;
import com.ken.material.vo.AdminMaterialAddVo;
import com.ken.material.vo.MaterialAddVo;
import com.ken.material.vo.MaterialIndexVo;
import com.ken.material.vo.MaterialListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 素材表 服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
@Slf4j
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    private IUserService userService;
    private OSSManager ossManager;

    private @Value("${aliyun.oss.bucket}")
    String bucketsName;

    @Override
    public PageVo<MaterialListVo> searchPage(AdminMaterialQueryParam queryParam) {
        Page<Material> page = new Page<>(queryParam.getPage(), queryParam.getRows());
        page.setOrders(Lists.newArrayList(OrderItem.desc("create_time")));
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(queryParam.getUsername())) {
            wrapper.lambda().eq(Material::getUsername, queryParam.getUsername());
        }
        if (StringUtils.hasText(queryParam.getBrand())) {
            wrapper.lambda().like(Material::getBrand, queryParam.getUsername());
        }
        if (StringUtils.hasText(queryParam.getKeywords())) {
            wrapper.lambda().like(Material::getKeywords, queryParam.getUsername());
        }
        if (queryParam.getStatus() != null) {
            wrapper.lambda().like(Material::getStatus, MaterialStatus.getByValue(queryParam.getStatus()));
        }
        if (queryParam.getStartTime() != null) {
            wrapper.lambda().ge(Material::getCreateTime, queryParam.getStartTime());
        }
        if (queryParam.getEndTime() != null) {
            wrapper.lambda().le(Material::getCreateTime, queryParam.getStartTime());
        }
        Page<Material> materialPage = this.page(page, wrapper);
        if (CollectionUtils.isEmpty(materialPage.getRecords())) {
            return PageVo.create(queryParam.getPage(), queryParam.getRows(), 0, Lists.newArrayList());
        }
        List<MaterialListVo> result = materialPage.getRecords().stream().map(material -> {
            MaterialListVo vo = new MaterialListVo();
            BeanUtils.copyProperties(material, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageVo.create(queryParam.getPage(), queryParam.getRows(), materialPage.getTotal(), result);
    }

    @Override
    public PageVo<MaterialIndexVo> findIndexList(MaterialIndexQueryParam queryParam) {
        Page<Material> page = new Page<>(queryParam.getCurrent(), queryParam.getPageSize());
        page.setOrders(Lists.newArrayList(OrderItem.desc("downloads"), OrderItem.desc("create_time")));
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        wrapper.select("id", "brand", "url");
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
            vo.setBrand(material.getBrand());
            vo.setUrl(material.getUrl());
            return vo;
        }).collect(Collectors.toList());
        return PageVo.create(queryParam.getCurrent(), queryParam.getPageSize(), materialPage.getTotal(), list);
    }


    @Override
    public void add(MaterialAddVo addVo, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("登录过期");
        }
        Material material = new Material();
        BeanUtils.copyProperties(addVo, material);
        String svgUrl = material.getUrl();
        String url = svg2Jpg(svgUrl);
        material.setStatus(MaterialStatus.WAIT_AUDIT);
        String fileExt = FileUtil.extName(material.getUrl());
        material.setFileFormat(fileExt);
        material.setUrl(url);
        material.setSvgUrl(svgUrl);
        User user = this.userService.getById(userId);
        material.setUserId(userId);
        material.setUsername(user.getUsername());
        if(!this.save(material)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("上传失败！"));
        }
    }

    @Override
    public void adminAdd(AdminMaterialAddVo addVo) {
        Material material = new Material();
        BeanUtils.copyProperties(addVo, material);
        String svgUrl = material.getUrl();
        String url = svg2Jpg(svgUrl);
        material.setStatus(MaterialStatus.WAIT_AUDIT);
        String fileExt = FileUtil.extName(svgUrl);
        material.setFileFormat(fileExt);
        material.setUrl(url);
        material.setSvgUrl(svgUrl);
        User user = this.userService.findByUsername(addVo.getUsername());
        material.setUserId(user.getId());
        this.save(material);
    }

    @Override
    public void adminUpdate(AdminMaterialAddVo addVo) {
        Material material = new Material();
        BeanUtils.copyProperties(addVo, material);
        User user = this.userService.findByUsername(addVo.getUsername());
        material.setUserId(user.getId());
        this.updateById(material);
    }

    @Override
    public void updateStatus(Long id, int status) {
        Material material = this.getById(id);
        if (material == null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL));
        }
        MaterialStatus materialStatus = MaterialStatus.getByValue(status);
        if (materialStatus == null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("状态错误"));
        }
        material.setStatus(materialStatus);
        this.updateById(material);
    }

    /**
     * svg转jpg
     * @param svgUrl svg图片地址
     * @return jpg图片地址
     */
    private String svg2Jpg(String svgUrl) {
        if (!StringUtils.hasText(svgUrl)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("图片不能为空"));
        }
        String fileName = UUID.randomUUID().toString() + ".svg";
        String savePath = System.getProperty("java.io.tmpdir");
        String filePath = savePath + File.separator + fileName;
        try {
            ImageUtils.download(svgUrl, fileName, savePath);
            if (!new File(filePath).exists()) {
                throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL));
            }
            String jpgFilePath = savePath + File.separator + UUID.randomUUID().toString() + ".jpg";
            File jpgFile = new File(jpgFilePath);
            ImageUtils.svg2Jpg(new File(filePath), jpgFile);
            if (!jpgFile.exists()) {
                throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL));
            }
            log.info(jpgFilePath);
            return this.ossManager.putObject("material/jpg/", bucketsName, jpgFile);
        } catch (Exception e) {
            log.error("svg2Jpg error [{}]", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Material> countByTagId(Long tagId) {
        return this.getBaseMapper().countByTagId(tagId);
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOssManager(OSSManager ossManager) {
        this.ossManager = ossManager;
    }
}
