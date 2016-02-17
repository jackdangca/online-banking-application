package com.onlinebank.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Entity(name = "TicketDeposit")
@PrimaryKeyJoinColumn(name = "ticketdepositId", referencedColumnName = "transactionId")
public class TransactionTicketDeposit extends Transaction {

    @Column(insertable = false, updatable = false)
    private Long ticketdepositId;

    @NotNull
    private Long srcTicketId;

    @NotNull
    private Long dstAccountId;

    public Long getDstAccountId() {
        return dstAccountId;
    }

    public Long getSrcTicketId() {
        return srcTicketId;
    }

    public Long getTicketdepositId() {
        return ticketdepositId;
    }

    public TransactionTicketDeposit setDstAccountId(Long dstAccountId) {
        this.dstAccountId = dstAccountId;
        return this;
    }

    public TransactionTicketDeposit setSrcTicketId(Long srcTicketId) {
        this.srcTicketId = srcTicketId;
        return this;
    }

    public TransactionTicketDeposit setTicketdepositId(Long ticketdepositId) {
        this.ticketdepositId = ticketdepositId;
        return this;
    }

}
