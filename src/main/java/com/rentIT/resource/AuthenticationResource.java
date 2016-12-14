package com.rentIT.resource;

import com.rentIT.dto.AuthenticationDto;
import com.rentIT.security.TokenProvider;
import com.rentIT.service.UserService;
import com.rentIT.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Collection;
import java.util.Collections;

import static com.rentIT.security.AuthorizationConfigurer.AUTHORIZATION_HEADER;

@RestController
@Slf4j
@RequestMapping("/api")
public class AuthenticationResource {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity authorize(@Valid @RequestBody AuthenticationDto authDto, HttpServletResponse httpServletResponse) {
        log.debug("Authenticating user with username ", authDto.getUsername());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        try{
            Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication, authDto.isRememberMe());
            httpServletResponse.addHeader(AUTHORIZATION_HEADER, "Bearer " + jwt);
            log.debug("User with username {} successfuly authenticated ", authDto.getUsername());

            return new ResponseEntity(new JwtToken(jwt), HttpStatus.OK);
        }catch (AuthenticationException ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity(Collections.singletonMap("Authentication Exception", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }


    }


}
