package ${package.Service};

import top.legendscloud.common.base.ReqPage;
import ${package.Entity}.${entity};
import ${package.Dto}.${entityDto};
import ${package.UpdDto}.${entityUpdDto};
import ${package.AddDto}.${entityAddDto};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * ${table.comment!}分页列表
    * @param reqPage 根据需要进行传值
    * @return
    */
    IPage<${entity}> page(ReqPage<${entity}> reqPage);

    /**
    * ${table.comment!}列表
    * @param ${entityDto?uncap_first}
    * @return
    */
    List<${entity}> listByEntity(${entityDto} ${entityDto?uncap_first});

    /**
    * ${table.comment!}详情
    * @param id
    * @return
    */
    ${entity} loadById(Long id);

    /**
    * ${table.comment!}新增
    * @param ${entityAddDto?uncap_first} 根据需要进行传值
    * @return
    */
    void add(${entityAddDto} ${entityAddDto?uncap_first});

    /**
    * ${table.comment!}修改
    * @param ${entityUpdDto?uncap_first} 根据需要进行传值
    * @return
    */
    void modifyById(${entityUpdDto} ${entityUpdDto?uncap_first});

    /**
    * ${table.comment!}删除(单个条目)
    * @param id
    * @return
    */
    void deleteById(Long id);

    /**
    * 删除(多个条目)
    * @param ids
    * @return
    */
    void deleteByIds(List<Long> ids);
 }
</#if>