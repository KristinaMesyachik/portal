package com.example.demo.service.interf;

public interface IMailService {
    void sendSimpleEmail(final String toAddress, final String subject, final String message);
}
