package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponse {

        private int statusCode;
        private String statusMessage;
        private String field;
        private String message;

        private StandardResponse(Builder builder) {
                this.statusCode = builder.statusCode;
                this.statusMessage = builder.statusMessage;
                this.field = builder.field.isEmpty() ? null :
                    builder.field;
                this.message = builder.message.isEmpty() ? null :
                    builder.message;
        }

        public int getStatusCode() {
                return statusCode;
        }

        public String getStatusMessage() {
                return statusMessage;
        }

        public String getField() {
                return field;
        }

        public String getMessage() {
                return message;
        }

        public static class Builder {

                private int statusCode;
                private String statusMessage;
                private String field = "";
                private String message = "";

                public Builder statusCode(int statusCode) {
                        this.statusCode = statusCode;
                        return this;
                }

                public Builder statusMessage(String statusMessage) {
                        this.statusMessage = statusMessage;
                        return this;
                }

                public Builder field(String field) {
                        this.field = field;
                        return this;
                }

                public Builder message(String message) {
                        this.message = message;
                        return this;
                }

                public StandardResponse build() {
                        return new StandardResponse(this);
                }
        }
}