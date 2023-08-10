package com.ssafy.a403.global.config.security.jwt;

import com.ssafy.a403.global.config.security.LoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtSetupService {

	private final JwtProvider jwtProvider;

    @Value("${client.host}")
    private String clientHost;

    @Value("${jwt.access-header}")
    private String accessTokenHeaderTag;

    @Value("${jwt.refresh-header}")
    private String refreshTokenHeaderTag;

//    public String addJwtTokensToCookie(HttpServletResponse response, LoginUser loginUser) {
//        JwtToken jwtToken = jwtProvider.createJwtToken(loginUser);
//
//        ResponseCookie accessTokenCookie = setCookie(accessTokenHeaderTag, jwtToken.getAccessToken());
//        ResponseCookie refreshTokenCookie = setCookie(refreshTokenHeaderTag, jwtToken.getRefreshToken());
//        log.info("accessToken = " + accessTokenCookie);
//        log.info("refreshToken = " + refreshTokenCookie);
//        response.addHeader("Set-Cookie", accessTokenCookie.toString());
//        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
//
//        return jwtToken.getAccessToken();
//    }
    
    public String createAuthToken(LoginUser loginUser) {
    	return jwtProvider.createAuthToken(loginUser);
    }
    
    public JwtToken createJwtTokenByAuthToken(String authToken) throws NumberFormatException {
    	return jwtProvider.createJwtTokenByAuthToken(authToken);
    }

    public ResponseCookie setCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .domain(clientHost)
                .build();
    }
}
