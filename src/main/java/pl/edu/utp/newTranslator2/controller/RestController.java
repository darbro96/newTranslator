package pl.edu.utp.newTranslator2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.utp.newTranslator2.entity.Message;
import pl.edu.utp.newTranslator2.enums.MessageType;
import pl.edu.utp.newTranslator2.service.MessageService;
import pl.edu.utp.newTranslator2.translator.TextTranslator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send/type={type}/code={code}/message={message}")
    public void putMessage(@PathVariable("message") String message, @PathVariable("type") String type, @PathVariable("code") String code) {
        messageService.addMessage(message, MessageType.valueOf(type), code);
    }

    @PostMapping("/send/type={type}/code={code}/{lang}/message={message}")
    public void putMessageAndTranslate(@PathVariable("message") String message, @PathVariable("type") String type, @PathVariable("code") String code, @PathVariable("lang") String lang) {
        messageService.addMessage(TextTranslator.translate(lang,"pl",message), MessageType.valueOf(type), code);
    }

    @RequestMapping("/all")
    public List<Message> getAll() {
        return messageService.findAll();
    }

    @RequestMapping("/getMessages/code={code}")
    public List<Message> getMessagesToCode(@PathVariable("code") String code) {
        return messageService.findByCode(code);
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable("id") long id) {
        messageService.deleteOne(id);
    }

    @DeleteMapping("/del/all")
    public void clear() {
        messageService.deleteAll();
    }

    @RequestMapping("/all/{lang}")
    public List<Message> getAllInLang(@PathVariable("lang") String language)
    {
        List<Message> conversation=messageService.findAll();
        conversation.forEach(t->t.setContent(TextTranslator.translate("pl",language,t.getContent())));
        return conversation;
    }
}

