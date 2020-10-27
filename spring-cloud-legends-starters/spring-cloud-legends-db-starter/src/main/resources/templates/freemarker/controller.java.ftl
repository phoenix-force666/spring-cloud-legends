package ${package.Controller};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ReqPage;
import ComReq;
import BaseDelDTO;
import BaseDelsDTO;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ComResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

<#if restControllerStyle>
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>


import javax.validation.Valid;
import java.util.List;

/**
* <p>
    * ${table.comment} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
    public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
    public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @ApiOperation(value = "${table.comment}分页列表",httpMethod = "POST")
    @PostMapping(value = "/page")
    public   ComResp<IPage<${entity}>> page (@Valid @RequestBody ComReq<ReqPage<${entity}>> comReq) {
        return  new ComResp.Builder().fromReq(comReq).data(${table.serviceName?uncap_first}.page(comReq.getData())).success().build();
    }

    @ApiOperation(value = "${table.comment}构建列表",httpMethod = "POST")
    @PostMapping(value = "/list")
    public   ComResp<List<${entity}>> listByEntity (@Valid @RequestBody ComReq<${entity}> comReq) {
        return  new ComResp.Builder().fromReq(comReq).data(${table.serviceName?uncap_first}.listByEntity(comReq.getData())).success().build();
    }

    @ApiOperation(value = "${table.comment}详情",httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public  ComResp<${entity}> loadById(@PathVariable Long id) {
        return new ComResp.Builder().data(${table.serviceName?uncap_first}.loadById(id)).success().build();
    }

    @ApiOperation(value = "${table.comment}新增",httpMethod = "POST")
    @PostMapping(value = "/add")
    public  ComResp add(@Valid @RequestBody ComReq<${entity}> comReq) {

        ${table.serviceName?uncap_first}.add(comReq.getData());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }


    @ApiOperation(value = "${table.comment}修改" ,httpMethod = "POST")
    @PostMapping(value = "/modifyById")
    public  ComResp modifyById(@Valid @RequestBody ComReq<${entity}> comReq) {

        ${table.serviceName?uncap_first}.modifyById(comReq.getData());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "${table.comment}删除(单个条目)",httpMethod = "POST")
    @PostMapping(value = "/deleteById")
    public  ComResp remove(@Valid @RequestBody ComReq<BaseDelDTO> comReq) {

        ${table.serviceName?uncap_first}.deleteById(comReq.getData().getId());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "${table.comment}删除(多个条目)",httpMethod = "POST")
    @PostMapping(value = "/deleteByIds")
    public  ComResp deleteByIds(@Valid @RequestBody ComReq<BaseDelsDTO> comReq) {

        ${table.serviceName?uncap_first}.deleteByIds(comReq.getData().getIds());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }

}
</#if>