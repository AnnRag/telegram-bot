package com.example.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegrambot.botUsername}")
    private String botUsername;
    @Value("${telegrambot.botToken}")
    private String botToken;
    @Value("classpath:pictures/robot.jpg")
    private Path pathPicture;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            try {
                execute(getSendPhoto(chat_id));
            } catch (TelegramApiException e) {
                log.error("Error while sending message to user",e);
            }
        }
    }

    private SendPhoto getSendPhoto(long chat_id) {
        return new SendPhoto()
                .setChatId(chat_id)
                .setPhoto(pathPicture.toFile())
                .setCaption("Hi! Do you want to know who I am?")
                .setReplyMarkup(getCustomReplyKeyboardMarkup());
    }

    private ReplyKeyboardMarkup getCustomReplyKeyboardMarkup() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("yes"));
        keyboardRow.add(new KeyboardButton("no"));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
        return replyKeyboardMarkup;
    }

}
