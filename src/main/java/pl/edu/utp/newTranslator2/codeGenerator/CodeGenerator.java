package pl.edu.utp.newTranslator2.codeGenerator;

import java.util.Random;

public class CodeGenerator {

    public static Code generateCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            codeBuilder.append(String.valueOf(randomSign()));
        }
        codeBuilder.append("-");
        for (int i = 0; i < 3; i++) {
            codeBuilder.append(String.valueOf(randomSign()));
        }
        Code code=new Code(codeBuilder.toString());
        return code;
    }

    private static char randomSign() {
        char generatedChar;
        do {
            Random random = new Random();
            int number = random.nextInt(74) + 48;
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
