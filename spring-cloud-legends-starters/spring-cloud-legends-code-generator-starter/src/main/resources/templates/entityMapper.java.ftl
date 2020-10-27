package ${package.ConvertMapper};

import ${package.Dto}.${entityDto};
import ${package.UpdDto}.${entityUpdDto};
import ${package.AddDto}.${entityAddDto};
import ${package.Entity}.${entity};
import ${package.Vo}.${vo};
import org.mapstruct.Mapper;
import java.util.List;

/**
* <p>
* ${table.comment!}
* </p>
* @author ${author}
* @since ${date}
*/
@Mapper(componentModel = "spring")
public interface ${entityMapper} {

    /**
    * ${table.comment!} entity 转 ${table.comment!} dto
    * @param ${entity?uncap_first}
    * @return
    */
    ${entityDto} ${entity?uncap_first}EntityToDto(${entity} ${entity?uncap_first});

    /**
    * ${table.comment!} entity list 转 ${table.comment!} dto list
    * @param ${entity?uncap_first}s
    * @return
    */
    List<${entityDto}> ${entity?uncap_first}EntityToDtos(List<${entity}> ${entity?uncap_first}s);


    /**
    * ${table.comment!} dto 转 ${table.comment!} entity
    * @param ${entityDto?uncap_first}
    * @return
    */
    ${entity} ${entityDto?uncap_first}ToEntity(${entityDto} ${entityDto?uncap_first});


    /**
    * ${table.comment!} 新增 dto 转 ${table.comment!} entity
    * @param ${entityAddDto?uncap_first}
    * @return
    */
    ${entity} ${entityDto?uncap_first}ToEntity(${entityAddDto} ${entityAddDto?uncap_first});


    /**
    * ${table.comment!} 修改 dto 转 ${table.comment!} entity
    * @param ${entityUpdDto?uncap_first}
    * @return
    */
    ${entity} ${entityDto?uncap_first}ToEntity(${entityUpdDto} ${entityUpdDto?uncap_first});


    /**
    * ${table.comment!} dto list 转 ${table.comment!} entity list
    * @param ${entityDto?uncap_first}s
    * @return
    */
    List<${entity}> ${entityDto?uncap_first}ToEntitys(List<${entityDto}> ${entityDto?uncap_first}s);

    /**
    * ${table.comment!} entity 转 ${table.comment!} vo
    * @param ${entity?uncap_first}
    * @return
    */
    ${vo} ${entity?uncap_first}EntityToVo(${entity} ${entity?uncap_first});


    /**
    * ${table.comment!} entity list 转 ${table.comment!} vo list
    * @param ${entity?uncap_first}s
    * @return
    */
    List<${vo}> ${entity?uncap_first}EntityToVos(List<${entity}> ${entity?uncap_first}s);

    /**
    * ${table.comment!} vo 转 ${table.comment!} entity
    * @param ${vo?uncap_first}
    * @return
    */
    ${entity} ${vo?uncap_first}ToEntity(${vo} ${vo?uncap_first});


    /**
    * ${table.comment!} vo list 转 ${table.comment!} entity list
    * @param ${vo?uncap_first}s
    * @return
    */
    List<${entity}> ${vo?uncap_first}ToEntitys(List<${vo}> ${vo?uncap_first}s);

}