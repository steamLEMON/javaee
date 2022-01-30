import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.controller.EmployeeController;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;
import com.atguigu.crud.service.EmployeeService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

/*
 * 推荐spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 * 1.导入springTest依赖；
 * 2.测试类标上注解@ContextConfiguration
 *   属性locations:string类型的数组，填spring配置文件的位置；
 * 3.测试类标上注解@RunWith(),括号里面填spring单元测试类的运行时类
 *  (junit4填SpringJUnit4ClassRunner.class；junit5填SpringExtension.class；)
 * 4.可以直接用@Autowired注解了SpringExtension.class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class MapperTest {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;
    @Autowired
    EmployeeService employeeService;
    //测试dao层
    @Test
    /**
     * 测试DepartmentMapper
     */
    public void test01() {
//        System.out.println(departmentMapper);
        //1.插入部门
//        departmentMapper.insert(new Department(1,"步兵连"));
//        Department department=new Department();
//        department.setDeptName("炮兵指挥部");
//        departmentMapper.insertSelective(department);
        //2.插入员工
//        employeeMapper.insert(new Employee(1, "张小斐", "o", "email.@com", 2, null));
        Employee employee = new Employee();
        employee.setdId(1);
        employee.setEmpName("罗地");
        employee.setGender("x");
        int i = employeeMapper.insertSelective(employee);
        System.out.println(i);
//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        String[] xing={"赵","李","周","张","章","刘","牛","马","邢","南"};
//        String[] ming={"伊曼","方","非","灵儿","毕","君","心凌","中","能","零"};
//        for(int i1=0;i1<10;i1++){
//            for (int i=0;i<10;i++){
//                String uid= UUID.randomUUID().toString().substring(0,5)+(i*i1);
//                String gender=new String();
//                if(i*i1/2==0){
//                    gender="o";
//                }else {
//                    gender="x";
//                }
//                mapper.insertSelective(new Employee(null,xing[i1]+ming[i],gender,uid+"@163.com",(i*i1)%2+1,null));
//            }
//        }
    }
    @Test
    public void test02(){
        List<Employee> emps = employeeService.getEmps();
        for (Employee e:emps){
            System.out.println(e.toString());
        }
    }
    @Test
    public void test03(){
        Employee employee = new Employee();
        employee.setdId(1);
        employee.setEmpName("罗中");
        employee.setGender("x");
        int i = employeeService.saveEmp(employee);
        System.out.println("最终结果是："+i);
    }
}
