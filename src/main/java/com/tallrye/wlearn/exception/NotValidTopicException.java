package com.tallrye.wlearn.exception;

public class NotValidTopicException extends RuntimeException {

    public NotValidTopicException(String title, String message) {

        super(String
                .format("TopicEntity with title: '%s' is not valid Reason: '%s'", title, message));
    }
}
