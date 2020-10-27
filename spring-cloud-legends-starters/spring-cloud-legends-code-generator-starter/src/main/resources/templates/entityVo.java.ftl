package ${package.Vo};

import BaseEntity;
import java.io.Serializable;

<#if isImportDate>
import java.util.Date;
</#if>
<#if isImportBigDecimal>
import java.math.BigDecimal;
</#if>

<#if swagger2>
import io.swagger.annotations.ApiModel;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
<#if chainModel>
import lombok.experimental.Accessors;
</#if>
</#if>

/**
* <p>
* ${table.comment!}
* </p>
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
@Data
<#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
</#if>
<#if chainModel>
@Accessors(chain = true)
</#if>
</#if>

<#if swagger2>
@ApiModel(value="${vo} 对象", description="${table.comment!}")
</#if>

public class ${vo} extends BaseEntity  implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.propertyName !="createTime" && field.propertyName !="updateTime"
    && field.propertyName !="createUser" && field.propertyName !="updateUser">
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
    return ${field.propertyName};
    }

        <#if chainModel>
        public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        <#else>
        public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if !entityLombokModel>
@Override
public String toString() {
return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
        "${field.propertyName}=" + ${field.propertyName} +
        <#else>
        ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
"}";
}
</#if>
}
