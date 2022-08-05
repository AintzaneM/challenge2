package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
@Service
public interface NotificationService {

  void notifyAboutTransfer(Account account, String transferDescription);
}
