package ru.databases.client.options;

public enum PrintingType {
    LETTERPRESS("Типографская печать"), AUTOTYPY("Автотипия"), LITHOGRAPHY("Литография"),
    OFFSET("Офсет"), PHOTOTYPE("Фототипия"), METALLOGRAPHIC("Металлография");

    private String translation;

    PrintingType(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return translation;
    }
}
