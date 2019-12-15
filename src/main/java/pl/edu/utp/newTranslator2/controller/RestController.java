package pl.edu.utp.newTranslator2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.utp.newTranslator2.codeGenerator.Code;
import pl.edu.utp.newTranslator2.codeGenerator.CodeGenerator;
import pl.edu.utp.newTranslator2.entity.Message;
import pl.edu.utp.newTranslator2.enums.MessageType;
import pl.edu.utp.newTranslator2.model.Faq;
import pl.edu.utp.newTranslator2.service.MessageService;
import pl.edu.utp.newTranslator2.translator.TextTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    private MessageService messageService;
    private final Logger logger=Logger.getLogger(getClass().getName());

    @PostMapping("/send/type={type}/code={code}/message={message}")
    public void putMessage(@PathVariable("message") String message, @PathVariable("type") String type, @PathVariable("code") String code) {
        messageService.addMessage(message, MessageType.valueOf(type), code, message);
    }

    @PostMapping("/send/type={type}/code={code}/{lang}/message={message}")
    public void putMessageAndTranslate(@PathVariable("message") String message, @PathVariable("type") String type, @PathVariable("code") String code, @PathVariable("lang") String lang) {
        if (!"en".equals(lang)) {
            messageService.addMessage(TextTranslator.translate(lang, "en", message), MessageType.valueOf(type), code, message);

        } else {
            messageService.addMessage(message, MessageType.valueOf(type), code, message);
        }
    }

    @PostMapping("/send/type={type}/code={code}/message={message}/o={originalMessage}")
    public void putMessage(@PathVariable("message") String message, @PathVariable("type") String type, @PathVariable("code") String code, @PathVariable String originalMessage) {
        messageService.addMessage(message, MessageType.valueOf(type), code, originalMessage);
    }

    @PostMapping("/send/type={type}/code={code}/{lang}/message={message}/o={originalMessage}")
    public void putMessageAndTranslate(@PathVariable("message") String message, @PathVariable("type") String type, @PathVariable("code") String code, @PathVariable("lang") String lang, @PathVariable String originalMessage) {
        if (!"en".equals(lang)) {
            messageService.addMessage(TextTranslator.translate(lang, "en", message), MessageType.valueOf(type), code, originalMessage);
        } else {
            messageService.addMessage(message, MessageType.valueOf(type), code, originalMessage);
        }
    }

    @PostMapping("/send/type={type}/code={code}/test")
    public void putTestMessage(@PathVariable("type") String type, @PathVariable("code") String code) {
        messageService.addTestMessage("test", MessageType.valueOf(type), code);
    }

    @RequestMapping("/test/{code}")
    public int codeHasTestMessage(@PathVariable("code") String code) {
        if (messageService.findAll().stream().filter(m -> m.isTestMessage()).filter(m -> m.getCode().equals(code)).count() > 0) {
            return 1;
        } else {
            return 0;
        }
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

    @DeleteMapping("/del/code/{code}")
    public void clearByCode(@PathVariable("code") String code) {
        List<Message> toDelete = messageService.findAll().stream().filter(m -> m.getCode().equals(code)).collect(Collectors.toList());
        for (Message m : toDelete) {
            messageService.deleteOne(m.getId());
        }
    }

    @RequestMapping("/all/{lang}")
    public List<Message> getAllInLang(@PathVariable("lang") String language) {
        List<Message> conversation = messageService.findAll();
        conversation.forEach(t -> t.setContent(TextTranslator.translate("en", language, t.getContent())));
        return conversation;
    }

    @RequestMapping("/getMessages/code={code}/lang={lang}")
    public List<Message> getMessagesToCodeAndLang(@PathVariable("code") String code, @PathVariable("lang") String lang) {
        List<Message> conversation = messageService.findByCode(code);
        conversation.forEach(t -> t.setContent(TextTranslator.translate("en", lang, t.getContent())));
        return conversation;
    }

    @GetMapping("/faq/{lang}")
    public List<Faq> getFaq(@PathVariable("lang") String lang) {
        List<Faq> list = new ArrayList<>();
        File file = new File("faq_" + lang + ".txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] lineSpilt = line.split(";");
                if (lineSpilt.length >= 2) {
                    if (lineSpilt[1].equals("true")) {
                        String[] options = lineSpilt[4].split("@");
                        list.add(new Faq(lineSpilt[0], true, lineSpilt[2], lineSpilt[3], options));
                    } else {
                        list.add(new Faq(lineSpilt[0], false));
                    }
                } else {
                    list.add(new Faq(line, false));
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            logger.log(Level.ALL,e.toString(),e);
        }
        return list;
    }

    @RequestMapping("/count/{code}")
    public int count(@PathVariable("code") String code) {
        return (int) messageService.findAll().stream().filter(m -> m.getCode().equals(code)).count();
    }

    @RequestMapping("/generate")
    public Code getCode() {
        Code code;
        boolean isUnique;
        do {
            code = CodeGenerator.generateCode();
            isUnique = true;
            for (Message m : messageService.findAll()) {
                if (m.getCode().equals(code.getCode())) {
                    isUnique = false;
                    break;
                }
            }
        }
        while (!isUnique);
        return code;
    }
}

