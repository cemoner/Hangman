package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ImageView backGround;
    List<String> words = Arrays.asList("Apple juice", "Orange juice", "Carrot juice", "babababaa");
    List<Character> lettersOfAlphabet = new ArrayList<>();
    List<String> wordTextScreen = new ArrayList<>();
    List<String> wordTextActual = new ArrayList<>();
    String actualWord = "";
    String wordScreen = "";
    int wrongCount = 0;
    int index = (int) (Math.random() * 4);

    @FXML
    private Text wordText;

    @FXML
    private TextField textField;

    @FXML
    private Button tryButton;

    @FXML
    private Circle head;

    @FXML
    private Line body;

    @FXML
    private Line leftArm;

    @FXML
    private Line rightArm;

    @FXML
    private Line leftLeg;

    @FXML
    private Line rightLeg;

    List<Shape> bodyParts = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();

    }

    public boolean validation(TextField text) {
        boolean isValid = false;
        try{

            char charText = text.getText().charAt(0);
            for (char letter : lettersOfAlphabet) {
                if (charText == letter) {
                    isValid = true;
                    break;
                }
            }
            if (isValid) {
                int index = 0;
                for (char character : lettersOfAlphabet) {
                    if (character == charText) {
                        index = lettersOfAlphabet.indexOf(character);
                    }
                }
                lettersOfAlphabet.remove(index);
            }


        }catch (StringIndexOutOfBoundsException e){

        }return isValid;
    }

    public void checkLetter() {
        boolean isThere = false;
        for (int i = 0; i <= wordTextActual.size() - 1; i++) {
            if (actualWord.substring(i,i+1).compareToIgnoreCase(textField.getText()) == 0) {
                isThere = true;
                theWordUpdate(actualWord.charAt(i) , i);
            }
        }
        if (!isThere) {
            bodyParts.get(wrongCount).setVisible(true);
            wrongCount += 1;
        }
        textField.clear();
        gameCheck();
    }

    public void theWordUpdate(char letter, int index) {
        wordTextScreen.set(index, Character.toString(letter));
        wordScreen = "";
        for (String element : wordTextScreen) {
            wordScreen += element;
        }
        wordText.setText(wordScreen);

    }

    public void setUp() {
        // screen text
        String placeHolder = words.get(index);
        for (int i = 0; i <= placeHolder.length() - 1; i++) {
            if (placeHolder.charAt(i) != ' ') {
                wordTextScreen.add("_");
                wordTextScreen.add(" ");
            } else {
                wordTextScreen.add(" ");
                wordTextScreen.add(" ");
            }
        }

        for (String element : wordTextScreen) {
            wordScreen += element;
        }
        wordText.setText(wordScreen);

        // actual text


        for (int i = 0; i <= placeHolder.length() - 1; i++) {
            if (placeHolder.charAt(i) != ' ') {
                wordTextActual.add(Character.toString(placeHolder.charAt(i)));
                wordTextActual.add(" ");
            } else {
                wordTextActual.add(" ");
                wordTextActual.add(" ");
            }
        }
        for (String element : wordTextActual) {
            actualWord += element;
        }
        System.out.println(actualWord);

        for (char c = 'a'; c <= 'z'; c++) {
            lettersOfAlphabet.add(c);
        }


        textField.textProperty().addListener((v, oldValue, newValue) -> {
            if (textField.getText().length() > 1) {
                textField.setText(textField.getText().substring(0, 1));
            }
        });

        tryButton.setOnAction(e -> {
            if (validation(textField)) {
                checkLetter();
            } else {
                Button button = new Button("Close");
                button.setFont(Font.font(15));
                button.setTranslateX(120);
                button.setTranslateY(50);

                Text text = new Text("Invalid or Used letter.");
                text.setFont(Font.font(20));
                text.setTranslateX(55);
                text.setTranslateY(40);

                Pane pane = new Pane();
                pane.getChildren().addAll(text,button);

                Scene scene = new Scene(pane,300,100);
                Stage stage = new Stage();

                button.setOnAction(m -> stage.close());

                stage.setScene(scene);
                stage.show();
                textField.clear();
            }
        });
        bodyParts.add(head);
        bodyParts.add(body);
        bodyParts.add(leftArm);
        bodyParts.add(rightArm);
        bodyParts.add(leftLeg);
        bodyParts.add(rightLeg);
    }

    public void gameCheck() {
        boolean isWon = true;
        for (int i = 0; i <= wordScreen.length() - 1; i++) {
            if (wordScreen.charAt(i) == '_') {
                isWon = false;
                break;
            }
        }
        if (isWon) {
            textField.setEditable(false);
            Button exit = new Button("Exit");
            Text text = new Text("You WON!");
            Text text1 = new Text("Answer is: "+ words.get(index));

            text.setTranslateY(30);
            text.setTranslateX(85);

            text.setFont(Font.font(25));

            text1.setTranslateY(50);
            text1.setTranslateX(50);

            text1.setFont(Font.font(20));



            exit.setTranslateY(60);
            exit.setTranslateX(120);

            exit.setFont(Font.font(15));

            Pane pane = new Pane();

            pane.getChildren().addAll(text,text1, exit);
            Scene scene = new Scene(pane, 300, 100);
            Stage stage = new Stage();

            exit.setOnAction(e -> {
                stage.close();
                Main.window.close();
            });

            stage.setScene(scene);
            stage.show();
        }

        if (wrongCount == 6) {
            textField.setEditable(false);
            Button exit = new Button("Exit");
            Text text = new Text("You Lost!");

            Text text2 = new Text("The answer was: " + words.get(index));

            text.setTranslateY(20);
            text.setTranslateX(95);

            text.setFont(Font.font(20));

            text2.setTranslateY(40);
            text2.setTranslateX(65);

            text2.setFont(Font.font(15));

            exit.setTranslateY(50);
            exit.setTranslateX(120);

            exit.setFont(Font.font(15));

            Pane pane = new Pane();

            pane.getChildren().addAll(text,text2, exit);
            Scene scene = new Scene(pane, 300, 100);
            Stage stage = new Stage();

            exit.setOnAction(e -> {
                stage.close();
                Main.window.close();
            });

            stage.setScene(scene);
            stage.show();
        }
    }
}
