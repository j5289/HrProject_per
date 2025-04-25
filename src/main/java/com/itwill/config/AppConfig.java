package com.itwill.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // 스프링 설정 클래스임을 표시
@ComponentScan(basePackages = "com.itwill")  // 스캔할 패키지 지정
public class AppConfig {
    // 필요한 추가적인 설정을 여기에 작성할 수 있습니다.
}
