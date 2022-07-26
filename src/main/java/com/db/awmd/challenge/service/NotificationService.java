package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;

import javax.validation.constraints.NotNull;

public interface NotificationService {

  void notifyAboutTransfer(Account account, String transferDescription);
}
