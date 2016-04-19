/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author USHAHTE
 * @date Jul 21, 2015
 */

public class AnnexureMainRow {

    private ArrayList<String[]> rows = new ArrayList<String[]>();

    public AnnexureMainRow(
            String postingDate,
            String annexureCode,
            String annexureDate,
            String annexureNumber,
            Integer serialNumber,
            String component,
            String componentQuantityString,
            Integer prevBalance,
            String balanceFromAnnex,
            List<AnnexureResults> annexureResultsList,
            Integer totalConsumption,
            Integer balance,
            String balanceToAnnex,
            Integer returned,
            Integer scrap) {

        int count = 0;
        if (annexureResultsList == null || annexureResultsList.size() == 0) {
            String[] data = new String[]{annexureCode,
                                         postingDate,
                                         annexureNumber,
                                         annexureDate,
                                         serialNumber.toString(),
                                         component,
                                         componentQuantityString,
                                         prevBalance.toString(),
                                         balanceFromAnnex,
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         "",
                                         totalConsumption.toString(),
                                         balance.toString(),
                                         balanceToAnnex,
                                         returned.toString(),
                                         scrap.toString()};

            rows.add(data);
        } else {
            for (AnnexureResults eachResult : annexureResultsList) {
                if (count == 0) {
                    String[] data = new String[]{annexureCode,
                                                 postingDate,
                                                 annexureNumber,
                                                 annexureDate,
                                                 serialNumber.toString(),
                                                 component,
                                                 componentQuantityString,
                                                 prevBalance.toString(),
                                                 balanceFromAnnex,
                                                 eachResult.getJobWorkerNumber(),
                                                 eachResult.getBoardShipmentDate(),
                                                 eachResult.getBoardName(),
                                                 eachResult.getBoardQuantity(),
                                                 "ASSEMBLY",
                                                 eachResult.getPerBoard(),
                                                 eachResult.getConsumptionPerBoard(),
                                                 totalConsumption.toString(),
                                                 balance.toString(),
                                                 balanceToAnnex,
                                                 returned.toString(),
                                                 scrap.toString()};

                    rows.add(data);
                } else {
                    String[] data = new String[]{"",
                                                 "",
                                                 "",
                                                 "",
                                                 "",
                                                 "",
                                                 "",
                                                 "",
                                                 "",
                                                 eachResult.getJobWorkerNumber(),
                                                 eachResult.getBoardShipmentDate(),
                                                 eachResult.getBoardName(),
                                                 eachResult.getBoardQuantity(),
                                                 "ASSEMBLY",
                                                 eachResult.getPerBoard(),
                                                 eachResult.getConsumptionPerBoard(),
                                                 "",
                                                 "",
                                                 "",
                                                 "",
                                                 ""};

                    rows.add(data);
                }
                count++;
            }
        }
    }

    /**
     * @return
     */
    public ArrayList<String[]> getRowsFromAnnexures() {
        // TODO Auto-generated method stub
        return rows;
    }
}
