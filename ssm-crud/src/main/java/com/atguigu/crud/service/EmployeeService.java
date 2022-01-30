package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("employeeService")
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    public List<Employee> getEmps(){
        List<Employee> employeeList = employeeMapper.selectByExampleWithDept(null);
        return employeeList;
    }
    public int saveEmp(Employee employee){
        return employeeMapper.insertSelective(employee);
    }
    public boolean checkName(String name) {
        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(name);
        long l = employeeMapper.countByExample(employeeExample);
        //数量等于0表示当前数据库无此名，可用
        return l==0;
    }

    public Employee getEmp(Integer id) {
        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdEqualTo(id);
        List<Employee> employees = employeeMapper.selectByExampleWithDept(employeeExample);
        Employee employee = employees.get(0);
        return employee;
    }

    public void updateEmp(Employee employee) {
        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdEqualTo(employee.getEmpId());
        employeeMapper.updateByExampleSelective(employee,employeeExample);
    }

    public void deleteEmp(Integer id) {
        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdEqualTo(id);
        employeeMapper.deleteByExample(employeeExample);
    }

    public void deleteEmps(String[] str_ids) {
        List<Integer> list_ids=new ArrayList<>();
        for(String id:str_ids){
            list_ids.add(Integer.parseInt(id));
        }
        EmployeeExample employeeExample=new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdIn(list_ids);
        employeeMapper.deleteByExample(employeeExample);
    }
}
