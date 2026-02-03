package com.app.management.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticatedUsersDto {

    private String serviceToken;
    private UsersDto userDto;

    public AuthenticatedUsersDto() {
    }

    public AuthenticatedUsersDto(String serviceToken, UsersDto userDto) {
        this.serviceToken = serviceToken;
        this.userDto = userDto;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    @JsonProperty("user")
    public UsersDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UsersDto userDto) {
        this.userDto = userDto;
    }

    

    

    
    
}
