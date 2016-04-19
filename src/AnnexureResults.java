/**
 * 
 */

/**
 * @author USHAHTE
 * @date Jul 21, 2015
 */

public class AnnexureResults {

    private String jobWorkerNumber;
    private String boardShipmentDate;
    private String boardName;
    private String boardQuantity;
    private String perBoard;
    private String consumptionPerBoard;

    public AnnexureResults(
            String jobWorkerNumber,
            String boardShipmentDate,
            String boardName,
            String boardQuantity,
            String perBoard,
            String consumptionPerBoard) {
        this.jobWorkerNumber = jobWorkerNumber;
        this.boardShipmentDate = boardShipmentDate;
        this.boardName = boardName;
        this.boardQuantity = boardQuantity;
        this.perBoard = perBoard;
        this.consumptionPerBoard = consumptionPerBoard;
    }

    public String getJobWorkerNumber() {
        return jobWorkerNumber;
    }

    public String getBoardShipmentDate() {
        return boardShipmentDate;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardQuantity() {
        return boardQuantity;
    }

    public String getPerBoard() {
        return perBoard;
    }

    public String getConsumptionPerBoard() {
        return consumptionPerBoard;
    }

    /**
     * @return
     */
    public static String getHeader() {
        // TODO Auto-generated method stub
        return "Tejas Document Number,Document Date,Annexure Number,Annexure Date,Serial Number,Item Description,Quantity,Carried Forward Balance,Balance From Previous Annexure," + "Job Worker DC Number,Date,Item Description,Board Quantity,Nature of Process Done,Per Board,Consumption," + "Total Consumption,Balance,Balance C/F to Annexure,Returned,Scrap";
    }
}
