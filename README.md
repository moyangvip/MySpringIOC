# MySpringIOC
在阿里实习期间学习Spring，参考源码实现的一个简化版的IOC模型，功能有：
 * 支持基本ioc
 * setter注入方式，可注入bean及int、string等基本类型
 * 可选择配置scope（单例模式或原型模式）、init-method、lazy-init
 * 支持多线程
 * 循环依赖检测
 
com.myspring.test包为基本ioc功能的测试，MyTestResource类为bean的配置文件，在TestApplication处理；
com.myspring.test.circle包为循环依赖的测试；
com.myspring.test.threads包为多线程的测试。
 
包含PPT及讲解文档
感谢阿里巴巴1688部门陈海涛师兄和黄舜平师兄的指导。
