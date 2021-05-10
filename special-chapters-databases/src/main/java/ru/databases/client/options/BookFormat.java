package ru.databases.client.options;

public enum BookFormat {
    MINIATURE("Миниатюрный"), SMALL("Уменьшенный"), STANDARD("Стандартный"),
    LARGE("Увеличенный"), ENCYCLOPEDIC("Энциклопедический"), HUGE("Очень большой");

    private String translation;

    BookFormat(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return translation;
    }
}
