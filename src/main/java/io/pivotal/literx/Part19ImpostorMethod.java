package io.pivotal.literx;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * Learn how to identify and fix an impostor method.
 *
 * @author Bruno Sales
 *
 * @see Mono#defer(Supplier)
 * @see Mono#fromSupplier(Supplier)
 */
public class Part19ImpostorMethod {

    public Mono<Integer> filterEven(Supplier<Integer> numberSupplier) {
        return Mono.fromSupplier(numberSupplier)
            .filter(number -> number % 2 == 0);
//        Integer number = numberSupplier.get();
//        boolean isEven = number % 2 == 0;
//        if (isEven) {
//            return Mono.just(number);
//        } else {
//            return Mono.empty();
//        }
    }
}
