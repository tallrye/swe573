package com.tallrye.wlearn.service;

import com.tallrye.wlearn.exception.CreatedByException;

public class WlearnUtils {

    private WlearnUtils() {
    }

    public static void checkCreatedBy(String entity, Long userId, Long createdBy) {

        if (!userId.equals(createdBy)) {
            throw new CreatedByException(entity, userId.toString(), createdBy.toString());
        }
    }

}
