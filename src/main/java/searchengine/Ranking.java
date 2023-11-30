package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Ranking {
    private Map<Page, Double> pagesWithScore;
    private List<Page> sortedPages;
    private boolean frequencyInverse;

    public Ranking() {
        pagesWithScore = new HashMap<>();
        sortedPages = new ArrayList<>();
        frequencyInverse = true;
    }

    public List<Page> rankPages(Set<Page> wordInUnion, List<String[]> searchWords) {
        for (Page page : wordInUnion) {
            List<Integer> occurrenceNumList = new ArrayList<>();
            for (String[] wordsWithAnd : searchWords) {
                int occurrenceNum = 0;
                for (int i = 0; i < wordsWithAnd.length; i++) {
                    Map<String, Integer> content = page.getContent();
                    if (content.containsKey(wordsWithAnd[i])) {
                        occurrenceNum += content.get(wordsWithAnd[i]);
                    }
                }
                occurrenceNumList.add(occurrenceNum);
            }
            Integer occurrenceSum = 0;
            for (Integer d : page.getContent().values()) {
                occurrenceSum += d;
            }
            double score = frequencyInverse ? Collections.max(occurrenceNumList) / occurrenceSum
                    : Collections.max(occurrenceNumList);
            pagesWithScore.put(page, score);
        }
        return sorteMap();
    }

    private List<Page> sorteMap() {
        List<Double> list = new ArrayList<>();
        for (Entry<Page, Double> entry : pagesWithScore.entrySet()) {
            double score = entry.getValue();
            if (!list.contains(score))
                list.add(score);
        }
        Collections.sort(list);
        Collections.reverse(list);
        for (double num : list) {
            for (Entry<Page, Double> entry : pagesWithScore.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedPages.add(entry.getKey());
                }
            }
        }
        return sortedPages;
    }

}
