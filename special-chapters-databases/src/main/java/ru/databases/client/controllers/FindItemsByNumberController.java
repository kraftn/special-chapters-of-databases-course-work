package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.databases.client.changelisteners.DoubleValueChangeListener;
import ru.databases.client.changelisteners.IntegerValueChangeListener;
import ru.databases.client.utils.Formatter;

public class FindItemsByNumberController {
    @FXML
    private TextField tfBegin, tfEnd;
    private boolean isLong;

    public FindItemsByNumberController(Dialog dialog, String title, boolean isLong) {
        this.isLong = isLong;

        dialog.setTitle(title);
        dialog.setHeaderText("Укажите необходимые параметры поиска:");
        ButtonType bOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        ButtonType bCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(bOk, bCancel);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == bOk) {
                if (isLong) {
                    Long begin = Formatter.convertToLong(tfBegin.getText());
                    Long end = Formatter.convertToLong(tfEnd.getText());
                    return new IntegersResultDialog(begin, end);
                } else {
                    Double begin = Formatter.convertToDouble(tfBegin.getText());
                    Double end = Formatter.convertToDouble(tfEnd.getText());
                    return new DoublesResultDialog(begin, end);
                }
            } else {
                return null;
            }
        });

    }

    public void setListeners() {
        if (isLong) {
            tfBegin.textProperty().addListener(new IntegerValueChangeListener(tfBegin));
            tfEnd.textProperty().addListener(new IntegerValueChangeListener(tfEnd));
        } else {
            tfBegin.textProperty().addListener(new DoubleValueChangeListener(tfBegin));
            tfEnd.textProperty().addListener(new DoubleValueChangeListener(tfEnd));
        }
    }

    public static class IntegersResultDialog {
        private Long begin, end;

        public IntegersResultDialog(Long begin, Long end) {
            this.begin = begin;
            this.end = end;
        }

        public Long getBegin() {
            return begin;
        }

        public void setBegin(Long begin) {
            this.begin = begin;
        }

        public Long getEnd() {
            return end;
        }

        public void setEnd(Long end) {
            this.end = end;
        }
    }

    public static class DoublesResultDialog {
        private Double begin, end;

        public DoublesResultDialog(Double begin, Double end) {
            this.begin = begin;
            this.end = end;
        }

        public Double getBegin() {
            return begin;
        }

        public void setBegin(Double begin) {
            this.begin = begin;
        }

        public Double getEnd() {
            return end;
        }

        public void setEnd(Double end) {
            this.end = end;
        }
    }
}
