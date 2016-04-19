/**
 * 
 */

/**
 * @author USHAHTE
 * @date Jul 25, 2015
 */

public class AnnexureUpdationData {

    private String postingDate;
    private String annexureDate;
    private String component;
    private String quantity;
    private String matDoc;
    private String annexNumber;

    public AnnexureUpdationData(
            String postingDate,
            String annexureDate,
            String component,
            String quantity,
            String matDoc,
            String annexNumber) {
        this.setPostingDate(postingDate);
        this.setAnnexureDate(annexureDate);
        this.component = component;
        this.quantity = quantity;
        this.matDoc = matDoc;
        this.setAnnexNumber(annexNumber);
    }

    /**
     * @param annexureDate
     */
    public void setAnnexureDate(String annexureDate) {
        this.annexureDate = annexureDate;
    }

    public String getAnnexureDate() {
        return annexureDate;
    }

    /**
     * @param postingDate
     */
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPostingDate() {
        return postingDate;
    }

    /**
     * @return
     */
    public String getMaterial() {
        return component;
    }

    /**
     * @return
     */
    public Integer getQuantity() {
        return Integer.parseInt(quantity);
    }

    /**
     * @return
     */
    public String getAnnexureCode() {
        return matDoc;
    }

    /**
     * @return the annexNumber
     */
    public String getAnnexNumber() {
        return annexNumber;
    }

    /**
     * @param annexNumber
     *            the annexNumber to set
     */
    public void setAnnexNumber(String annexNumber) {
        this.annexNumber = annexNumber;
    }
}
