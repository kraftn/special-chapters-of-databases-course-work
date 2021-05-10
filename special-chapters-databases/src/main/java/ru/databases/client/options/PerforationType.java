package ru.databases.client.options;

public enum PerforationType {
    COMB("Гребенчатая"), STROKE("Линейная"), FRAME("Рамочная"),
    DECORATIVE("Декоративная");

    private String translation;

    PerforationType(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return translation;
    }
}
