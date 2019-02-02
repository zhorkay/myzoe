package io.myzoe.site.entities;

import io.myzoe.site.common.converter.InstantConverter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "event")
public class Event extends BaseAuditEntity
{
    private static final long serialVersionUID = 1L;

    private long eventId;
    private String eventTypeCd;
    private String eventName;
    private String eventDesc;
    private Instant eventStartDate;
    private Instant eventEndDate;

    @Id
    @Column(name = "EVENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getEventId() {
        return this.eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Column(name = "EVENT_TYPE_CD")
    public String getEventTypeCd() {
        return this.eventTypeCd;
    }

    public void setEventTypeCd(String eventTypeCd) {
        this.eventTypeCd = eventTypeCd;
    }

    @Column(name = "EVENT_NAME")
    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Column(name = "EVENT_DESC")
    public String getEventDesc() {
        return this.eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    @Column(name = "EVENT_START_DT")
    @Convert(converter = InstantConverter.class)
    public Instant getEventStartDate() {
        return this.eventStartDate;
    }

    public void setEventStartDate(Instant eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    @Column(name = "EVENT_END_DT")
    @Convert(converter = InstantConverter.class)
    public Instant getEventEndDate() {
        return this.eventEndDate;
    }

    public void setEventEndDate(Instant eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
}
