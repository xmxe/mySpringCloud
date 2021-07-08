package com.xmxe.service;

import com.xmxe.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = loginService.getUserByUsername(username);
        if (users == null || users.size() == 0) {
            throw new UsernameNotFoundException("用户名未找到");
        }
        String password = users.get(0).getPassword();
//            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//            String passwordAfterEncoder = passwordEncoder.encode(password);//对用户密码进行加密
//            System.out.println(username + "/" + passwordAfterEncoder);

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
        System.out.println("UserDetailsService中的permissionArr============" + Arrays.toString(permissionArr));
        return org.springframework.security.core.userdetails.User.withUsername(username).password(password).authorities(permissionArr).build();
    }
}
