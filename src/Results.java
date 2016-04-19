import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 */

/**
 * @author USHAHTE
 * @date Jul 17, 2015
 */

public class Results implements Comparable<Results> {

    private String component;
    private String board;
    private String invNumber;
    private Date invDate;
    private String boardQuantity;
    private String compQuantity;
    private String totalCompQuantity;

    public Results(
            String component,
            String board,
            String invNumber,
            String invDate,
            String boardQuantity,
            String compQuantity,
            String totalCompQuantity) {

        this.component = component;
        this.board = board;
        this.invNumber = invNumber;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        try {

            this.invDate = formatter.parse(invDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.boardQuantity = boardQuantity;
        this.compQuantity = compQuantity;
        this.totalCompQuantity = totalCompQuantity;
    }

    public static String getHeader() {
        return "Component, Board, Invoice Number, Invoice Date, Board Quantity, Component Consumption, " + "Total Component Quantity";
    }

    public static String getRow(Results resultRow) {

        DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yy");
        String outputDate = outputFormatter.format(resultRow.invDate);

        return resultRow.component + "," + resultRow.board + "," + resultRow.invNumber + "," + outputDate + "," + resultRow.boardQuantity + "," + resultRow.compQuantity + "," + resultRow.totalCompQuantity;
    }

    public String getBoard() {
        return board;
    }

    public String getComponent() {
        return component;
    }

    public boolean checkIfSame(List<Results> results) {
        for (Results tempResultsObj : results) {
            if (component.equals(tempResultsObj.component) && board.equals(tempResultsObj.board) && invNumber
                    .equals(tempResultsObj.invNumber) && invDate.equals(tempResultsObj.invDate) && boardQuantity
                    .equals(tempResultsObj.boardQuantity) && compQuantity
                    .equals(tempResultsObj.compQuantity)) {

                return true;
            }
        }
        return false;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    @Override
    public int compareTo(Results results) {
        return getInvDate().compareTo(results.getInvDate());
    }

    /**
     * @return
     */
    public Integer getTotalComponentQuantity() {
        return Integer.parseInt(totalCompQuantity);
    }

    /**
     * @return
     */
    public String getJobWokerNumber() {
        return invNumber;
    }

    /**
     * @return
     */
    public String getBoardShipmentDate() {

        DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yy");
        String outputDate = outputFormatter.format(invDate);

        return outputDate;
    }

    /**
     * @return
     */
    public String getQuantity() {
        // TODO Auto-generated method stub
        return boardQuantity;
    }

    /**
     * @return
     */
    public String getPerBoard() {
        // TODO Auto-generated method stub
        return compQuantity;
    }

    public String getTotalComponentQuantityString() {
        return totalCompQuantity;
    }

}
