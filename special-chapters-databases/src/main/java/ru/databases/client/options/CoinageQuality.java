package ru.databases.client.options;

public enum CoinageQuality {
    UNCIRCULATED("Анциркулейтед"), BRILLIANT_UNCIRCULATED("Бриллиант-анциркулейтед"),
    PROOF("Пруф"), PROOF_LIKE("Пруф-лайк"), REVERSE_FROSTED("Реверс-фростед");

    private String translation;

    CoinageQuality(String translation){
        this.translation = translation;
    }

    @Override
    public String toString() {
        return translation;
    }
}
