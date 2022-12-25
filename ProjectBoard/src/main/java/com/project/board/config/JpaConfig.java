package com.project.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() { // 안에 들어갈 내용은 id
        // 생성자에 jangjh로 들어 가게 된다.
        return () -> Optional.of("jangjh"); // TODO: 스프링 시큐리티로 인증기능을 붙이게 될때 수정

    }
}
