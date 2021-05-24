package ${package.ServiceImpl};

CommonEnumCode;
BizException;
import ReqPage;
import PageUtils;
import ${package.Entity}.${entity};
import ${package.Dto}.${entityDto};
import ${package.UpdDto}.${entityUpdDto};
import ${package.AddDto}.${entityAddDto};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${package.ConvertMapper}.${entityMapper};
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

    @Autowired
    private ${entityMapper} ${entityMapper?uncap_first};

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
    * ${table.comment!}列表
    * @param ${entityDto?uncap_first}
    * @return
    */
    @Override
    public List<${entity}> listByEntity(${entityDto} ${entityDto?uncap_first}) {
        ${entity} ${entity?uncap_first} =${entityMapper?uncap_first}.${entityDto?uncap_first}ToEntity(${entityDto?uncap_first});
        return list(getBuildQueryWrapper(${entity?uncap_first}));
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
    * @param ${entityAddDto?uncap_first} 根据需要进行传值
    * @return
    */
    @Override
    public void add(${entityAddDto} ${entityAddDto?uncap_first}) {
        ${entity} ${entity?uncap_first} =${entityMapper?uncap_first}.${entityDto?uncap_first}ToEntity(${entityAddDto?uncap_first});
        ${entity?uncap_first}.setCreateTime(new Date());
        if (!save(${entity?uncap_first})) {
            throw new BizException(CommonEnumCode.FAIL);
        }
    }

    /**
    * ${table.comment!}修改
    * @param ${entityUpdDto?uncap_first} 根据需要进行传值
    * @return
    */
    @Override
    public void modifyById(${entityUpdDto} ${entityUpdDto?uncap_first}) {
        ${entity} ${entity?uncap_first} =${entityMapper?uncap_first}.${entityDto?uncap_first}ToEntity(${entityUpdDto?uncap_first});
        ${entity?uncap_first}.setUpdateTime(new Date());
        if (!updateById(${entity?uncap_first})) {
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