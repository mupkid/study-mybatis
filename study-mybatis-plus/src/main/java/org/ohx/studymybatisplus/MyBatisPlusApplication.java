package org.ohx.studymybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 *
 * @author mudkip
 * @date 2022/5/9
 */
@SpringBootApplication
public class MyBatisPlusApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MyBatisPlusApplication.class, args);

        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
