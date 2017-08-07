package dto;

/**
 * Created by David Szilagyi on 2017. 08. 07..
 */
public class Share {
    private String email;
    private boolean readOnly;
    private Token token;

    public Share(String email, boolean readOnly, Token token) {
        this.email = email;
        this.readOnly = readOnly;
        this.token = token;
    }

    public Share(String email, Token token) {
        this.email = email;
        this.token = token;
    }

    public Share() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
