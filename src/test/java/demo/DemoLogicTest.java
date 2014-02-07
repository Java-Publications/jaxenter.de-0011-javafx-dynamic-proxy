/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import junit.org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.Context;
import junit.org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.DynamicDecoratorTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ts40 on 09.01.14.
 */
@RunWith(Arquillian.class)
public class DemoLogicTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.demo")
                .addPackages(true, "junit.org.rapidpm.demo")
                .addPackages(true, "demo")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject @DynamicDecoratorTest
    Instance<DemoController> demoControllerInstance;
    @Inject
    Context context;

    static final FXMLLoader loader = new FXMLLoader();

    @Test
    public void testDemoLogicJavaFXTest() throws Exception {
        loader.setControllerFactory(param -> demoControllerInstance.get());
        Application.launch(DemoApp.class);
    }


    public static class DemoApp extends Application {
        @Override
        public void start(Stage stage) throws Exception {

            final URL resource = getClass()
                    .getClassLoader().getResource("DemoPane.fxml");
            loader.setLocation(resource);
            final DemoController controller = (DemoController) loader
                    .getControllerFactory()
                    .call(DemoController.class);
            try {

                loader.setController(controller);
                loader.setRoot(new AnchorPane());
                final Parent root = (Parent) loader.load();

                stage.setScene(new Scene(root));
                stage.setTitle("Custom Control");
                stage.setWidth(300);
                stage.setHeight(200);
                stage.show();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
