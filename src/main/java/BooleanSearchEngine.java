import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> resultMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        String[] allFiles = pdfsDir.list();
        for (String file : allFiles) {
            var doc = new PdfDocument(new PdfReader(pdfsDir.getPath() + "/" + file));
            for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                Map<String, Integer> tempMapByPage = new HashMap<>();
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.toLowerCase().split(("\\P{IsAlphabetic}+"));
                for (String word : words) {
                    tempMapByPage.put(word, (tempMapByPage.getOrDefault(word, 0) + 1));
                }
                for (Map.Entry<String, Integer> entry : tempMapByPage.entrySet()) {
                    if (resultMap.containsKey(entry.getKey())) {
                        List<PageEntry> temp = resultMap.get(entry.getKey());
                        temp.add(new PageEntry(file, i, entry.getValue()));

                        resultMap.replace(entry.getKey(), temp);
                    } else {
                        resultMap.put(entry.getKey(), new ArrayList<>
                                (Arrays.asList(new PageEntry(file, i, entry.getValue()))));
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return resultMap.get(word.toLowerCase());
    }
}
