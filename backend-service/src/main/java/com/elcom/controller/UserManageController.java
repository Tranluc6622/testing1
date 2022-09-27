package com.elcom.controller;

import com.elcom.auth.CustomUserDetails;
import com.elcom.auth.LoginRequest;
import com.elcom.auth.LoginResponse;
import com.elcom.auth.jwt.JwtTokenProvider;
import com.elcom.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserManageController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "lay danh sach user ",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thành công, có dữ liệu"),
            @ApiResponse(code = 204, message = "Thành công, không có dữ liệu"),
            @ApiResponse(code = 401, message = "Chưa xác thực"),
            @ApiResponse(code = 403, message = "Truy cập bị cấm"),
            @ApiResponse(code = 404, message = "Không tìm thấy"),
            @ApiResponse(code = 500, message = "Lỗi BackEnd")
    })
    public ResponseEntity<List<User>> findAll(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer rowsPerPage,
                                              @RequestParam(defaultValue = "id") String sort) {

    }
