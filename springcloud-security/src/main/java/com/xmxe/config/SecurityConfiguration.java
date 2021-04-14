package com.xmxe.config;

import com.xmxe.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

//https://zhuanlan.zhihu.com/p/67519928
// spring security + jwt :https://mp.weixin.qq.com/s/dk8CW5uvMPD-KE7ruaqwmA
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    FailureHandler failureHandler;

    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(WebSecurity web) {
                web.ignoring().antMatchers("/js/**", "/css/**", "/images/**","/**/favicon.*");
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin")
                        //.defaultSuccessUrl("/hello",true) //配置默认首页 加true相当于successForwardUrl 二者区别见下方注释
                        //.usernameParameter("name")//不配置的话登陆表单参数必须为username
                        //.passwordParameter("passwd")//不配置的话登陆表单参数必须为password
                        //.failureForwardUrl("/error")
                        .failureHandler(failureHandler)//登陆失败时将自定义信息写入session
                        .permitAll()//允许访问
                        .and()
                    .sessionManagement()
                        .maximumSessions(1)//相同用户只允许登陆1个
                        //.maxSessionsPreventsLogin(true)//相同用户登陆后不允许在登陆
                        .expiredSessionStrategy(sessionInformationExpiredStrategy)//自定义session过期策略
                        .and()
                       // .invalidSessionUrl("/login")
                        .and()
                    .rememberMe()
                        .rememberMeParameter("remember-me")
                        .userDetailsService(myUserDetailService)
                        .tokenValiditySeconds(60)//记住我的时间 (秒)
                        //.tokenRepository(persistentTokenRepository()) // 设置数据访问层
                        .and()
                    .authorizeRequests()
                        .antMatchers("/guest/**","/error","/login").permitAll()// /guest/**的接口会被允许所有人访问，包括未登录的人。
                        .antMatchers("/admin/**").hasRole("adminRole")// /admin/**的接口只能被拥有admin角色的用户访问。
                        .antMatchers("/authenticated/**").authenticated()// /authenticated/**的接口可以被所有已经登录的用户访问。
                        .antMatchers("/permission1/**").hasAuthority("permission1")// /permission1/的接口可以被拥有permission1权限的用户访问。/permission2/、/permission3/**、/permission4/**同理
                        .antMatchers("/permission2/**").hasAuthority("permission2")
                        .antMatchers("/permission3/**").hasAuthority("permission3")
                        .antMatchers("/permission4/**").hasAuthority("permission4")
                        .anyRequest().authenticated().
                        and()
                    .logout()
                        .logoutUrl("/logout_1")//spring security设置logout_1为系统退出的url 当监测到这个请求url时使用spring security处理登出逻辑 请求方式默认使用post a标签方式为get 可以在spring mvc中处理登出逻辑 但是这样的话就和spring security没关系了
                        //.logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))//自定义请求方式
                        .logoutSuccessUrl("/login")//注销成功后要跳转的页面。
                        .deleteCookies()//用来清除 cookie
                        .permitAll()
                        .and()
                    .csrf().disable();
            }

          /*  //代码创建用户密码
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication()
                        .withUser("user")
                        .password("password")
                        .roles("USER")
                        .and()
                        .withUser("admin")
                        .password("admin")
                        .roles("USER", "ADMIN");
            }*/

        };

    }

    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();//不加密 在demo中测试使用 生产环境不建议
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy() {//角色集成 user角色能访问的admin也角色可以访问
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_adminRole > ROLE_guestRole");
        return hierarchy;
    }
    //防止当设置成相同用户登陆后不允许在登陆时第一个用户注销登录后无法继续登陆
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    /**
     * 持久化token
     * Security中，默认是使用PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建表persistent_logins，将token持久化到数据库
     */
 /*   @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // 设置数据源
//        tokenRepository.setCreateTableOnStartup(true); // 启动创建表，创建成功后注释掉
        return tokenRepository;
    }*/
}


/**
 * 在 Spring Security 中，和登录成功重定向 URL 相关的方法有两个
 * defaultSuccessUrl successForwardUrl
 * defaultSuccessUrl 有一个重载的方法，我们先说一个参数的 defaultSuccessUrl 方法。
 * 如果我们在 defaultSuccessUrl 中指定登录成功的跳转页面为 /index，此时分两种情况，
 * 如果你是直接在浏览器中输入的登录地址，登录成功后，就直接跳转到 /index，
 * 如果你是在浏览器中输入了其他地址，例如 http://localhost:8080/hello，结果因为没有登录，又重定向到登录页面，
 * 此时登录成功后，就不会来到 /index ，而是来到 /hello 页面。
 * defaultSuccessUrl 还有一个重载的方法，第二个参数如果不设置默认为 false，也就是我们上面的的情况，
 * 如果手动设置第二个参数为 true，则 defaultSuccessUrl 的效果和 successForwardUrl 一致。
 * successForwardUrl 表示不管你是从哪里来的，登录后一律跳转到 successForwardUrl 指定的地址。
 * 例如 successForwardUrl 指定的地址为 /index ，你在浏览器地址栏输入 http://localhost:8080/hello，
 * 结果因为没有登录，重定向到登录页面，当你登录成功之后，就会服务端跳转到 /index 页面；
 * 或者你直接就在浏览器输入了登录页面地址，登录成功后也是来到 /index。
 *
 * 与登录成功相似，登录失败也是有两个方法：
 * failureForwardUrl failureUrl
 * 「这两个方法在设置的时候也是设置一个即可」。
 * failureForwardUrl 是登录失败之后会发生服务端跳转，failureUrl 则在登录失败之后，会发生重定向。
 */

/**
 * spring boot如果想在浏览器中直接访问html (localhost:8090/login.html) 在resources下新建public目录
 * 否则的话必须通过视图渲染解析才能访问 也就是loginPage("/login.html") 404的原因
 * spring boot默认首页会去访问static/index.html 所以不配置defaultSuccessUrl首页的话也不会报错 如果配置的话必须在Controller写接口 否则会报404
 */