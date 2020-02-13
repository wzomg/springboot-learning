package com.zzw.cache.service;

import com.zzw.cache.mapper.EmployeeMapper;
import com.zzw.cache.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp")  // 抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存；以后再要相同的数据。直接从缓存中获取，不用调用sql查询
     *
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作再Cache中，每一个缓存组件有自己唯一一个名字
     * 几个属性：
     *      cacheNames/value：指定缓存的名字；指定将方法的返回值放在哪个缓存中，是数组的方式，可以指定多个缓存
     *      key：缓存数据使用的key：可以用它来指定。默认使用方法参数的值，如：1-方法的返回值
     *             编写SpEl表达式： #id表示参数id的值；等同于 #a0 #p0  #root.args[0]
     *             getEmp[2]
     *
     *      keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     *          key/keyGenerator：二选一
     *      cacheManager：指定缓存管理器；或者指定缓存解析器 cacheResolver
     *      condition：指定符合条件的情况下才缓存
     *                  condition = "#id>0"
     *                  condition = "#a0>1" 方法参数的第一个值大于1时才进行缓存
     *      unless：否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存
     *                  unless = "#result == null"
     *                  unless = "#a0 == 2" 若第一个参数的值是2，结果才缓存
     *      sync：是否使用异步模式，若异步的值为true，则unless属性就不支持
     * 原理：
     *      1、自动配置类：CacheAutoConfiguration
     *      2、缓存的配置类：
     *          org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *          org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration 【默认开启】
     *          org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *       3、哪个配置类默认生效：SimpleCacheConfiguration
     *       4、给容器中注册了一个cacheManager缓存管理器：ConcurrentMapCacheManager
     *        可以获取和创建 ConcurrentMapCache 类型的缓存组件，其作用是将数据保存在 ConcurrentMap 中；
     *       5、运行流程：
     *        @Cacheable：
     *        1、方法运行之前，先去查询Cache（缓存组件），按照 cacheNames 指定的名字获取；
     *              （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件 会自动创建出来
     *        2、去Cache中查找缓存的内容，使用一个key，默认为方法的参数
     *          key是按照某种策略生成的，默认使用 keyGenerator 生成的，默认使用 SimpleKeyGenerator 生成key
     *              SimpleKeyGenerator生成key的默认策略：
     *              若没有参数，key=new SimpleKey()
     *              若有一个参数，key=参数的值
     *              若有多个参数，key=new SimpleKey(params)
     *        3、没有查到缓存就调用目标方法 put
     *        4、将目标方法返回的结果，放进缓存中
     *  总结：@Cacheable标注的方法执行之前先来检查缓存中是否有这个数据，默认按照参数的值作为key去查询缓存，
     *         若没有就运行put方法将结果放入缓存ConcurrentMap中，以后再来调用就可以直接使用缓存中的数据
     *  核心：
     *      1）、使用 CacheManager 【ConcurrentMapCacheManager】 按照名字得到Cache【ConcurrentMapCache】组件
     *      2）、key使用 keyGenerator 生成的，默认是 SimpleKeyGenerator
     * @param id
     * @return
     */
    @Cacheable(value = {"emp"}/*, keyGenerator = "myKeyGenerator", condition = "#a0>1", unless = "#a0 == 2"*/)
    // key="#root.methodName+'['+#id+']'" => key：getEmp[2]
    // keyGenerator = "myKeyGenerator" => 指定自定义的key生成id
    // a0取出第一个参数的值
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut：修改了数据库的某个数据，同时更新缓存
     * @param employee
     * @return
     * 运行时机：
     *      1、先执行目标方法；
     *      2、再将目标方法的结果缓存起来
     * 测试步骤：
     *      1、查询1号员工；查到的结果会放在缓存中
     *      2、以后查询还是之前的结果
     *      3、更新1号员工
     *          将方法的返回值也放进缓存中
     *          key：传入的employee 对象，值：返回的employee 对象
     *      4、查询1号员工？
     *          应该是更新后的员工：
     *              key = "#employee.id" 使用传入参数的员工id作为key
     *              key = "#result.id"  使用返回值的id
     *              @Cacheable  的key是不能用#result，因为是先去缓存中查找的
     *          为什么是没更新前的？【1号员工没有在缓存中更新】
     */

    @CachePut(value = "emp", key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp：" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }


    /**
     * 缓存清除
     * key：指定要清除的数据
     *  allEntries = true：指定清除这个缓存中所有数据
     *  beforeInvocation = false：表示缓存的清除是否在方法之前执行
     *          默认表示在方法执行之后执行；若出现异常则缓存就不会被清除
     *  beforeInvocation = true：代表清除缓存操作是在方法运行之前执行，无论是方法是否出现异常，缓存都清除
     * @param id
     */
    @CacheEvict(/*value = "emp",*/ key = "#id" )
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp：" + id);
        // employeeMapper.deleteEmp(id);
        //int i = 10/0;
    }

    //  @Caching 定义复杂的缓存规则
    @Caching(
            cacheable = {
                    //先去缓存查询name
                    @Cacheable(/*value = "emp",*/ key = "#lastName")
            },
            put = {
                    // 在目标方法执行之后被调用
                    @CachePut(/*value = "emp",*/ key = "#result.id"),
                    @CachePut(/*value = "emp",*/ key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
       return  employeeMapper.getEmpByLastName(lastName);
    }
}
