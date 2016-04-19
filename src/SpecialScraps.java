/**
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * @author USHAHTE
 * @date Jul 17, 2015
 */

public class SpecialScraps {

    private String[] headers;
    List<String[]> rows;
    HashMap<String, ArrayList<String>> scrapMap;

    public SpecialScraps() {
        CsvParserSettings parserSettings = new CsvParserSettings();
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(new FileReader("resources" + System.getProperty("file.separator") + "SpecialScraps.csv"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.headers = rowProcessor.getHeaders();
        this.rows = rowProcessor.getRows();
        this.scrapMap = getSpecialScrapMap();
    }

    public List<String[]> getScrapRows() {
        return rows;
    }

    public String[] getScrapSpecsHeaders() {
        return headers;
    }

    public HashMap<String, ArrayList<String>> getSpecialScrapMap() {

    	HashMap<String, ArrayList<String>> scrapMap = new HashMap<String, ArrayList<String>>();

        for (int index = 0; index < getScrapRows().size(); index++) {
        	
        	String[] row = getScrapRows().get(index);
            String component = row[0];
            String annexureNumber = row[1];
            
        	if (scrapMap.containsKey(component)) {
        		scrapMap.get(component).add(annexureNumber);

            } else {
                ArrayList<String> componentArrayList = new ArrayList<String>();
                componentArrayList.add(annexureNumber);
                scrapMap.put(component, componentArrayList);
            }
        }

        return scrapMap;
    }
}
