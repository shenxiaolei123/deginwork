###用300行代码手写提炼Spring的核心原理

##1、理解SpringIOC、DI、MVC的基本执行原理。
 * IOC就是由spring来负责控制对象的生命周期和对象间的关系
 * IoC的一个重点是在系统运行中，动态的向某个对象提供它所需要的其他对象。这一点是通过DI（Dependency Injection，依赖注入）来实现的
 * SpringMVC
   * 客户端通过url发送请求
   * 核心控制器Dispatcher Servlet接收到请求，通过系统或自定义的映射器配置找到对应的handler，从handlermapping中并将url映射的控制器controller返回给核心控制器。
   * 通过核心控制器找到系统或默认的适配器HanlderAdapter
   * 由找到的适配器，调用实现对应接口的处理器，并将结果返回给适配器，结果中包含数据模型和视图对象ModelAndView，再由适配器返回给核心控制器
   * 核心控制器将获取的数据和视图结合的对象传递给视图解析器ViewResolver，获取解析得到的结果，并由视图解析器响应给核心控制器
   * 核心控制器将结果返回给客户端
   
##2、自己手写一个Mini版本的SpringMVC。
-[x] [springmvcmini](https://github.com/shenxiaolei123/deginwork/tree/master/springMVCmini%E4%BD%9C%E4%B8%9A/springmvcmini)