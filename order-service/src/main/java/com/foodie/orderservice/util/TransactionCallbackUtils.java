package com.foodie.orderservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Created on 31/05/25.
 *
 * @author : aasif.raza
 * @apiNote : Used int declarative transaction with @Transactional runs code after transaction has been commited.
 */
@Slf4j
public class TransactionCallbackUtils {
    public static void runAfterCommit(Runnable task) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                task.run();
            }
        });
    }
}
