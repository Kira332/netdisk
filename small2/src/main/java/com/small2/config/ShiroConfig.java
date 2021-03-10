package com.small2.config;

import com.small2.realm.AdminRealm;
import com.small2.realm.UserRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);

        Map<String, String> filterMap=new LinkedHashMap<>();

//        filterMap.put("/user", "authc");
//        filterMap.put("/admin", "authc");
        filterMap.put("/index", "anon");
        filterMap.put("/user/**", "roles[user]");
        filterMap.put("/admin/**", "roles[admin]");

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/toLogin");

        return bean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        Collection<Realm> realms = new ArrayList<>();

        realms.add(userRealm());
        realms.add(adminRealm());

        ModularRealmAuthenticator modularRealmAuthenticator=new ModularRealmAuthenticator();

        modularRealmAuthenticator.setRealms(realms);
        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());

        securityManager.setAuthenticator(modularRealmAuthenticator);

        return securityManager;

    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public AdminRealm adminRealm(){
        return new AdminRealm();
    }


}
