package com.zzw.springboot03webjsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/abc")
    public String hello(Model model) {
        model.addAttribute("msg", "你好！");
        return "success";
    }
}

/*
使用外置的Servlet容器：
1、将项目的打包方式改为war；
2、然后把tomcat的依赖范围改为provided（表示目标环境有了，就不需要打包时带上tomcat）
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
3、必须编写一个类继承SpringBootServletInitializer，并重写configure方法，调用参数的sources方法springboot启动类传过去然后返回
4、启动tomcat服务器即可使用。

原理
jar包：执行SpringBoot主类的main方法，启动ioc容器，创建嵌入式的Servlet容器；
war包：启动服务器，服务器启动SpringBoot应用[SpringBootServletInitializer]，启动ioc容器；
规则：
    1）、服务器启动（web应用启动）会创建当前web应用里每一个jar包里面的ServletContainerInitializer实例；
    2）、Servlet3.0标准ServletContainerInitializer扫描所有jar包中META-INF/services/javax.servlet.ServletContainerInitializer文件指定的类并加载
    3）、还可以使用@HandlesTypes，在应用启动的时候加载我们感兴趣的类；
流程：
    1）、启动tomcat
    2）、org\springframework\spring-web\5.2.3.RELEASE\spring-web-5.2.3.RELEASE.jar!\META-INF\services\javax.servlet.ServletContainerInitializer
    Spring的web模块里面有这个文件：org.springframework.web.SpringServletContainerInitializer
    3）、SpringServletContainerInitializer将@HandlesTypes(WebApplicationInitializer.class)标注的所有这个类型的类
    都传入到onStartup方法的Set<Class<?>>；为这些WebApplicationInitializer类型创建实例；
    4）、每一个WebApplicationInitializer都调用自己的onStartup方法
    5）、相当于SpringBootServletInitializer的类会被创建对象，并执行onStartup方法；
    6）、SpringBootServletInitializer实例执行onStartup时会创建容器：createRootApplicationContext；

    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        //1、创建SpringApplicationBuilde
		SpringApplicationBuilder builder = createSpringApplicationBuilder();
		builder.main(getClass());
		ApplicationContext parent = getExistingRootWebApplicationContext(servletContext);
		if (parent != null) {
			this.logger.info("Root context already created (using as parent).");
			servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, null);
			builder.initializers(new ParentContextApplicationContextInitializer(parent));
		}
		builder.initializers(new ServletContextApplicationContextInitializer(servletContext));
		builder.contextClass(AnnotationConfigServletWebServerApplicationContext.class);
		 //调用configure方法，子类重写了这个方法，将SpringBoot的主程序类传入了进来
		builder = configure(builder);
		builder.listeners(new WebEnvironmentPropertySourceInitializer(servletContext));
		//使用builder创建一个Spring应用
		SpringApplication application = builder.build();
		if (application.getAllSources().isEmpty()
				&& MergedAnnotations.from(getClass(), SearchStrategy.TYPE_HIERARCHY).isPresent(Configuration.class)) {
			application.addPrimarySources(Collections.singleton(getClass()));
		}
		Assert.state(!application.getAllSources().isEmpty(),
				"No SpringApplication sources have been defined. Either override the "
						+ "configure method or add an @Configuration annotation");
		// Ensure error pages are registered
		if (this.registerErrorPageFilter) {
			application.addPrimarySources(Collections.singleton(ErrorPageFilterConfiguration.class));
		}
		//启动Spring应用
		return run(application);
	}
	7）、Spring的应用就启动并且创建IOC容器：
	public ConfigurableApplicationContext run(String... args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments, printedBanner);
			//刷新IOC容器
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
*/
