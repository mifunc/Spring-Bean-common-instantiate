- 通过构造方法实例化
- 通过静态工厂实例化
- 通过实例工厂实例化
- 通过FactoryBean实例化

**RumenzA实体类**

```java
package com.rumenz;
public class RumenzA {

    private String id;
    private String name;
    public  RumenzA() {
        System.out.println("RumenzA 无参构造方法");
    }
    public RumenzA(String id) {
        this.id = id;
        System.out.println("ID构造方法");
    }
    // set get省略
}

```


## 构造方法

**beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
<bean id="rumenz" class="com.rumenz.RumenzA" />
<bean id="rumenz1" class="com.rumenz.RumenzA">
     <constructor-arg name="id" value="1"/>
</bean>
</beans>
```

**DemoApplication.java**

```java
package com.rumenz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
        RumenzA rumenzA=(RumenzA)ca.getBean("rumenz");
    }

}
```
**输出**

```
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rumenz'
RumenzA 无参构造方法
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rumenz1'
ID构造方法
```

## 静态工厂

**beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

<bean id="rFactory" class="com.rumenz.RumenzFactory" factory-method="rumenzFactory"/>
<bean id="rFactory1" class="com.rumenz.RumenzFactory" factory-method="rumenzFactory">
    <constructor-arg name="id" value="111"/>
</bean>
</beans>
```
**RumenzFactory工厂类**

```java
package com.rumenz;
public class RumenzFactory {
    
    //静态方法
    public static RumenzA rumenzFactory(){
        return new RumenzA();
    }
    public static RumenzA rumenzFactory(String id){
        return new RumenzA(id);
    }

}
```

**DemoApplication.java**

```
package com.rumenz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
    }

}
```

**输出**

```
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rFactory'
RumenzA 无参构造方法
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rFactory1'
ID构造方法
```

## 实例工厂实例化

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean  id="rFactory" class="com.rumenz.RumenzFactory" />
    <bean id="rumenz" factory-bean="rFactory" factory-method="rumenzFactory"></bean>
    <bean id="rumenz1" factory-bean="rFactory" factory-method="rumenzFactory">
        <constructor-arg name="id" value="666"></constructor-arg>
    </bean>
</beans>
```

**RumenzFactory.java**

```java

package com.rumenz;
public class RumenzFactory {
    //不能用静态方法
    public  RumenzA rumenzFactory(){
        return new RumenzA();
    }
    public  RumenzA rumenzFactory(String id){
        return new RumenzA(id);
    }
}
```

**DemoApplication.java**

```
package com.rumenz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
        //RumenzA rumenzA=(RumenzA)ca.getBean("rFactory1");
    }

}
```

**输出**

```
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rumenz'
RumenzA 无参构造方法
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rumenz1'
ID构造方法
```

## 通过FactoryBean实例化

**beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="rumenz-by-factoryBean"  class="com.rumenz.RumenzAFactoryBean"/>
</beans>
```

**RumenzAFactoryBean.java**

```java
package com.rumenz;

import org.springframework.beans.factory.FactoryBean;

public class RumenzAFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return RumenzA.createRumenzA();
    }
    @Override
    public Class<?> getObjectType() {
        return RumenzA.class;
    }
}

```

**DemoApplication.java**

```java
package com.rumenz;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
        RumenzA rumenzA=(RumenzA)ca.getBean("rumenz-by-factoryBean");
    }

}
```

**输出/异步加载bean**

```
xxx.DefaultListableBeanFactory - Creating shared instance of singleton bean 'rumenz-by-factoryBean'
RumenzA 无参构造方法
```
![图片alt](https://rumenz.com/static/cimg/20200628/1593359495.png ''图片title'')

源码 : https://github.com/mifunc/Spring-Bean-common-instantiate

原文: [https://rumenz.com/rumenbiji/Spring-Bean-common-instantiate.html](https://rumenz.com/rumenbiji/Spring-Bean-common-instantiate.html)
