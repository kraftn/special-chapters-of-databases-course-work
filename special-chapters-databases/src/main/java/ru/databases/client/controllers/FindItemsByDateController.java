package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FindItemsByDateController {
    @FXML
    private DatePicker dpBegin;
    @FXML
    private DatePicker dpEnd;

    public FindItemsByDateController(Dialog<FindItemsByDateController.ResultDialog> dialog) {
        dialog.setTitle("Найти предметы по дате поступления");
        dialog.setHeaderText("Укажите необходимые параметры поиска:");
        ButtonType bOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        ButtonType bCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(bOk, bCancel);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == bOk) {
                LocalDate dateBegin = dpBegin.getValue();
                LocalDate dateEnd = dpEnd.getValue();
                return new ResultDialog(dateBegin, dateEnd);
            } else {
                return null;
            }
        });

    }

    public static class ResultDialog {
        private LocalDate beginDate;
        private LocalDate endDate;

        public ResultDialog(LocalDate beginDate, LocalDate endDate) {
            this.beginDate = beginDate;
            this.endDate = endDate;
        }

        public LocalDate getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(LocalDate beginDate) {
            this.beginDate = beginDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
    }
}
