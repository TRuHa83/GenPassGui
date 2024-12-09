package es.truhatechdev.genpassgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MainController {
    // Variables
    private static String password = "";
    private static int passlength;
    private static final boolean[] config = new boolean[4];
    private static final ArrayList<String> types = new ArrayList<>();
    private static final String[] charac = {
            "abcdefghijklmnopqrstuvwxyz",
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "0123456789",
            "!@#$%&*",
    };

    @FXML
    private TextField fieldPassword;

    @FXML
    private Label longNum;

    @FXML
    private Slider sliderLong;

    @FXML
    private CheckBox charLower;

    @FXML
    private CheckBox charUpper;

    @FXML
    private CheckBox charNum;

    @FXML
    private CheckBox charChar;

    private void configDefault() {
        System.out.println("Configuración por defecto");
        // Configuración por defecto
        config[0] = true; // Minúsculas
        config[1] = true; // Mayúsculas
        config[2] = true; // Números
        config[3] = true; // Caracteres
        passlength = 16;   // Longitud de contraseña
    }

    private void loadConfig() {
        System.out.println("Cargando configuración");
        // Carga configuración
        charLower.setSelected(config[0]);
        charUpper.setSelected(config[1]);
        charNum.setSelected(config[2]);
        charChar.setSelected(config[3]);

        sliderLong.setValue(passlength);
        longNum.setText(String.valueOf(passlength));
    }

    @FXML
    private void initialize() {
        System.out.println("Generador contraseñas iniciado");
        configDefault();
        loadConfig();
        genPass(null);
    }

    @FXML
    void saveConfig(ActionEvent event) {
        System.out.println("Configuración guardada");
        config[0] = charLower.isSelected();
        config[1] = charUpper.isSelected();
        config[2] = charNum.isSelected();
        config[3] = charChar.isSelected();

        passlength = (int) sliderLong.getValue();
    }

    @FXML
    void changeNum(MouseEvent event) {
        System.out.println("Valor cambiado");
        longNum.setText(String.valueOf((int) sliderLong.getValue()));
        saveConfig(null);

        genPass(null);
    }

    private static boolean isValid(String password) {
        for (String type : types) {
            int count = 0;
            for (int i = 0; i < type.length(); i++) {
                count += password.indexOf(type.charAt(i));
            }

            count += type.length();
            if (count == 0) return false;
        }

        return true;
    }

    @FXML
    void genPass(ActionEvent event) {
        for (int i = 0; i < 4; i++) {
            if (config[i]) {
                types.add(charac[i]);
            }
        }

        do {
            password = "";
            for (int i = 0; i < passlength; i++) {
                int option = (int) (Math.random() * types.size());
                password += types.get(option).charAt((int) (Math.random() * types.get(option).length()));
            }
        } while (!isValid(password));

        fieldPassword.setText(password);
        System.out.println("Generando password: " + password);

        types.clear();
    }

    @FXML
    void passCopy(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(password);
        clipboard.setContent(content);

        System.out.println("Password copiada al portapapeles");
    }
}
