package com.open.capacity.user.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.List;

@Data
public class UserExcelModel extends BaseRowModel {
    @ExcelProperty(value = "ID",index = 1)
    private int id;

    @ExcelProperty(value = "姓名",index = 2)
    private String name;

    private List<RoleModel> roles;
    @ExcelProperty(value = "角色名",index = 3)
    private String roleName;

    @ExcelProperty(value = "角色编码",index = 4)
    private String roleCode;

}

