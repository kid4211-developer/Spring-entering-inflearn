package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @SpringBootTest : spring container 와 테스트를 함께 실행한다.
 * @Transactional
 * -> TEST CASE 에 이 어노테이션이 있다면, 테스트 시작 전에 트랜잭션을 시작하고 테스트 완료 후에 항상 롤백을 한다.
 *    이렇게 하면 DB에 테스트후 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
 */
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    /**
     * test code 에서는 프로젝트 내에서 별도의 기능과는 별개 이므로 단순히 Field 주입 방식으로 DI를 구연해도 무방하다.
     */
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}