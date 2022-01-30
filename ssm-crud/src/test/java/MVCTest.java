import com.atguigu.crud.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用spring测试模块提供的测试请求功能，测试crud请求的正确性
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration/*ioc容器可以自动注入ioc容器里面的，但ioc容器自己（WebApplicationContext）
                            不能自动注入，所以这里需要一个注解@WebAppConfiguration*/
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:springMVC.xml"})
//传入spring和springmvc的配置文件
public class MVCTest {
    //传入SpringMVC的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟mvc请求，获取到处理结果
    MockMvc mockMvc;
    @Before
    public void initMokcMvc(){
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void test01() throws Exception {
        //携带参数模拟请求并拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/").param("pn", "16")).andReturn();
        //请求成功后，从请求域拿到数据
        MockHttpServletRequest request = result.getRequest();
        List<Employee> emps = (List<Employee>) request.getAttribute("emps");
        System.out.println(emps.get(0).toString());
    }
    @Test
    public void test02() throws Exception {
        List<Integer> s=new ArrayList<>();
        s.add(6);
        System.out.println(s.get(0));
    }
}
