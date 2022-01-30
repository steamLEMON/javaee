package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /*传统方法查询所有员工*/
//    @RequestMapping(value = "/")
//    public String getEmps(Model model, @RequestParam(value = "pn",defaultValue = "1")Integer pn){
//        PageHelper.startPage(pn,5);
//        List<Employee> employeeList = employeeService.getEmps();
//        model.addAttribute("emps",employeeList);
//        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList,5);
//        model.addAttribute("pageInfo",pageInfo);
//        return "index";
//    }
    //使用json返回所有员工信息
    @RequestMapping(value = "/emps")
    @ResponseBody
    //@ResponseBody注解需要jackson依赖
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn, HttpServletResponse resp){
        PageHelper.startPage(pn,5);
        List<Employee> employeeList = employeeService.getEmps();
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList,5);
        Cookie cookie=new Cookie("pn",pn.toString());
        cookie.setMaxAge(3600);
        resp.addCookie(cookie);
        return Msg.success().add("pageInfo",pageInfo);
    }

    /**保存员工信息
     * 支持JSR303校验
     * 首先导入hibernate-validator依赖
     */
    @RequestMapping(
            value = "/emp",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        //在有校验项的对象前加@Valid注解，BindingResult类用于返回校验失败的错误信息
        if(result.hasErrors()){
            Map<String,Object> map=new HashMap<String,Object>();
            //校验失败，应返回失败，在模态框中显示校验失败
            List<FieldError> errors = result.getFieldErrors();
            //得到所有字段的错误信息
            for(FieldError error:errors){
                System.out.println("错误的字段为："+error.getField());
                System.out.println("错误信息："+error.getDefaultMessage());
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }
    //检验用户名是否重复
    @RequestMapping(value = "/checkName")
    @ResponseBody
    public Msg checkName(String name){
        boolean b= employeeService.checkName(name);
        if(b==true){
            return Msg.fail();
        }else {
            return Msg.success();
        }
    }

    /*按照员工id查询员工*/
    @RequestMapping(
            value = "/emp/{id}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee=employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    /*修改员工*/
    @ResponseBody
    @RequestMapping(
            value = "/emp/{id}",
            method = RequestMethod.PUT
    )
    public Msg updateEmp(@PathVariable("id") Integer id, Employee employee){
        employee.setEmpId(id);
        employeeService.updateEmp(employee);
        return Msg.success();
    }
    //删除单个或多个员工
    @RequestMapping(
            value = "/emp/{ids}",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    public Msg deleteEmp(@PathVariable("ids")String ids){
        if(ids.contains("-")){
            String[] str_ids = ids.split("-");
            employeeService.deleteEmps(str_ids);
        }else {
            int id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
       return Msg.success();
    }

}
