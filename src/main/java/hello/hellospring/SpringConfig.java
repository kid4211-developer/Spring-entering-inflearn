package hello.hellospring;

import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    /**
     * - DataSource 는 데이터베이스 커넥션을 획득할 때 사용하는 객체다.
     * - 스프링 부트는 데이터베이스 커넥션 정보를 바탕으로 DataSource 를 생성하고 Spring Bean 으로 만들어 DI를 받을 수 있다.
     */
    private final DataSource dataSource;
    private final EntityManager entityManager;

    public SpringConfig(DataSource dataSource, EntityManager entityManager) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(entityManager);
    }
}