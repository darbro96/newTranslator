package pl.edu.utp.newTranslator2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Faq {
    private String text;
    private boolean dialog;
    private String template="";
    private String returnedText="";
    private String[] options;

    public Faq(String text, boolean dialog)
    {
        this.text=text;
        this.dialog=dialog;
    }
}
