package pl.edu.utp.newTranslator2.codeGenerator;

import java.util.Random;

public class CodeGenerator {

    public static Code generateCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            codeBuilder.append(randomSign());
        }
        codeBuilder.append("-");
        for (int i = 0; i < 3; i++) {
            codeBuilder.append(randomSign());
        }
        return new Code(codeBuilder.toString());
    }

    private static char randomSign() {
        char generatedChar;
        do {
            Random random = new Random();
            int number = random.nextInt(26) + 97;
            byte randomChar = (byte) number;
            generatedChar = (char) randomChar;
        }
        while (!Character.isLetterOrDigit(generatedChar));
        return generatedChar;
    }

    private CodeGenerator()
    {

    }
}
