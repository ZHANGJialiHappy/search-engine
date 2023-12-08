package searchengine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchService {

    public List<String> handleRequest(String searchTerm, Map<String, Map<Page, Integer>> invertedIndex,
            int quantityOfPages) {
        var response = new ArrayList<String>();
        var searchList = handleSearchTerm(searchTerm.toLowerCase(), invertedIndex, quantityOfPages);
        for (Page page : searchList) {
            response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
                    page.getUrl(), page.getTitle()));
        }
        return response;
    }

    private List<Page> handleSearchTerm(String searchTerm, Map<String, Map<Page, Integer>> invertedIndex,
            int quantityOfPages) {
        String decodedSearchTerm = "";
        try {
            decodedSearchTerm = URLDecoder.decode(searchTerm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String[]> searchWords = new ArrayList<>();
        String[] wordsSplitedByOr = decodedSearchTerm.split(" or ");
        for (int i = 0; i < wordsSplitedByOr.length; i++) {
            searchWords.add(wordsSplitedByOr[i].split(" "));
        }

        Set<Page> unionPages = new HashSet<>();
        for (String[] wordsWithAnd : searchWords) {
            Set<Page> intersectionPages = getIntersection(wordsWithAnd, invertedIndex);
            for (Page page : intersectionPages) {
                unionPages.add(page);
            }
        }
        Ranking rankedPages = new Ranking();

        return rankedPages.rankPages(unionPages, searchWords, quantityOfPages, invertedIndex);
    }

    private Set<Page> getIntersection(String[] wordsWithAnd, Map<String, Map<Page, Integer>> invertedIndex) {
        Set<Page> intersectionPages = new HashSet<>(search(wordsWithAnd[0], invertedIndex));
        for (int i = 0; i < wordsWithAnd.length; i++) {
            Set<Page> subPages = search(wordsWithAnd[i], invertedIndex);
            intersectionPages.retainAll(subPages);
        }
        return intersectionPages;
    }

    private Set<Page> search(String searchWord, Map<String, Map<Page, Integer>> invertedIndex) {
        Set<Page> result = new HashSet<>();
        if (!invertedIndex.containsKey(searchWord)) {
            return result;
        }
        result = invertedIndex.get(searchWord).keySet();
        return result;
    }

}
