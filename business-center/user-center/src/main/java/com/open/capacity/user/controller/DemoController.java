package com.open.capacity.user.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.discovery.converters.Auto;
import com.open.capacity.common.model.SysRole;
import com.open.capacity.common.model.SysUser;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.user.model.MergeExcelCellMode;
import com.open.capacity.user.model.RoleModel;
import com.open.capacity.user.model.UserExcelModel;
import com.open.capacity.user.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "Demo API")
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + new String("用户角色".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);

        Sheet sheet = new Sheet(1, 0, UserExcelModel.class);
        List<UserExcelModel> userExcelModels = createTestJavaModelByJdbc();
        List<UserExcelModel> newMode = Lists.newArrayList();

        int i = 1;

        List<MergeExcelCellMode> mergeExcelCellModeList = Lists.newArrayList();

        for (UserExcelModel u : userExcelModels) {
            if (!CollectionUtils.isEmpty(u.getRoles())) {
                for (RoleModel roleModel : u.getRoles()) {
                    UserExcelModel excelModel = new UserExcelModel();
                    BeanUtils.copyProperties(u, excelModel);
                    excelModel.setRoleName(roleModel.getRoleName());
                    excelModel.setRoleCode(roleModel.getRoleCode());
                    newMode.add(excelModel);
                }
                if (u.getRoles().size() > 1) {
                    MergeExcelCellMode mergeExcelCellMode = new MergeExcelCellMode(i, i + u.getRoles().size() - 1, 0, 0);
                    MergeExcelCellMode mergeExcelCellMode2 = new MergeExcelCellMode(i, i + u.getRoles().size() - 1, 1, 1);
                    mergeExcelCellModeList.add(mergeExcelCellMode);
                    mergeExcelCellModeList.add(mergeExcelCellMode2);
                 //   i=i-u.getRoles().size() +1;
                    i=i+u.getRoles().size();
                }else {
                    i++;
                }
            } else {
                newMode.add(u);
                i++;
            }

        }
        writer.write(newMode, sheet);
        mergeExcelCellModeList.forEach(e -> writer.merge(e.getFirstRow(), e.getLastRow(), e.getFirstCol(), e.getLastCol()));
        writer.finish();
    }

    private List<UserExcelModel> createTestJavaModel() {

        List<UserExcelModel> userExcelModels = Lists.newArrayList();

        UserExcelModel userExcelModel1 = new UserExcelModel();
        userExcelModel1.setId(1);
        userExcelModel1.setName("张三");
        userExcelModel1.setRoleCode("ADMIN");
        userExcelModel1.setRoleName("系统管理员");
        userExcelModels.add(userExcelModel1);


        UserExcelModel userExcelModel2 = new UserExcelModel();
        userExcelModel2.setId(2);
        userExcelModel2.setName("李四");

        List<RoleModel> roleModelList2 = Lists.newArrayList();

        RoleModel roleModel2 = new RoleModel();
        roleModel2.setRoleCode("PERSON");
        roleModel2.setRoleName("普通用户");
        roleModelList2.add(roleModel2);

        RoleModel roleModel3 = new RoleModel();
        roleModel3.setRoleCode("ADMIN");
        roleModel3.setRoleName("系统管理员");
        roleModelList2.add(roleModel3);
        userExcelModel2.setRoles(roleModelList2);

        userExcelModels.add(userExcelModel2);


        return userExcelModels;
    }

    private List<UserExcelModel> createTestJavaModelByJdbc() {
        List<UserExcelModel> userExcelModels = Lists.newArrayList();

        System.out.println(1);
        Map<String, Object> params = Maps.newHashMap();
        params.put("page", 1);
        params.put("limit", 20);
        List<SysUser> users = sysUserService.findUsers(params).getData();
        for (SysUser sysUser : users) {
            UserExcelModel model = new UserExcelModel();
            model.setId(sysUser.getId().intValue());
            model.setName(sysUser.getUsername());

            List<RoleModel> roleModels = Lists.newArrayList();
            for (SysRole role : sysUser.getRoles()) {
                RoleModel roleModel = new RoleModel(role.getCode(), role.getName());
                roleModels.add(roleModel);
            }
            model.setRoles(roleModels);
            userExcelModels.add(model);
        }

        return userExcelModels;
    }

}
