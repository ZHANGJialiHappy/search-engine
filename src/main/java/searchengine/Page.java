package searchengine;

import java.util.Map;

public class Page {
    private String url;
    private String title;
    private Map<String, Integer> content;

    public Page(String url, String title, Map<String, Integer> content) {
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

    public Map<String, Integer> getContent() {
        return content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(Map<String, Integer> content) {
        this.content = content;
    }

}
