package com.lytquest.frogdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    private Long id;
    private String fullname;
    private String email;
    private String username;
    private String password;
    private String token;

}

