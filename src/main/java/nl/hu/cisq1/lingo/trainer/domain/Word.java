package nl.hu.cisq1.lingo.trainer.domain;

public class Word {
    private String value;
    private Integer length;

    public Word(){}
    public Word(String value) {
        this.value = value;
        this.length = length;
    }


    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }


    public String getValue() {
        return value;
    }

}
