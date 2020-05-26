package io.pivotal.literx;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Learn how to identify and fix an impostor method.
 *
 * @author Bruno Sales
 *
 * @see Mono#defer(Supplier)
 * @see Mono#fromSupplier(Supplier)
 */
public class Part19ImpostorMethodTest {

    Part19ImpostorMethod workshop = new Part19ImpostorMethod();

    @Test
    public void shouldBeEmptyIfNumberIsOdd() {
        AtomicInteger i = new AtomicInteger(1);
        Supplier<Integer> supplier = i::getAndIncrement;

        Mono<Integer> result = workshop.filterEven(supplier);

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    public void shouldResult2BecauseItIsEvent() {
        AtomicInteger i = new AtomicInteger(1);
        Supplier<Integer> supplier = i::getAndIncrement;

        Mono<Integer> result = workshop.filterEven(supplier);

        StepVerifier.create(result.repeat(2))
            .expectNext(2)
            .verifyComplete();
    }

}
