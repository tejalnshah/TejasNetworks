/**
 * 
 */

/**
 * @author USHAHTE
 * @date Jul 29, 2015
 */

public class ReturnedComponents {

    private String componentName;
    private Integer returnedQuantity;
    private String returnedDocNumber;
    private String returnedDate;

    public ReturnedComponents(
            String componentName,
            Integer returnedQuantity,
            String returnedDocNumber,
            String returnedDate) {

        this.setComponentName(componentName);
        this.setReturnedQuantity(returnedQuantity);
        this.setReturnedDocNumber(returnedDocNumber);
        this.setReturnedDate(returnedDate);
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
     * @return the returnedQuantity
     */
    public Integer getReturnedQuantity() {
        return returnedQuantity;
    }

    /**
     * @param returnedQuantity
     *            the returnedQuantity to set
     */
    public void setReturnedQuantity(Integer returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    /**
     * @return the returnedDocNumber
     */
    public String getReturnedDocNumber() {
        return returnedDocNumber;
    }

    /**
     * @param returnedDocNumber
     *            the returnedDocNumber to set
     */
    public void setReturnedDocNumber(String returnedDocNumber) {
        this.returnedDocNumber = returnedDocNumber;
    }

    /**
     * @return the returnedDate
     */
    public String getReturnedDate() {
        return returnedDate;
    }

    /**
     * @param returnedDate
     *            the returnedDate to set
     */
    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

}
