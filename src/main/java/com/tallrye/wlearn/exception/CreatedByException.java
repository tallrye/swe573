package com.tallrye.wlearn.exception;

public class CreatedByException extends RuntimeException {

    public CreatedByException(String entryType, String entryId, String userId) {

        super(String
                .format("'%s' Entry with id: '%s' cannot be edited by this UserEntity: '%s'", entryType, entryId, userId));
    }
}
