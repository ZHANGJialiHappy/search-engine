package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Ranking class ranks a hashset of pages by scores, there're 2 ways to
 * calculate pages' scores.
 */
public class Ranking {
    private Map<Page, Double> pagesWithScore;
    private List<Page> sortedPages;
    private static boolean isFrequencyInverse = true;

    public Ranking() {
        pagesWithScore = new HashMap<>();
        sortedPages = new ArrayList<>();
    }

    /**
     * Ranks the pages according to the number of occurences of a specific search
     * word
     *
     * @param unionPages  the set of pages which are being ranked
     * @param searchWords the searchWords that are being counted on the number of
     *                    occurences
     *
     * @return A set of ranked pages
     */
    public List<Page> rankPages(Set<Page> unionPages, List<String[]> searchWords, int quantityOfPages,
            Map<String, Map<Page, Integer>> invertedIndex) {
        for (Page page : unionPages) {
            List<Double> scoreList = getScoreList(searchWords, page, quantityOfPages, invertedIndex);

            double score = (double) Collections.max(scoreList);
            pagesWithScore.put(page, score);
        }
        return sorteMap();
    }

    private static List<Double> getScoreList(List<String[]> searchWords, Page page, int quantityOfPages,
            Map<String, Map<Page, Integer>> invertedIndex) {
        List<Double> scoreList = new ArrayList<>();
        Integer occurrenceSum = 0;
        for (Integer d : page.getContent().values()) {
            occurrenceSum += d;
        }
        for (String[] wordsWithAnd : searchWords) {
            Double scorePerAnd = 0.0;
            for (int i = 0; i < wordsWithAnd.length; i++) {
                Map<String, Integer> content = page.getContent();
                if (content.containsKey(wordsWithAnd[i])) {
                    if (isFrequencyInverse) {
                        int quantityOfPagePerWord = invertedIndex.get(wordsWithAnd[i]).size();
                        scorePerAnd += (double) content.get(wordsWithAnd[i]) / occurrenceSum
                                * Math.log(quantityOfPages / quantityOfPagePerWord);
                    } else {
                        scorePerAnd += (double) content.get(wordsWithAnd[i]) / occurrenceSum;
                    }
                }
            }
            scoreList.add(scorePerAnd);
        }
        return scoreList;
    }

    /**
     * sort hashmap
     * 
     * @return Returns list of sorted pages
     */
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

    public boolean getIsFrequencyInverse() {
        return isFrequencyInverse;
    }

}
