package com.honeycrisp.fave.event;

/**
 * Abstract superclass for all HTTP-related EventBus events. All HttpEvents should subclass OnSuccess
 * and OnFailure.
 *
 * Created by Ryan Taylor on 3/31/16.
 */
public abstract class HttpEvent {

    protected abstract static class OnSuccess<T> {
        private T response;

        public OnSuccess(T response) {
            this.response = response;
        }

        public T getResponse() {
            return response;
        }
    }

    protected abstract static class OnFailure {
        private Throwable throwable;

        public OnFailure(Throwable throwable) {
            this.throwable = throwable;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}