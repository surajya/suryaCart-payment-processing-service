package com.hulkhiretech.payment.pojo.activemq;

import java.io.Serializable;

public class StatusMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String txnReference;
    private String txnStatus;

    // Getters and Setters
    public String getTxnReference() {
        return txnReference;
    }

    public void setTxnReference(String txnReference) {
        this.txnReference = txnReference;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    @Override
    public String toString() {
        return "StatusMessage{" +
                "txnReference='" + txnReference + '\'' +
                ", txnStatus='" + txnStatus + '\'' +
                '}';
    }
}

