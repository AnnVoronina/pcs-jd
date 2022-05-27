import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    File pdfsDir;
    HashMap<String, List<PageEntry>> wordStorage = new HashMap<>();


    public BooleanSearchEngine(File pdfsDir) throws IOException {
        this.pdfsDir=pdfsDir;
        Map<String, Integer> wordAndCount = new HashMap<>();
        for (File fileName : pdfsDir.listFiles()) {
            if (!fileName.isFile() && !fileName.getName().endsWith("pdf")) {
                continue;
            }
            {//doc.getNumberOfPdfObjects()
                var doc = new PdfDocument(new PdfReader(fileName));
                for (int pageNumber = 1; pageNumber <=doc.getNumberOfPdfObjects() ; pageNumber++) {
                    var text = PdfTextExtractor.getTextFromPage(doc.getPage(pageNumber));
                    var words = (text.split("\\P{IsAlphabetic}+"));
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        wordAndCount.put(word.toLowerCase(), wordAndCount.getOrDefault(word.toLowerCase(), 0) + 1);
                    }
                    for (var w : wordAndCount.entrySet()) {
                        List<PageEntry> pageEntryList = new ArrayList<>();
                        if (wordStorage.containsKey(w.getKey())){
                            pageEntryList = wordStorage.get(w.getKey());
                        }
                        pageEntryList.add(new PageEntry(fileName.getName(),pageNumber,w.getValue()));
                        wordStorage.put(w.getKey(),pageEntryList);
                    }
                }
            }

        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return wordStorage.get(word);
    }

}
