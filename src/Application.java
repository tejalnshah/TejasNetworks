import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author USHAHTE
 * @date Jul 17, 2015
 */

public class Application {

    public static void main(String[] args) {

        Consumption consumption = new Consumption();

        Dispatch dispatch = new Dispatch();

        Returned returnedComponents = new Returned();

        List<Results> results = new ArrayList<Results>();

        List<String[]> consumptionRows = consumption.getConsumtpionRows();
        String[] headers = consumption.getConsumptionHeaders();

        LinkedHashMap<String, ArrayList<Results>> resultsMap = new LinkedHashMap<String, ArrayList<Results>>();

        // Component backlog map
        LinkedHashMap<String, Integer> componentBacklogList = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> returnedAccountedList = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer[]> annexureTotals = new LinkedHashMap<String, Integer[]>();
        LinkedHashMap<String, String> annexureBalances = new LinkedHashMap<String, String>();

        LinkedHashMap<String, ArrayList<String>> openAnnexuresMap = new LinkedHashMap<String, ArrayList<String>>();

        for (String[] consumptionRow : consumptionRows) {
            String component = consumptionRow[0];
            componentBacklogList.put(component, 0);
            for (int index = 2; index < consumptionRow.length; index++) {
                if (consumptionRow[index] != null) {
                    String board = headers[index];
                    String compQuantity = consumptionRow[index];

                    String dispatchBoard = board;
                    if (board.contains(" ")) {
                        dispatchBoard = board.substring(0, board.indexOf(" "));
                    }

                    List<String[]> dispatchDetails = dispatch.getDispatchRows(dispatchBoard);

                    for (String[] dispatchDetailsRow : dispatchDetails) {
                        String invNumber = dispatchDetailsRow[0];
                        String invDate = dispatchDetailsRow[1];
                        String boardQuantity = dispatchDetailsRow[2];

                        int i = Integer.parseInt(boardQuantity);
                        int j = Integer.parseInt(compQuantity);
                        Integer k = i * j;
                        String totalQuantity = k.toString();

                        Results newResult = new Results(
                                component,
                                dispatchBoard,
                                invNumber,
                                invDate,
                                boardQuantity,
                                compQuantity,
                                totalQuantity);

                        if (!(newResult.checkIfSame(results))) {

                            results.add(newResult);

                            if (resultsMap.containsKey(component)) {
                                // Add this results object to it arraylist value
                                (resultsMap.get(component)).add(newResult);
                            } else {
                                // Put this component - results obj in a key - value pair
                                ArrayList<Results> resultsArrayList = new ArrayList<Results>();
                                resultsArrayList.add(newResult);
                                resultsMap.put(component, resultsArrayList);
                            }
                        }
                    }
                }
            }
        }

        // Arrange the Results
        for (String key : resultsMap.keySet()) {
            ArrayList<Results> resultsArrayList = resultsMap.get(key);
            Collections.sort(resultsArrayList);
        }

        ArrayList<Results> forPrinting = new ArrayList<Results>();
        for (ArrayList<Results> resultsArrayList : resultsMap.values()) {
            for (Results eachResult : resultsArrayList) {
                forPrinting.add(eachResult);
            }
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter("resources" + System.getProperty("file.separator") + "results.csv", "UTF-8");
            writer.println(Results.getHeader());
            for (Results resultRow : forPrinting) {
                writer.println(Results.getRow(resultRow));
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Read Annexure Updation
        ReadUpdatedAnnexure annexureUpdation = new ReadUpdatedAnnexure();
        LinkedHashMap<String, ArrayList<AnnexureUpdationData>> annexureUpdationDataMap = annexureUpdation
                .getAnnexureUpdationData();
        LinkedHashMap<String, LinkedList<String>> componentAnnexureMap = annexureUpdation
                .getComponentAnnexureLinkedList();
        LinkedHashMap<String, ArrayList<String[]>> allFinalRows = new LinkedHashMap<String, ArrayList<String[]>>();
        LinkedHashMap<String, Component> componentsMap = new LinkedHashMap<String, Component>();

        for (Map.Entry<String, ArrayList<AnnexureUpdationData>> entry : annexureUpdationDataMap
                .entrySet()) {
            String annexureNumber = entry.getKey();
            Integer serialNumber = 0;
            ArrayList<String[]> rows = new ArrayList<String[]>();
            ArrayList<AnnexureUpdationData> updationDataArrayList = entry.getValue();
            Integer totalAnnexureQuantity = 0;
            Integer totalAnnexureConsumption = 0;
            Integer totalAnnexureBalance = 0;
            Integer totalAnnexureScrap = 0;
            Integer totalAnnexureReturned = 0;

            for (AnnexureUpdationData annexureData : updationDataArrayList) {
                serialNumber++;
                String postingDate = annexureData.getPostingDate();
                String annexureCode = annexureData.getAnnexureCode();
                String annexureDate = annexureData.getAnnexureDate();

                // Get component quantity from annexure
                String componentQuantityString = annexureData.getQuantity().toString();
                String component = annexureData.getMaterial();
                Integer componentQuantity = annexureData.getQuantity();

                // Get component backlog data from its map
                Integer balanceComponents = componentBacklogList.get(component);

                // Decide how many rows to copy from Results
                Integer totalQuantity = 0;
                try {
                    totalQuantity = componentQuantity + balanceComponents;
                } catch (NullPointerException npe) {
                    System.out
                            .println("Component : " + component + " + Annexure Number : " + annexureNumber + " + CompQuantity : " + componentQuantity + " + balance : " + balanceComponents);
                }
                // Integer totalQuantity = componentQuantity + balanceComponents;

                ArrayList<Results> componentResults = resultsMap.get(component);

                Integer totalCompQuantity = 0;
                int rowCount = 0;
                Integer totalConsumption = 0;
                Integer balance = 0;
                Integer scrap = 0;
                Integer returned = 0;
                List<AnnexureResults> annexureResultsList = null;
                ScrapSpecs scrapData = new ScrapSpecs();
                SpecialScraps specialScrapData = new SpecialScraps(); 
                HashMap<String, ArrayList<String>> specialScraps = new HashMap<String, ArrayList<String>>();
                
                if (component.contains("-ACC") || component.contains("-AYC") || component
                        .contains("-AIC") || component.contains("-AXC")) {
                    scrap = (int) Math.round(totalQuantity * 0.04);
                }
                
                specialScraps = specialScrapData.getSpecialScrapMap();
                if (specialScraps.containsKey(component)) {
                    ArrayList<String> annexuresList = specialScraps.get(component);
                    if (annexuresList.contains(annexureNumber)) {
                        scrap = 0;
                    }
                }

                if (component.equals("999-AYC000126-E")) {
                    scrap = 0;
                }

                scrap = scrapData.getNewScrap(component, annexureNumber, scrap);

                if(totalQuantity <= scrap) {
                	scrap = totalQuantity;
                }
                Integer totalQuantityForUse = totalQuantity - scrap;

                if (componentResults != null) {
                    for (int i = 0; i < componentResults.size(); i++) {
                        Results resultRow = componentResults.get(i);
                        Integer compQuantity = resultRow.getTotalComponentQuantity();
                        totalCompQuantity += compQuantity;
                        if (totalCompQuantity > totalQuantityForUse) {
                            break;
                        }
                        rowCount++;
                    }

                    // Copy rows from Results

                    annexureResultsList = new ArrayList<AnnexureResults>();

                    for (int count = 0; count < rowCount; count++) {
                        String jobWorkerNumber = componentResults.get(count).getJobWokerNumber();
                        String boardShipmentDate = componentResults.get(count)
                                .getBoardShipmentDate();
                        String boardName = componentResults.get(count).getBoard();
                        String boardQuantity = componentResults.get(count).getQuantity();
                        String perBoard = componentResults.get(count).getPerBoard();
                        String consumptionPerBoard = componentResults.get(count)
                                .getTotalComponentQuantityString();

                        AnnexureResults annexResults = new AnnexureResults(
                                jobWorkerNumber,
                                boardShipmentDate,
                                boardName,
                                boardQuantity,
                                perBoard,
                                consumptionPerBoard);

                        annexureResultsList.add(annexResults);

                        totalConsumption += componentResults.get(count).getTotalComponentQuantity();
                    }

                    for (int i = 0; i < rowCount; i++) {
                        componentResults.remove(0);
                    }
                }

                if (totalConsumption == 0 && scrapData.dontResetScrap(component, annexureNumber) == false) {
                    scrap = 0;
                }

                Integer balanceBeforeReturning = totalQuantity - totalConsumption - scrap;
                returned = returnedComponents.processReturned(
                        component,
                        balanceBeforeReturning,
                        postingDate);
                balance = balanceBeforeReturning - returned;

                if (returnedAccountedList.containsKey(component)) {
                    returnedAccountedList.put(
                            component,
                            returnedAccountedList.get(component) + returned);
                } else {
                    returnedAccountedList.put(component, returned);
                }

                int indexOfAnnex = componentAnnexureMap.get(component).indexOf(annexureNumber);
                String balanceToAnnex = "";
                String balanceFromAnnex = "";
                if (indexOfAnnex < componentAnnexureMap.get(component).size() - 1) {
                    balanceToAnnex = componentAnnexureMap.get(component).get(indexOfAnnex + 1);
                }
                if (indexOfAnnex > 0) {
                    balanceFromAnnex = componentAnnexureMap.get(component).get(indexOfAnnex - 1);
                }
                if (balance > 0) {
                    annexureBalances.put(component, annexureNumber);
                }

                AnnexureMainRow finalData = new AnnexureMainRow(
                        postingDate,
                        annexureCode,
                        annexureDate,
                        annexureNumber,
                        serialNumber,
                        component,
                        componentQuantityString,
                        componentBacklogList.get(component),
                        balanceFromAnnex,
                        annexureResultsList,
                        totalConsumption,
                        balance,
                        balanceToAnnex,
                        returned,
                        scrap);

                if (balance != 0 && balanceToAnnex.isEmpty()) {
                    if (openAnnexuresMap.containsKey(annexureNumber)) {
                        ArrayList<String> componentsListFromOpenAnnexures = openAnnexuresMap
                                .get(annexureNumber);
                        componentsListFromOpenAnnexures.add(component);
                    } else {
                        ArrayList<String> componentsListFromOpenAnnexures = new ArrayList<String>();
                        componentsListFromOpenAnnexures.add(component);
                        openAnnexuresMap.put(annexureNumber, componentsListFromOpenAnnexures);
                    }
                }

                for (String[] eachRow : finalData.getRowsFromAnnexures()) {
                    rows.add(eachRow);
                }

                componentBacklogList.put(component, balance);

                totalAnnexureQuantity += totalQuantity;
                totalAnnexureConsumption += totalConsumption;
                totalAnnexureScrap += scrap;
                totalAnnexureReturned += returned;
                totalAnnexureBalance += balance;

                if (componentsMap.containsKey(component)) {
                    Component tempComponent = componentsMap.get(component);
                    if (tempComponent != null) {

                        int newShippedQuantity = tempComponent.getShippedQuantity() + componentQuantity;
                        tempComponent.setShippedQuantity(newShippedQuantity);

                        int newConsumedQuantity = tempComponent.getConsumedQuantity() + totalConsumption;
                        tempComponent.setConsumedQuantity(newConsumedQuantity);

                        int newReturnedQuantity = returnedComponents
                                .getComponentwiseReturns(component);
                        tempComponent.setReturnedQuantity(newReturnedQuantity);

                        int newScrapQuantity = tempComponent.getScrapQuantity() + scrap;
                        tempComponent.setScrapQuantity(newScrapQuantity);

                        int newBalanceQuantity = componentBacklogList.get(component);
                        tempComponent.setBalanceQuantity(newBalanceQuantity);

                        int returnedAccountedQuantity = returnedAccountedList.get(component);
                        tempComponent.setReturnedAccountedQuantity(returnedAccountedQuantity);

                        boolean newBalanceStatus = (newShippedQuantity == (newConsumedQuantity + newScrapQuantity + newBalanceQuantity + returnedAccountedQuantity))
                                                                                                                                                                    ? true
                                                                                                                                                                    : false;
                        String annexBalances = annexureBalances.get(component);
                        tempComponent.setAnnexureBalances(annexBalances);

                        tempComponent.setBalancing(newBalanceStatus);
                    }
                } else {
                    int returnedForNewComponents = 0;
                    if (returnedAccountedList.containsKey(component)) {
                        returnedForNewComponents = returnedAccountedList.get(component);
                    } else {
                        returnedForNewComponents = returnedComponents
                                .getComponentwiseReturns(component);
                    }
                    boolean balanceStatus = (componentQuantity == (totalConsumption + scrap + componentBacklogList
                            .get(component) + returnedForNewComponents)) ? true : false;
                    componentsMap.put(component, new Component(
                            component,
                            componentQuantity,
                            totalConsumption,
                            scrap,
                            componentBacklogList.get(component),
                            balanceStatus,
                            returnedComponents.getComponentwiseReturns(component),
                            returnedAccountedList.get(component),
                            annexureBalances.get(component)));
                }

            }
            allFinalRows.put(annexureNumber, rows);
            annexureTotals.put(annexureNumber, new Integer[]{totalAnnexureQuantity,
                                                             totalAnnexureConsumption,
                                                             totalAnnexureScrap,
                                                             totalAnnexureReturned,
                                                             totalAnnexureBalance});

        }

        PrintWriter writer5;
        try {
            writer5 = new PrintWriter(
            		"resources" + System.getProperty("file.separator") + "OpenAnnexures.csv",
                    "UTF-8");

            writer5.println("Annexure Number, Components List");

            for (Map.Entry<String, ArrayList<String>> entry : openAnnexuresMap.entrySet()) {
                String annexNumber = entry.getKey();
                ArrayList<String> componentsListFromOpenAnnexures = entry.getValue();

                for (int index = 0; index < componentsListFromOpenAnnexures.size(); index++) {
                    if (index == 0) {
                        writer5.println(annexNumber + "," + componentsListFromOpenAnnexures
                                .get(index));
                    } else {
                        writer5.println("" + "," + componentsListFromOpenAnnexures.get(index));
                    }
                }
            }
            writer5.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        PrintWriter writer2;
        try {
            writer2 = new PrintWriter(
            		"resources" + System.getProperty("file.separator") + "ComponentsData.csv",
                    "UTF-8");

            writer2.println("Component, Received From Tejas, Consumption, Scrap, Balance, Returned Quantity, " + "Returned Accounted Quantity, Unaccounted Return Quantity, Is Balancing, Remaining Rows in Results, Balance In Annexure");

            for (String component : componentsMap.keySet()) {

                int remainingRows = 0;
                if (resultsMap.get(component) != null) {
                    remainingRows = resultsMap.get(component).size();
                }

                Component tempComponent = componentsMap.get(component);

                writer2.println(component + ", " + tempComponent.getShippedQuantity() + ", " + tempComponent
                        .getConsumedQuantity() + ", " + tempComponent.getScrapQuantity() + ", " + tempComponent
                        .getBalanceQuantity() + ", " + tempComponent.getReturnedQuantity() + ", " + tempComponent
                        .getReturnedAccountedQuantity() + ", " + (tempComponent
                        .getReturnedQuantity() - tempComponent.getReturnedAccountedQuantity()) + ", " + tempComponent
                        .isBalancing() + ", " + remainingRows + ", " + tempComponent
                        .getAnnexureBalances());
            }

            writer2.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (String annexNumber : allFinalRows.keySet()) {
            PrintWriter writer3;
            try {
                writer3 = new PrintWriter(
                		"resources" + System.getProperty("file.separator") + "annex" + System.getProperty("file.separator") + annexNumber + ".csv",
                        "UTF-8");

                writer3.println(AnnexureResults.getHeader());

                for (String[] rowData : allFinalRows.get(annexNumber)) {
                    writer3.println(toCSVString(rowData));
                }
                writer3.println("");
                writer3.println("");
                writer3.println(",,Total Quantity," + annexureTotals.get(annexNumber)[0]);
                writer3.println(",,Total Consumption," + annexureTotals.get(annexNumber)[1]);
                writer3.println(",,Total Scrap," + annexureTotals.get(annexNumber)[2]);
                writer3.println(",,Total Returned," + annexureTotals.get(annexNumber)[3]);
                writer3.println(",,Total Balance," + annexureTotals.get(annexNumber)[4]);

                writer3.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Gather monthly shipped quantity

        ArrayList<LinkedHashMap<String, Component>> monthlyList = new ArrayList<LinkedHashMap<String, Component>>();
        // LinkedHashMap<String, LinkedHashMap<String, ArrayList<AnnexureUpdationData>>>
        // monthlyUpdationDataMap = new LinkedHashMap<String, LinkedHashMap<String,
        // ArrayList<AnnexureUpdationData>>>();

        // Set<String> monthsList = monthlyUpdationDataMap.keySet();

        // for (String eachMonth : monthsList) {
        // LinkedHashMap<String, ArrayList<AnnexureUpdationData>>
        // monthlyUpdationDataMap.get(eachMonth);
        // LinkedHashMap<String, ArrayList<AnnexureUpdationData>> componentUpdationMap =
        // Integer monthlyTotalQuantity = 0;
        // Integer monthlyTotalConsumption = 0;
        // Integer monthlyTotalReturned = 0;
        // Integer monthlyTotalBalance = 0;
        // Integer monthlyTotalScrap = 0;
        //
        //
        // for()
        // }

        // for (int index = 0; index < 12; index++) {
        // LinkedHashMap<String, Component> monthlyComponentMap = new LinkedHashMap<String,
        // Component>();
        //
        // for (String component : componentBacklogList.keySet()) {
        //
        // Component monthlyComponentData = new Component();
        //
        // monthlyComponentMap.put(component, monthlyComponentData);
        // }
        // monthlyList.add(monthlyComponentMap);
        // }

        PrintWriter writer4;
        String[] months = {};
        for (int monthIndex = 0; monthIndex < monthlyList.size(); monthIndex++) {
            LinkedHashMap<String, Component> eachMonthComponentMap = monthlyList.get(monthIndex);
            try {
                writer4 = new PrintWriter(
                		"resources" + System.getProperty("file.separator") + "balance" + System.getProperty("file.separator") + months[monthIndex] + ".csv",
                        "UTF-8");

                writer4.println("TPN,Annexure Date,Opening Balance,Shipped Quantity,Total Quantity,Consumed Quantity,Scrap Quantity,Returned Quantity,Closing Balance");

                for (String component : eachMonthComponentMap.keySet()) {
                    String annexureDate = eachMonthComponentMap.get(component).getAnnexureNumber();
                    Integer openingBalance = eachMonthComponentMap.get(component)
                            .getOpeningBalance();
                    Integer shippedQuantity = eachMonthComponentMap.get(component)
                            .getShippedQuantity();
                    Integer totalInputQuantity = openingBalance + shippedQuantity;
                    Integer consumedQuantity = eachMonthComponentMap.get(component)
                            .getConsumedQuantity();
                    Integer scrapQuantity = eachMonthComponentMap.get(component).getScrapQuantity();
                    Integer returnedQuantity = eachMonthComponentMap.get(component)
                            .getReturnedQuantity();
                    Integer closingBalance = eachMonthComponentMap.get(component)
                            .getClosingBalance();
                    String[] outputString = {component,
                                             annexureDate,
                                             openingBalance.toString(),
                                             shippedQuantity.toString(),
                                             totalInputQuantity.toString(),
                                             consumedQuantity.toString(),
                                             scrapQuantity.toString(),
                                             returnedQuantity.toString(),
                                             closingBalance.toString()};
                    writer4.println(toCSVString(outputString));
                }

                writer4.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("Results Printed!");
    }

    public static String toCSVString(String[] inpStringArray) {

        String tempRowData = "";
        for (int i = 0; i < inpStringArray.length; i++) {
            if (i == 0) {
                tempRowData = inpStringArray[i];
            } else {
                tempRowData = tempRowData.concat("," + inpStringArray[i]);
            }
        }
        return tempRowData;
    }

}
