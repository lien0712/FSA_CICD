package api.models;

public class CustomerRequest {
    private String title;

    public CustomerRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}