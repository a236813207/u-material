package com.ken.material.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ken.material.entity.SysUser;
import com.ken.material.interceptor.MybatisPaginationInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Configuration
@MapperScan({"com.ken.material.mapper"})
public class MybatisPlusConf {

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return mybatisPlusProperties -> {
            String typeEnumsPackage = mybatisPlusProperties.getTypeEnumsPackage();
            if (StringUtils.isBlank(typeEnumsPackage)) {
                //设置默认枚举包路径
                mybatisPlusProperties.setTypeEnumsPackage("**.enums");
            }
            //设置默认逻辑删除配置
            GlobalConfig.DbConfig dbConfig = mybatisPlusProperties.getGlobalConfig().getDbConfig();
            dbConfig.setLogicDeleteField("deleted");
            dbConfig.setLogicDeleteValue("1");
            dbConfig.setLogicNotDeleteValue("0");
        };
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new MybatisPaginationInterceptor();
    }

    /**
     * mybatis-plus乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                String username = sysUser != null?sysUser.getUsername():"system";
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "createBy", String.class, username);
                this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "modifyBy", String.class, username);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                String username = sysUser != null?sysUser.getUsername():"";
                this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "modifyBy", String.class, username);
            }
        };
    }

}
