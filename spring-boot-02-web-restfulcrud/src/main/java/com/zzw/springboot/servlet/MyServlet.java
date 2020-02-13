package com.zzw.springboot.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello MyServlet!");
    }
}

/*
ServletWebServerFactoryAutoConfiguration：
// BeanPostProcessorsRegistrar：后置处理器注册器(也是给容器注入一些组件)
@EnableConfigurationProperties(ServerProperties.class)
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class,
		ServletWebServerFactoryConfiguration.EmbeddedTomcat.class,
		ServletWebServerFactoryConfiguration.EmbeddedJetty.class,
		ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })
public class ServletWebServerFactoryAutoConfiguration {

EmbeddedTomcat.class：
@Configuration(proxyBeanMethods = false)
//判断当前是否引入了Tomcat依赖；
@ConditionalOnClass({ Servlet.class, Tomcat.class, UpgradeProtocol.class })
//判断当前容器中没有用户自己定义ServletWebServerFactory：嵌入式的web服务器工厂；作用：创建嵌入式的web服务器
@ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
public static class EmbeddedTomcat {
    //给容器中添加相应的XXXServletWebServerFactory
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(
            ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers,
            ObjectProvider<TomcatContextCustomizer> contextCustomizers,
            ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.getTomcatConnectorCustomizers()
                .addAll(connectorCustomizers.orderedStream().collect(Collectors.toList()));
        factory.getTomcatContextCustomizers()
                .addAll(contextCustomizers.orderedStream().collect(Collectors.toList()));
        factory.getTomcatProtocolHandlerCustomizers()
                .addAll(protocolHandlerCustomizers.orderedStream().collect(Collectors.toList()));
        return factory;
    }
}

ServletWebServerFactory：嵌入式的web服务器工厂
@FunctionalInterface
public interface ServletWebServerFactory {
    //获取嵌入式的Servlet容器
    WebServer getWebServer(ServletContextInitializer... initializers);
}

TomcatServletWebServerFactory：
@Override
public WebServer getWebServer(ServletContextInitializer... initializers) {
    if (this.disableMBeanRegistry) {
        Registry.disableRegistry();
    }
     //创建一个Tomcat
    Tomcat tomcat = new Tomcat();
    //配置Tomcat的基本环境，（tomcat的配置都是从本类获取的，tomcat.setXXX）
    File baseDir = (this.baseDirectory != null) ? this.baseDirectory : createTempDir("tomcat");
    tomcat.setBaseDir(baseDir.getAbsolutePath());
    Connector connector = new Connector(this.protocol);
    connector.setThrowOnFailure(true);
    tomcat.getService().addConnector(connector);
    customizeConnector(connector);
    tomcat.setConnector(connector);
    tomcat.getHost().setAutoDeploy(false);
    configureEngine(tomcat.getEngine());
    for (Connector additionalConnector : this.additionalTomcatConnectors) {
        tomcat.getService().addConnector(additionalConnector);
    }
    prepareContext(tomcat.getHost(), initializers);
    //将配置好的Tomcat传入进去，返回一个WebServer；并且启动Tomcat服务器
    return getTomcatWebServer(tomcat);
}


public static class BeanPostProcessorsRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {... }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
        if (this.beanFactory == null) {
            return;
        }
         //注册了下面两个组件
        registerSyntheticBeanIfMissing(registry, "webServerFactoryCustomizerBeanPostProcessor",
                WebServerFactoryCustomizerBeanPostProcessor.class);
        registerSyntheticBeanIfMissing(registry, "errorPageRegistrarBeanPostProcessor",
                ErrorPageRegistrarBeanPostProcessor.class);
    }

    private void registerSyntheticBeanIfMissing(BeanDefinitionRegistry registry, String name, Class<?> beanClass) {...}

}

public class WebServerFactoryCustomizerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

......

//在Bean初始化之前
public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    //判断添加的Bean是不是WebServerFactory类型的
    if (bean instanceof WebServerFactory) {
        this.postProcessBeforeInitialization((WebServerFactory)bean);
    }

    return bean;
}

public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
}

private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
    //获取所有的定制器，调用每一个定制器的customize方法来给Servlet容器进行属性赋值；
    LambdaSafe.callbacks(WebServerFactoryCustomizer.class, getCustomizers(), webServerFactory)
            .withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)
            .invoke((customizer) -> customizer.customize(webServerFactory));
}

private Collection<WebServerFactoryCustomizer<?>> getCustomizers() {
    if (this.customizers == null) {
        // Look up does not include the parent context
        this.customizers = new ArrayList<>(getWebServerFactoryCustomizerBeans());
        this.customizers.sort(AnnotationAwareOrderComparator.INSTANCE);
        this.customizers = Collections.unmodifiableList(this.customizers);
    }
    return this.customizers;
}
@SuppressWarnings({ "unchecked", "rawtypes" })
private Collection<WebServerFactoryCustomizer<?>> getWebServerFactoryCustomizerBeans() {
    //从容器中获取所有这种类型的组件，WebServerFactoryCustomizer，如我们在com.zzw.springboot.config.MyServerConfig中定制的servlet容器
    return (Collection) this.beanFactory.getBeansOfType(WebServerFactoryCustomizer.class, false, false).values();
}
ServerProperties也是定制器

总结：

SpringBoot根据导入的依赖情况，给容器中添加相应的XXXServletWebServerFactory（工厂对象）
容器中某个组件要创建对象就会触动后置处理器 webServerFactoryCustomizerBeanPostProcessor
只要是嵌入式的是Servlet容器工厂，后置处理器就会工作；
后置处理器：从容器中获取所有的 WebServerFactoryCustomizer，调用定制器的定制方法给工厂添加配置

嵌入式Servlet容器启动原理：略
*/