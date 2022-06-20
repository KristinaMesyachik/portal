package com.example.demo.service;

public interface IMailService {
    void sendSimpleEmail(final String toAddress, final String subject, final String message);
}
