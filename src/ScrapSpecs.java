/**
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * @author USHAHTE
 * @date Jul 17, 2015
 */

public class ScrapSpecs {

    private String[] headers;
    List<String[]> rows;
    Map<String, Integer> scrapMap;

    public ScrapSpecs() {
        CsvParserSettings parserSettings = new CsvParserSettings();
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(new FileReader("resources" + System.getProperty("file.separator") + "ScrapSpecs.csv"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.headers = rowProcessor.getHeaders();
        this.rows = rowProcessor.getRows();
        this.scrapMap = getScrapMap();
    }

    public List<String[]> getScrapRows() {
        return rows;
    }

    public String[] getScrapSpecsHeaders() {
        return headers;
    }

    public HashMap<String, Integer> getScrapMap() {

        HashMap<String, Integer> scrapMap = new HashMap<String, Integer>();

        for (int index = 0; index < getScrapRows().size(); index++) {
            String[] row = getScrapRows().get(index);
            String component = row[0];
            String annexureNumber = row[1];
            scrapMap.put(component + "@" + annexureNumber, Integer.parseInt(row[2]));
        }

        return scrapMap;
    }

    public Integer getNewScrap(String component, String annexureNumber, Integer oldScrap) {

        Integer newScrap = scrapMap.get(component + "@" + annexureNumber);
        return (newScrap == null ? oldScrap : newScrap);
    }
    
    public boolean dontResetScrap(String component, String annexureNumber) {
    	return scrapMap.containsKey(component + "@" + annexureNumber);
    }
}
