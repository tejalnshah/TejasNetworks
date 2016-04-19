/**
 * @author USHAHTE
 * @date Jul 26, 2015
 */

public class Component {

    private String componentName = "";
    private int shippedQuantity = 0;
    private int consumedQuantity = 0;
    private int scrapQuantity = 0;
    private int balanceQuantity = 0;
    private int returnedQuantity = 0;
    private int returnedAccountedQuantity = 0;
    private boolean isBalancing = false;
    private String annexureNumber = "";
    private int openingBalance = 0;
    private int closingBalance = 0;
    private String annexureBalances = "";

    public Component(
            String componentName,
            int shippedQuantity,
            int consumedQuantity,
            int scrapQuantity,
            int balanceQuantity,
            boolean balanceStatus,
            int returnedQuantity,
            Integer returnedAccountedQuantity,
            String annexureBalances) {
        this.componentName = componentName;
        this.shippedQuantity = shippedQuantity;
        this.consumedQuantity = consumedQuantity;
        this.scrapQuantity = scrapQuantity;
        this.balanceQuantity = balanceQuantity;
        this.isBalancing = balanceStatus;
        this.returnedQuantity = returnedQuantity;
        this.returnedAccountedQuantity = returnedAccountedQuantity;
        this.annexureBalances = annexureBalances;

    }

    public Component(
            String componentName,
            int shippedQuantity,
            int consumedQuantity,
            int scrapQuantity,
            int balanceQuantity,
            int returnedQuantity,
            String annexureNumber,
            int openingBalance,
            int closingBalance) {
        this.componentName = componentName;
        this.shippedQuantity = shippedQuantity;
        this.consumedQuantity = consumedQuantity;
        this.scrapQuantity = scrapQuantity;
        this.balanceQuantity = balanceQuantity;
        this.returnedQuantity = returnedQuantity;
        this.annexureNumber = annexureNumber;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
    }

    /**
     * @return the shippedQuantity
     */
    public int getShippedQuantity() {
        return shippedQuantity;
    }

    /**
     * @param shippedQuantity
     *            the shippedQuantity to set
     */
    public void setShippedQuantity(int shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    /**
     * @return the consumedQuantity
     */
    public int getConsumedQuantity() {
        return consumedQuantity;
    }

    /**
     * @param consumedQuantity
     *            the consumedQuantity to set
     */
    public void setConsumedQuantity(int consumedQuantity) {
        this.consumedQuantity = consumedQuantity;
    }

    /**
     * @return the scrapQuantity
     */
    public int getScrapQuantity() {
        return scrapQuantity;
    }

    /**
     * @param scrapQuantity
     *            the scrapQuantity to set
     */
    public void setScrapQuantity(int scrapQuantity) {
        this.scrapQuantity = scrapQuantity;
    }

    /**
     * @return the balanceQuantity
     */
    public int getBalanceQuantity() {
        return balanceQuantity;
    }

    /**
     * @param balanceQuantity
     *            the balanceQuantity to set
     */
    public void setBalanceQuantity(int balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    /**
     * @return the returnedQuantity
     */
    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    /**
     * @param returnedQuantity
     *            the returnedQuantity to set
     */
    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    /**
     * @return the componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * @param componentName
     *            the componentName to set
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * @return the isBalancing
     */
    public boolean isBalancing() {
        return isBalancing;
    }

    /**
     * @param isBalancing
     *            the isBalancing to set
     */
    public void setBalancing(boolean isBalancing) {
        this.isBalancing = isBalancing;
    }

    /**
     * @return
     */
    public Integer getReturnedAccountedQuantity() {
        return returnedAccountedQuantity;
    }

    /**
     * @param returnedAccountedQuantity
     */
    public void setReturnedAccountedQuantity(int returnedAccountedQuantity) {
        this.returnedAccountedQuantity = returnedAccountedQuantity;
    }

    /**
     * @return the annexureNumber
     */
    public String getAnnexureNumber() {
        return annexureNumber;
    }

    /**
     * @param annexureNumber
     *            the annexureNumber to set
     */
    public void setAnnexureNumber(String annexureNumber) {
        this.annexureNumber = annexureNumber;
    }

    /**
     * @return the openingBalance
     */
    public int getOpeningBalance() {
        return openingBalance;
    }

    /**
     * @param openingBalance
     *            the openingBalance to set
     */
    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    /**
     * @return the closingBalance
     */
    public int getClosingBalance() {
        return closingBalance;
    }

    /**
     * @param closingBalance
     *            the closingBalance to set
     */
    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    /**
     * @param annexBalances
     */
    public void setAnnexureBalances(String annexBalances) {
        this.annexureBalances = annexBalances;
    }

    /**
     * @return
     */
    public String getAnnexureBalances() {
        return this.annexureBalances;
    }

}
