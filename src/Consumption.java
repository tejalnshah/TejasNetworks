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

public class Consumption {

    private String[] headers;
    List<String[]> rows;

    public Consumption() {
        CsvParserSettings parserSettings = new CsvParserSettings();
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(new FileReader("resources" + System.getProperty("file.separator") + "consumption.csv"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.headers = rowProcessor.getHeaders();
        this.rows = rowProcessor.getRows();
    }

    public List<String[]> getConsumtpionRows() {
        return rows;
    }

    public String[] getConsumptionHeaders() {
        return headers;
    }

    public List<String> getBoardList() {
        List<String> boardNamesList = new ArrayList<String>();
        String[] consumptionHeader = getConsumptionHeaders();
        for (int index = 2; index < consumptionHeader.length; index++) {

            String board = consumptionHeader[index];
            if (board.contains(" ")) {
                board = board.substring(0, board.indexOf(" "));
            }

            boardNamesList.add(board);
        }
        return boardNamesList;
    }
}
