//package cn.cat.talk.commons.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
///**
// * Druid的配置文件
// * 用于监控数据库SQL
// */
//@Configuration
//@Slf4j
//public class DruidConfiguration {
//
//
//
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${spring.datasource.initialSize}")
//    private int initialSize;
//
//    @Value("${spring.datasource.minIdle}")
//    private int minIdle;
//
//    @Value("${spring.datasource.maxActive}")
//    private int maxActive;
//
//    @Value("${spring.datasource.maxWait}")
//    private int maxWait;
//
//    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//
//    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//
//    @Value("${spring.datasource.validationQuery}")
//    private String validationQuery;
//
//    @Value("${spring.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//
//    @Value("${spring.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//
//    @Value("${spring.datasource.testOnReturn}")
//    private boolean testOnReturn;
//
//    @Value("${spring.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//
//    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//
//    @Value("${spring.datasource.filters}")
//    private String filters;
//
//    @Value("${spring.datasource.connectionProperties}")
//    private String connectionProperties;
//
//    @Value("${spring.datasource.druidMonitorUserName}")
//    private String druidMonitorUserName;
//
//    @Value("${spring.datasource.druidMonitorPassword}")
//    private String druidMonitorPassword;
//
//    /**
//     * 配置数据库连接池
//     * @return
//     */
//    @Bean     //声明其为Bean实例
//    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
//    public DataSource dataSource(){
//        DruidDataSource datasource = new DruidDataSource();
//
//        log.info(">>>>>>>>>>>>>开始配置druid连接池<<<<<<<<<<<<<<<<<<");
//
//        log.info(">>>>>>>>>>>>>>>>>>>>数据库连接 : dbUrl<<<<<<<<<<<<<<<<<<<"+dbUrl);
//
//        datasource.setUrl(this.dbUrl);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//
//        //configuration
//        datasource.setInitialSize(initialSize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        datasource.setValidationQuery(validationQuery);
//        datasource.setTestWhileIdle(testWhileIdle);
//        datasource.setTestOnBorrow(testOnBorrow);
//        datasource.setTestOnReturn(testOnReturn);
//        datasource.setPoolPreparedStatements(poolPreparedStatements);
//        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            log.error("druid configuration initialization filter", e);
//        }
//        datasource.setConnectionProperties(connectionProperties);
//
//        return datasource;
//    }
//
//
//    /**
//     * 配置监控统计Filter
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        log.info("init Druid Servlet Configuration ");
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        // IP白名单
//        //servletRegistrationBean.addInitParameter("allow", "10.108.45.95,127.0.0.1");
//        // IP黑名单(共同存在时，deny优先于allow)
//        //servletRegistrationBean.addInitParameter("deny", "10.108.45.95");
//        //控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername", druidMonitorUserName);
//        servletRegistrationBean.addInitParameter("loginPassword", druidMonitorPassword);
//        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }
//
//
//    @Bean(name ="druidStatInterceptor")
//    public DruidStatInterceptor druidStatInterceptor(){
//        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
//        return druidStatInterceptor;
//    }
//
//   /* @Bean
//    public BeanTypeAutoProxyCreator BeanTypeAutoProxyCreator(){
//        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
//
//        beanTypeAutoProxyCreator.setProxyTargetClass(true);
//        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
//
//        return beanTypeAutoProxyCreator;
//    }*/
//
//}
