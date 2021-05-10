package ru.databases.client.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import ru.databases.client.options.CoinageQuality;

public class Coin extends Item {
    private String series;
    private Long edition;
    private Denomination denomination;
    @BsonProperty(value = "coinage_quality")
    private CoinageQuality coinageQuality;
    @BsonProperty(value = "physical_characteristics")
    private PhysicalCharacteristics physicalCharacteristics;
    private String material;

    public Coin() {
        denomination = new Denomination();
        physicalCharacteristics = new PhysicalCharacteristics();
    }

    public static class PhysicalCharacteristics {
        private Double diameter, thickness, weight;

        public Double getDiameter() {
            return diameter;
        }

        public void setDiameter(Double diameter) {
            this.diameter = diameter;
        }

        public Double getThickness() {
            return thickness;
        }

        public void setThickness(Double thickness) {
            this.thickness = thickness;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }
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

    public CoinageQuality getCoinageQuality() {
        return coinageQuality;
    }

    public void setCoinageQuality(CoinageQuality coinageQuality) {
        this.coinageQuality = coinageQuality;
    }

    public PhysicalCharacteristics getPhysicalCharacteristics() {
        return physicalCharacteristics;
    }

    public void setPhysicalCharacteristics(PhysicalCharacteristics physicalCharacteristics) {
        this.physicalCharacteristics = physicalCharacteristics;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
