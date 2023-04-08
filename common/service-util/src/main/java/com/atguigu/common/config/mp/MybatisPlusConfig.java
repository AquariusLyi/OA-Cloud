package com.atguigu.common.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.atguigu.auth.mapper","com.atguigu.process.mapper","com.atguigu.wechat.mapper"})
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 在 MyBatis 中，useDeprecatedExecutor 属性控制着 MyBatis 在执行 SQL 语句时所使用的执行器（Executor）。执行器是 MyBatis
     * 中用于执行 SQL 语句并返回结果的核心组件之一，负责从数据源中获取数据、将结果映射到 Java 对象上，并处理分页等操作。
     * <p>
     * 在早期版本的 MyBatis 中，执行器的实现是通过线程池实现的。但是，随着时间的推移和 MyBatis 的不断升级，MyBatis 推出了一种新的执行器实现
     * 方式，使用的是 JDK 的 Executor 接口以及 Future 接口。这种新的实现方式更加简洁、高效，并且能够充分利用 JDK 中的并发机制。
     * <p>
     * 因此，将 useDeprecatedExecutor 属性设置为 false 可以让 MyBatis 使用新的执行器实现方式，提高 MyBatis 在执行 SQL 语句时的性能和效
     * 率。同时，由于新的执行器实现方式具有更好的可维护性和扩展性，因此也更加符合现代软件开发的需求。需要注意的是，useDeprecatedExecutor 属性
     * 的默认值可能会因 MyBatis 版本或配置环境的不同而有所不同，因此在具体应用中需要结合实际情况进行配置和调整。
     *
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
