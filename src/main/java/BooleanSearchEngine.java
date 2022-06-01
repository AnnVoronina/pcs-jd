import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    File pdfsDir;
    HashMap<String, List<PageEntry>> wordStorage = new HashMap<>();


    public BooleanSearchEngine(File pdfsDir) throws IOException {


        for (File fileName : pdfsDir.listFiles()) {
            if (!fileName.getName().endsWith("pdf")) {
                continue;
            }
            var doc = new PdfDocument(new PdfReader(fileName));
            for (int pageNumber = 1; pageNumber <= doc.getNumberOfPages(); pageNumber++) {
                var textOfPage = PdfTextExtractor.getTextFromPage(doc.getPage(pageNumber));
                var wordsOnPage = (textOfPage.split("\\P{IsAlphabetic}+"));
                Map<String, Integer> wordAndCount = new HashMap<>();
                for (var word : wordsOnPage) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    wordAndCount.put(word.toLowerCase(), wordAndCount.getOrDefault(word.toLowerCase(), 0) + 1);
                }
                for (var w : wordAndCount.entrySet()) {
                    List<PageEntry> pageEntryList = new ArrayList<>();
                    if (wordStorage.containsKey(w.getKey())) {
                        pageEntryList = wordStorage.get(w.getKey());
                    }
                    pageEntryList.add(new PageEntry(fileName.getName(), pageNumber, w.getValue()));
                    wordStorage.put(w.getKey().toLowerCase(), pageEntryList);
                }
            }
        }

    }


    @Override
    public List<PageEntry> search(String word) {
        return wordStorage.get(word.toLowerCase());
    }

}
