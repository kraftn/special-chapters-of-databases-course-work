package ru.databases.client.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import ru.databases.client.options.PerforationType;
import ru.databases.client.options.PrintingType;

public class Stamp extends Item {
    private String series;
    private Long edition;
    private Denomination denomination;
    private Person artist;
    @BsonProperty(value = "printing_type")
    private PrintingType printingType;
    @BsonProperty(value = "perforation_type")
    private PerforationType perforationType;
    private Gauges gauges;

    public Stamp() {
        denomination = new Denomination();
        artist = new Person();
        gauges = new Gauges();
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Long getEdition() {
        return edition;
    }

    public void setEdition(Long edition) {
        this.edition = edition;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public Person getArtist() {
        return artist;
    }

    public void setArtist(Person artist) {
        this.artist = artist;
    }

    public PrintingType getPrintingType() {
        return printingType;
    }

    public void setPrintingType(PrintingType printingType) {
        this.printingType = printingType;
    }

    public PerforationType getPerforationType() {
        return perforationType;
    }

    public void setPerforationType(PerforationType perforationType) {
        this.perforationType = perforationType;
    }

    public Gauges getGauges() {
        return gauges;
    }

    public void setGauges(Gauges gauges) {
        this.gauges = gauges;
    }
}
