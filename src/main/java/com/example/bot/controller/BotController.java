package com.example.bot.controller;

import com.example.bot.TelegramBot;
import com.example.bot.exceptions.BotException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Update> sendResponse(@RequestBody Update update) throws BotException {
        try {
            telegramBot.onUpdateReceived(update);
            return ResponseEntity.ok(update);
        } catch (Exception e) {
            throw new BotException(e);
        }
    }


}
