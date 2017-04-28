package com.rentIT.util;

import org.springframework.http.HttpHeaders;

public final class HttpHeaderFactory {

    private HttpHeaderFactory() {
        throw new UnsupportedOperationException("this should not be called");
    }

    public static HttpHeaders createAuthenticationHeader(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        return headers;
    }

}
