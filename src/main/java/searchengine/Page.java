package searchengine;

import java.util.Map;

public class Page {
    private String url;
    private String title;
    private Map<String, Double> content;

    public Page(String url, String title, Map<String, Double> content) {
        this.url = url;
        this.title = title;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, Double> getContent() {
        return content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(Map<String, Double> content) {
        this.content = content;
    }

}
