package com.security.config;

import com.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    LoginService loginService; //https://zhuanlan.zhihu.com/p/67519928

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            List<com.security.entity.User> users = loginService.getUserByUsername(username);
            if (users == null || users.size() == 0) {
                throw new UsernameNotFoundException("用户名未找到");
            }
            String password = users.get(0).getPassword();
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String passwordAfterEncoder = passwordEncoder.encode(password);//对用户密码进行加密
            System.out.println(username + "/" + passwordAfterEncoder);

            List<String> roles = loginService.getRoleByUsername(username);
            List<String> permissions = loginService.getPermissionsByUsername(username);

            String[] permissionArr = new String[roles.size() + permissions.size()];
            int permissionArrIndex = 0;
            for (String role : roles) {
                permissionArr[permissionArrIndex] = "ROLE_" + role;
                permissionArrIndex++;
            }
            for (String permission : permissions) {
                permissionArr[permissionArrIndex] = permission;
                permissionArrIndex++;
            }
            return User.withUsername(username).password(passwordAfterEncoder).authorities(permissionArr).build();
        };
    }

//    /guest/**的接口会被允许所有人访问，包括未登录的人。
//    /admin/**的接口只能被拥有admin角色的用户访问。
//    /auth/**的接口可以被所有已经登录的用户访问。
//    /permission1/的接口可以被拥有permission1权限的用户访问。/permission2/、/permission3/**、/permission4/**同理
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeRequests().antMatchers("/guest/**").permitAll().
                        and().authorizeRequests().antMatchers("/admin/**").hasRole("adminRole").
                        and().authorizeRequests().antMatchers("/auth/**","/").authenticated().
                        and().authorizeRequests().antMatchers("/permission1/**").hasAuthority("permission1").
                        and().authorizeRequests().antMatchers("/permission2/**").hasAuthority("permission2").
                        and().authorizeRequests().antMatchers("/permission3/**").hasAuthority("permission3").
                        and().authorizeRequests().antMatchers("/permission4/**").hasAuthority("permission4").
                        and().authorizeRequests().anyRequest().permitAll().
                        and().formLogin().loginPage("/login").successForwardUrl("/index").failureForwardUrl("/login").

                        and().csrf().disable();
            }
        };
    }

}
