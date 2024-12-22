package com.rentacar.rentacar.exception;

public class UserNotFoundException  extends RuntimeException
{
    public UserNotFoundException(String message)
    {
        super(message);
    }
}
