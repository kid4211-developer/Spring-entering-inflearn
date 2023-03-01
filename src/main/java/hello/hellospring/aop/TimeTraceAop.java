package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP : Aspect Oriented Programming
 * - cross-cutting concern (공통 관심 사항) vs corm concern (핵심 관심 사항)
 * - 예를들어 각 메서드의 동작 소요 시간 측정은 핵심 관심사항이 아니기 때문에 각 메소드에 구현하게 되면 비효율적이다.
 *   각 메소드에 개별적으로 적용하게 되면 유지보수성이 매우 떨어진다. (수정 사항이 있다면 모든 로직을 찾아 변경해야 하므로)
 * - 그렇다고 해서 공통 모듈을 만들어 기능을 구현하는 것도 필요 기능 대비 난이도가 높아 비효율적이다.
 */
@Aspect
@Component
public class TimeTraceAop {
    /**
     * @Around : 구현한 AOP 기능을 동작 실킬 target 을 지정할 수 있다.
     */
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}