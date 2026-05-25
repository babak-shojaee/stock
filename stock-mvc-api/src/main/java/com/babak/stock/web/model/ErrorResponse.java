package com.babak.stock.web.model;

import java.io.Serializable;
import java.time.Instant;

public record ErrorResponse(Instant timestamp,
                            int status,
                            String error,
                            String code,
                            String exception,
                            String message,
                            String path) implements Serializable {

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Instant timestamp;
        private int status;
        private String error;
        private String code;
        private String exception;
        private String message;
        private String path;

        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public Builder status(int status) { this.status = status; return this; }
        public Builder error(String error) { this.error = error; return this; }
        public Builder code(String code) { this.code = code; return this; }
        public Builder exception(String exception) { this.exception = exception; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder path(String path) { this.path = path; return this; }

        public ErrorResponse build() {
            return new ErrorResponse(timestamp, status, error, code, exception, message, path);
        }
    }
}
