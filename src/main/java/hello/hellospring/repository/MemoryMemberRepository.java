package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @Repository
 * - Config 파일 작성을 통해 Spring Bean 을 등록 할 수 있다.
 * - 실무에서는 주로 정형화된 @Controller, @Service, @Repository 어노테이션을 통해 컴포넌트 스캔을 사용한다.
 * - 그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 config 파일(@Configuration)을 통해 스프링 빈으로 등록한다.
 */
@Repository
public class MemoryMemberRepository implements MemberRepository {
    /**
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
     */
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }
}