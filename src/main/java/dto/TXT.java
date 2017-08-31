package dto;

/**
 * Created by David Szilagyi on 2017. 08. 03..
 */
public class TXT {
    private String name;
    private String content;
    private boolean modify;
    private Token token;

    public TXT(String name, String content, boolean modify, Token token) {
        this.name = name;
        this.content = content;
        this.modify = modify;
        this.token = token;
    }

    public TXT(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public TXT() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
