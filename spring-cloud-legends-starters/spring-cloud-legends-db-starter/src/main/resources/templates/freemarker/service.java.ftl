package ${package.Service};

import ReqPage;
import ${package.Entity}.${entity};
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
    * @param param
    * @return
    */
    List<${entity}> listByEntity(${entity} param);

    /**
    * ${table.comment!}详情
    * @param id
    * @return
    */
    ${entity} loadById(Long id);

    /**
    * ${table.comment!}新增
    * @param param 根据需要进行传值
    * @return
    */
    void add(${entity} param);

    /**
    * ${table.comment!}修改
    * @param param 根据需要进行传值
    * @return
    */
    void modifyById(${entity} param);

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