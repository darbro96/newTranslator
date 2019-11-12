package pl.edu.utp.newTranslator2.codeGenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    public void testGenerateCode() {
        Code code=CodeGenerator.generateCode();
        System.out.println("Wygenerowano: "+code);
        assertTrue(code.getCode().matches("\\w\\w\\w-\\w\\w\\w"));
    }
}