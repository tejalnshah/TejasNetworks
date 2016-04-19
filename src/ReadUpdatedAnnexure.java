import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class ReadUpdatedAnnexure {

    private String[] headers;
    List<String[]> rows;
    LinkedHashMap<String, ArrayList<AnnexureUpdationData>> annexureUpdationDataMap;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<AnnexureUpdationData>>> monthlyUpdationDataMap;
    HashMap<String, Integer> materialTotalQuantities;

    public ReadUpdatedAnnexure() {

        annexureUpdationDataMap = new LinkedHashMap<String, ArrayList<AnnexureUpdationData>>();
        monthlyUpdationDataMap = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<AnnexureUpdationData>>>();

        CsvParserSettings parserSettings = new CsvParserSettings();
        RowListProcessor rowProcessor = new RowListProcessor();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setRowProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(parserSettings);

        try {
            parser.parse(new FileReader("resources" + System.getProperty("file.separator") + "annexureUpdation.csv"));
        } catch (FileNotFoundException e) {
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

    public LinkedHashMap<String, ArrayList<AnnexureUpdationData>> getAnnexureUpdationData() {

        for (String[] updationRow : rows) {
            String postingDate = updationRow[0];
            String component = updationRow[1];
            String quantity = updationRow[3];
            String matDoc = updationRow[4];
            String annexNumber = updationRow[5];
            String annexDate = updationRow[6];

            AnnexureUpdationData updationData = new AnnexureUpdationData(
                    postingDate,
                    annexDate,
                    component,
                    quantity,
                    matDoc,
                    annexNumber);

            if (annexureUpdationDataMap.containsKey(annexNumber)) {
                // Add this results object to it arraylist value
                (annexureUpdationDataMap.get(annexNumber)).add(updationData);
            } else {
                // Put this component - results obj in a key - value pair
                ArrayList<AnnexureUpdationData> annexureUpdationDataArrayList = new ArrayList<AnnexureUpdationData>();
                annexureUpdationDataArrayList.add(updationData);
                annexureUpdationDataMap.put(annexNumber, annexureUpdationDataArrayList);
            }
        }
        return annexureUpdationDataMap;
    }

    /**
     * @return
     */
    public LinkedHashMap<String, LinkedList<String>> getComponentAnnexureLinkedList() {
        // TODO Auto-generated method stub

        LinkedHashMap<String, LinkedList<String>> componentAnnexureMap = new LinkedHashMap<String, LinkedList<String>>();

        for (String[] updationRow : rows) {
            String component = updationRow[1];
            String annexNumber = updationRow[5];

            if (componentAnnexureMap.containsKey(component)) {
                // Add this results object to it arraylist value
                (componentAnnexureMap.get(component)).add(annexNumber);
            } else {
                // Put this component - results obj in a key - value pair
                LinkedList<String> annexList = new LinkedList<String>();
                annexList.add(annexNumber);
                componentAnnexureMap.put(component, annexList);
            }
        }
        return componentAnnexureMap;
    }

    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<AnnexureUpdationData>>> getMonthlyUpdationData() {

        for (String[] updationRow : rows) {
            String postingDate = updationRow[0];
            String component = updationRow[1];
            String quantity = updationRow[3];
            String matDoc = updationRow[4];
            String annexNumber = updationRow[5];
            String annexDate = updationRow[6];

            AnnexureUpdationData updationData = new AnnexureUpdationData(
                    postingDate,
                    annexDate,
                    component,
                    quantity,
                    matDoc,
                    annexNumber);

            String annexureDate = parseDate(annexDate);

            if (monthlyUpdationDataMap.containsKey(annexureDate)) {
                // Add this results object to it arraylist value
                LinkedHashMap<String, ArrayList<AnnexureUpdationData>> componentwiseUpdationDataMap = monthlyUpdationDataMap
                        .get(annexDate);
                if (componentwiseUpdationDataMap.containsKey(component)) {
                    // Add this results object to it arraylist value
                    ArrayList<AnnexureUpdationData> updationArrayList = componentwiseUpdationDataMap
                            .get(component);
                    updationArrayList.add(updationData);
                } else {
                    // Put this component - results obj in a key - value pair
                    ArrayList<AnnexureUpdationData> componentwiseUpdationDataArrayList = new ArrayList<AnnexureUpdationData>();
                    componentwiseUpdationDataArrayList.add(updationData);
                    componentwiseUpdationDataMap.put(component, componentwiseUpdationDataArrayList);
                    monthlyUpdationDataMap.put(annexureDate, componentwiseUpdationDataMap);
                }

            } else {
                // Put this component - results obj in a key - value pair
                LinkedHashMap<String, ArrayList<AnnexureUpdationData>> componentwiseUpdationDataMap = new LinkedHashMap<String, ArrayList<AnnexureUpdationData>>();
                ArrayList<AnnexureUpdationData> monthlyUpdationDataArrayList = new ArrayList<AnnexureUpdationData>();
                monthlyUpdationDataArrayList.add(updationData);
                componentwiseUpdationDataMap.put(component, monthlyUpdationDataArrayList);
                monthlyUpdationDataMap.put(annexureDate, componentwiseUpdationDataMap);
            }
        }
        return monthlyUpdationDataMap;
    }

    public String parseDate(String inputDate) {

        DateFormat inputDF = new SimpleDateFormat("MM/dd/yy");
        String monthString = "";

        Date date1;
        try {
            date1 = inputDF.parse(inputDate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);

            Integer month = cal.get(Calendar.MONTH);
            Integer year = cal.get(Calendar.YEAR);

            monthString = month.toString() + "/" + year.toString();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return monthString;
    }
}
