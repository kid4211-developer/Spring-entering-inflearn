package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    /**
     * test case 는 각 method 의 순서에 의존적으로 수행되어서는 안된다.
     * -> 테스트 동작 마다 메모리를 clear 해주는 작업이 필요함
     */
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        memoryMemberRepository.save(member);

        //then
        Member result = memoryMemberRepository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");

        memoryMemberRepository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);

        //when
        List<Member> result = memoryMemberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);

        //when
        Member result = memoryMemberRepository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }
}