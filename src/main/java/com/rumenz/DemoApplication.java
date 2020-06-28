package com.rumenz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
        RumenzA rumenzA=(RumenzA)ca.getBean("rumenz-by-factoryBean");
    }

}
