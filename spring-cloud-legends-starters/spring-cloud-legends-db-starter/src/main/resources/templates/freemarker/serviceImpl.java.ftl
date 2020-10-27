package ${package.ServiceImpl};

import com.legends.cloud.common.enums.CommonEnumCode;
import com.legends.cloud.common.exception.BizException;
import ReqPage;
import PageUtils;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private PageUtils pageUtils;

    /**
    * ${table.comment!}分页列表
    * @param reqPage 根据需要进行传值
    * @return
    */
    @Override
    public IPage<${entity}> page(ReqPage<${entity}> reqPage) {
        return page(pageUtils.page(reqPage.getPage(),reqPage.getSize(),reqPage.isSort(),reqPage.getSortName()), getBuildQueryWrapper(reqPage.getData()));
    }

    private QueryWrapper<${entity}> getBuildQueryWrapper(${entity} param) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            <#list table.fields as field>
            // ${field.comment}
                <#if !entityLombokModel>
                    <#if field.propertyType == "Boolean">
                        <#assign getprefix="is"/>
                    <#else>
                        <#assign getprefix="get"/>
                    </#if>
                    <#if field.propertyType == "String">
                    .eq(!StringUtils.isEmpty(param.${getprefix}${field.capitalName}()), ${entity}::${getprefix}${field.capitalName}, param.${getprefix}${field.capitalName}())
                    <#else>
                    .eq(param.${getprefix}${field.capitalName}() != null, ${entity}::${getprefix}${field.capitalName}, param.${getprefix}${field.capitalName}())
                    </#if>
                <#else>
                    <#if field.propertyType == "String">
                    .eq(!StringUtils.isEmpty(param.get${field.capitalName}()), ${entity}::get${field.capitalName}, param.get${field.capitalName}())
                    <#else>
                    .eq(param.get${field.capitalName}() != null, ${entity}::get${field.capitalName}, param.get${field.capitalName}())
                    </#if>
                </#if>
            </#list>;
        return queryWrapper;
    }

    /**
    * 构建列表
    * @param param
    * @return
    */
    @Override
    public List<${entity}> listByEntity(${entity} param) {
        return list(getBuildQueryWrapper(param));
    }

    /**
    * ${table.comment!}详情
    * @param id
    * @return
    */
    @Override
    public ${entity} loadById(Long id) {
        return getById(id);
    }

    /**
    * ${table.comment!}新增
    * @param param 根据需要进行传值
    * @return
    */
    @Override
    public void add(${entity} param) {
        param.setCreateTime(new Date());
        if (!save(param)) {
            throw new BizException(CommonEnumCode.FAIL);
        }
    }

    /**
    * ${table.comment!}修改
    * @param param 根据需要进行传值
    * @return
    */
    @Override
    public void modifyById(${entity} param) {
        param.setUpdateTime(new Date());
        if (!updateById(param)) {
        throw new BizException(CommonEnumCode.FAIL);
        }
    }

    /**
    * ${table.comment!}删除(单个条目)
    * @param id
    * @return
    */
    @Override
    public void deleteById(Long id) {
        removeById(id);
    }

    /**
    * ${table.comment!}删除(多个条目)
    * @param ids
    * @return
    */
    @Override
    public void deleteByIds(List<Long> ids) {
        removeByIds(ids);
    }
}
</#if>