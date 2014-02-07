package demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import junit.org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.Context;
import junit.org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.DemoLogic;
import junit.org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.DynamicDecoratorTest;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sven Ruppert on 06.02.14.
 */
@DynamicDecoratorTest
public class DemoController implements Initializable{

    @FXML public TextField textFieldA;
    @FXML public TextField textFieldB;
    @FXML public Button button;
    @FXML public Label label;
    @FXML public CheckBox checkbox;

    @Inject
    @DynamicDecoratorTest
    Instance<DemoLogic> demoLogicInstance;

    @Inject
    Context context;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.setText("klick me");
        button.setOnAction(actionEvent -> {

            final DemoLogic demoLogic = demoLogicInstance.get();

            final String textFieldAText = textFieldA.getText();
            final Integer a = Integer.valueOf(textFieldAText);

            final String textFieldBText = textFieldB.getText();
            final Integer b = Integer.valueOf(textFieldBText);

            final int result = demoLogic.add(a, b);
            label.setText(result+"");

        });

        checkbox.setOnAction(actionEvent -> {
            context.original = checkbox.isSelected();
        });
    }
}
