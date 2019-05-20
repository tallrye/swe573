package com.tallrye.wlearn.service.util;

import com.tallrye.wlearn.exception.CreatedByException;

public class SmeptUtilities {

    private SmeptUtilities() {
    }

    public static void checkCreatedBy(String entity, Long userId, Long createdBy) {

        if (!userId.equals(createdBy)) {
            throw new CreatedByException(entity, userId.toString(), createdBy.toString());
        }
    }

}
