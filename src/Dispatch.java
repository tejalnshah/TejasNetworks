/**
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * @author USHAHTE
 * @date Jul 17, 2015
 */

public class Dispatch {

    private String[] headers;
    List<String[]> rows;

    public Dispatch() {
        CsvParserSettings parserSettings = new CsvParserSettings();
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(new FileReader("resources" + System.getProperty("file.separator") + "dispatch.csv"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.headers = rowProcessor.getHeaders();
        this.rows = rowProcessor.getRows();
    }

    public List<String[]> getDispatchRows(String dispatchBoard) {
        List<String[]> results = new ArrayList<String[]>();
        for (String[] dispatchRow : rows) {
            String boardName = dispatchRow[5];
            if (boardName != null && !boardName.isEmpty() && dispatchBoard != null && !dispatchBoard
                    .isEmpty()) {
                if (boardName.equalsIgnoreCase(dispatchBoard)) {
                    results.add(new String[]{dispatchRow[9], dispatchRow[10], dispatchRow[7]});
                }
            }
        }
        return results;
    }

}
