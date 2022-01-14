package com.cos.photogram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //해당 파일로 시큐리티를 활성화시킴
@Configuration //ioc에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //SecurityConfig가 IOC에 등록될때 @Bean을 읽어서 이걸 리턴해서 IOC가 들고있음 DI에서 쓰기만하면됨
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super 삭제하면 -> 기존시큐리티가 가지고 있는 속성이 모두 비활성화
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated() //이런주소로 들어오게되면 인증이 필요하다
                .anyRequest().permitAll() //그 외는 모두 허가
                .and()
                .formLogin()
                //get
                .loginPage("/auth/signin") //롬 로긴페이지가 이거다 ("/","user/**","/image/**","/subscribe/**","/comment/**" 로 찾아오면 여기로 오게하겠다
                //post
                .loginProcessingUrl("/auth/signin") //post로 요청을 했을때 낚아채서 스프링시큐리티가 로그인프로세스 진행
                .defaultSuccessUrl("/"); //로그인을 정상적으로 처리하면 / 로 오겠다
    }
}
