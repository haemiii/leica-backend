package com.example.leica_refactoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = {
        org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
        org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
        org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
}) // 이게 뭐냐면 여기 어노테이션 괄호 지우면 WARN이 뜨거든? endpoint 서비스랑 연결이 안되었다고?
// 이게 aws랑 연결되어있는데 로컬에서 실행하면 aws랑 연결이 안되었다고 뜨는 오류인거야 즉, 서버 실행할때는 필요없는거지
// 그래서 이거를 적용하면 WARN 안떠!

public class LeicaRefactoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeicaRefactoringApplication.class, args);
    }
}
