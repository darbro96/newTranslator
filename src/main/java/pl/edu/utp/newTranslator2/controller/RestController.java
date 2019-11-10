package pl.edu.utp.newTranslator2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.utp.newTranslator2.entity.Message;
import pl.edu.utp.newTranslator2.enums.MessageType;
import pl.edu.utp.newTranslator2.service.MessageService;
import pl.edu.utp.newTranslator2.translator.TextTranslator;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
        messageService.addMessage(TextTranslator.translate(lang, "pl", message), MessageType.valueOf(type), code);
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
    public List<Message> getAllInLang(@PathVariable("lang") String language) {
        List<Message> conversation = messageService.findAll();
        conversation.forEach(t -> t.setContent(TextTranslator.translate("pl", language, t.getContent())));
        return conversation;
    }

    @GetMapping("/faq/{lang}")
    public List<String> getFaq(@PathVariable("lang") String lang) {
        List<String> list = new ArrayList<>();
        try {
            URL url = getClass().getClassLoader().getResource("faq_"+lang+".txt");
            File file = Paths.get(url.toURI()).toFile();
            try(BufferedReader reader=new BufferedReader(new FileReader(file)))
            {
                String line=reader.readLine();
                while(line!=null)
                {
                    list.add(line);
                    line=reader.readLine();
                }
            } catch (IOException e) {
                list = Arrays.asList("Pokój","Klucz do pralki","Brak prądu","Przepalona żarówka","Czy administracja jest otwarta?");
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            list = Arrays.asList("Pokój","Klucz do pralki","Brak prądu","Przepalona żarówka","Czy administracja jest otwarta?");
            e.printStackTrace();
        }
        return list;
    }
}

