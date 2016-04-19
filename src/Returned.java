/**
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * @author USHAHTE
 * @date Jul 17, 2015
 */

public class Returned {

    private String[] headers;
    List<String[]> rows;
    LinkedHashMap<String, ArrayList<ReturnedComponents>> returnedComponentsMap = new LinkedHashMap<String, ArrayList<ReturnedComponents>>();
    Map<String, Integer> componentWiseReturns = new HashMap<String, Integer>();

    public Returned() {
        CsvParserSettings parserSettings = new CsvParserSettings();
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(new FileReader("resources" + System.getProperty("file.separator") + "returned.csv"));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        this.headers = rowProcessor.getHeaders();
        this.rows = rowProcessor.getRows();

        for (String[] eachRow : rows) {
            String component = eachRow[0];
            Integer returnedQuantity = Integer.parseInt(eachRow[1]);
            String returnedDocNumber = eachRow[2];
            String returnedDate = eachRow[3];

            if (returnedComponentsMap.containsKey(component)) {
                returnedComponentsMap.get(component).add(
                        new ReturnedComponents(
                                component,
                                returnedQuantity,
                                returnedDocNumber,
                                returnedDate));

            } else {
                ArrayList<ReturnedComponents> componentArrayList = new ArrayList<ReturnedComponents>();
                componentArrayList.add(new ReturnedComponents(
                        component,
                        returnedQuantity,
                        returnedDocNumber,
                        returnedDate));
                returnedComponentsMap.put(component, componentArrayList);
            }
        }

        for (String component : returnedComponentsMap.keySet()) {
            Integer totalReturned = 0;
            ArrayList<ReturnedComponents> returnedComponentsList = returnedComponentsMap
                    .get(component);
            if (returnedComponentsList != null) {
                for (ReturnedComponents returnedComponent : returnedComponentsList) {
                    totalReturned += returnedComponent.getReturnedQuantity();
                }
            }
            componentWiseReturns.put(component, totalReturned);
        }

    }

    public List<String[]> getReturnedRows() {
        return rows;
    }

    public String[] getReturnedHeaders() {
        return headers;
    }

    public LinkedHashMap<String, ArrayList<ReturnedComponents>> getReturnedMap() {
        return returnedComponentsMap;
    }

    /**
     * @param component
     * @param balance
     * @param postingDate
     * @return
     */
    public Integer processReturned(String component, Integer balance, String postingDate) {

        ArrayList<ReturnedComponents> returnedComponentsList = returnedComponentsMap.get(component);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        Integer returned = 0;
        Integer tempReturned = 0;
        int rowCount = 0;

        if (returnedComponentsList != null) {

            while (returnedComponentsList.size() > 0) {
                ReturnedComponents firstReturnedRow = returnedComponentsList.get(0);
                Date ReturnDateSDF = null;
                Date postingDateSDF = null;
                try {
                    ReturnDateSDF = sdf.parse(firstReturnedRow.getReturnedDate());
                    postingDateSDF = sdf.parse(postingDate);

                    if (postingDateSDF.compareTo(ReturnDateSDF) < 0) {
                        Integer returnedOnThisDate = firstReturnedRow.getReturnedQuantity();
                        if (balance >= returnedOnThisDate) {
                            returned += returnedOnThisDate;
                            balance = balance - returnedOnThisDate;
                            returnedComponentsList.remove(0);
                        } else {
                            break;
                        }
                    } else
                        break;

                }

                catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

        return returned;
    }

    public Integer getComponentwiseReturns(String component) {

        if (componentWiseReturns.containsKey(component)) {
            return componentWiseReturns.get(component);
        } else {
            return 0;
        }
    }
}
