package pl.edu.utp.newTranslator2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.utp.newTranslator2.entity.Message;
import pl.edu.utp.newTranslator2.enums.MessageType;
import pl.edu.utp.newTranslator2.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void addMessage(String message, MessageType messageType, String code, String orignial) {
        Message m = new Message();
        m.setContent(message);
        m.setMessageType(messageType);
        m.setCode(code);
        m.setOriginalContent(orignial);
        messageRepository.save(m);
    }

    public void addTestMessage(String message, MessageType messageType, String code) {
        Message m = new Message();
        m.setContent(message);
        m.setMessageType(messageType);
        m.setCode(code);
        m.setTestMessage(true);
        messageRepository.save(m);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findByCode(String code) {
        return messageRepository.findAll().stream().filter(r->!r.isTestMessage()).filter(r -> r.getCode().equals(code)).collect(Collectors.toList());
    }

    public void deleteOne(long id) {
        Message message = messageRepository.getOne(id);
        messageRepository.delete(message);
    }

    public void deleteAll() {
        messageRepository.deleteAll();
    }
}
