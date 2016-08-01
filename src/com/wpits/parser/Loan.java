
package com.wpits.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Loan  implements Serializable {

    private String isloanrequsested;
    private String loanAccountnumber;
    private String loanValue;
    private List<LoanGauranters> loangauranterlist = new ArrayList<LoanGauranters>();

    /**
     * 
     * @return
     *     The isloanrequsested
     */
    public String getIsloanrequsested() {
        return isloanrequsested;
    }

    /**
     * 
     * @param isloanrequsested
     *     The isloanrequsested
     */
    public void setIsloanrequsested(String isloanrequsested) {
        this.isloanrequsested = isloanrequsested;
    }

    /**
     * 
     * @return
     *     The loanAccountnumber
     */
    public String getLoanAccountnumber() {
        return loanAccountnumber;
    }

    /**
     * 
     * @param loanAccountnumber
     *     The loan_accountnumber
     */
    public void setLoanAccountnumber(String loanAccountnumber) {
        this.loanAccountnumber = loanAccountnumber;
    }

    /**
     * 
     * @return
     *     The loanValue
     */
    public String getLoanValue() {
        return loanValue;
    }

    /**
     * 
     * @param loanValue
     *     The loan_value
     */
    public void setLoanValue(String loanValue) {
        this.loanValue = loanValue;
    }

    /**
     * 
     * @return
     *     The loangauranterlist
     */
    public List<LoanGauranters> getLoangauranterlist() {
        return loangauranterlist;
    }

    /**
     * 
     * @param loangauranterlist
     *     The loangauranterlist
     */
    public void setLoangauranterlist(List<LoanGauranters> loangauranterlist) {
        this.loangauranterlist = loangauranterlist;
    }

}
