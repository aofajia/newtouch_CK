package com.ruoyi;

import com.google.common.collect.Lists;
import com.ruoyi.system.domain.Employee;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.List;

/**
 * web容器中进行部署
 * 
 * @author ruoyi
 */
public class RuoYiServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(RuoYiApplication.class);
    }

    public static void main(String []args){

        Employee employee = new Employee();

        List<Employee> list = Lists.newArrayList();
        for (int i = 0;i < 5;i++){
            employee.setName("I"+i);

            list.add(employee);
        }

        list.forEach(a->{
            System.out.println(a.getName());
        });

    }
}
