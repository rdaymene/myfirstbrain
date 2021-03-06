/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question;
//
import DAO.Dao;
import DAO.QuestionBean;
import Menu.MenuForm;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Question.MyButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author $céline
 */
public class Questions extends BorderPane {

    //attributs qui concerne le background classic
    private final Image questionclassic = new Image(getClass().getResourceAsStream("backgroundquestionclassic.png"));
    private final BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
    private final Background backgroundquestionclassic = new Background(new BackgroundImage(questionclassic,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bSize));
    //attributs qui concerne le background classic
    private final Image questionexpert = new Image(getClass().getResourceAsStream("backgroundquestionexpert.png"));
    private final Background backgroundquestionexpert = new Background(new BackgroundImage(questionexpert,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bSize));

    private Dao questionDAO;
    private ArrayList<QuestionBean> ListNiveau1;
    private ArrayList<QuestionBean> ListNiveau2;
     private QuestionBean questionBean;
    private Label tQuestion;
    private TextField tfInput;
    private Text answer;
    private MyButton btCheck;
    private MyButton btSolution;
    private MyButton btOtherQuestion;
    private VBox vbQuestion;
    private HBox hbButton;//contient les 3 boutons
   

    public Questions() {

        // on instancie l'objet Dao et les 2 listes d'objet question
        questionDAO = new Dao();
        ListNiveau1 = questionDAO.fillCollection(1);// remplit la collection avec les questions faciles
        ListNiveau2 = questionDAO.fillCollection(2);// remplit la collection avec les questions difficiles
        // on instancie 2 listes qui contiendront les questions déjà passées
        ArrayList<QuestionBean> List1Passed = new ArrayList<>();
        ArrayList<QuestionBean> List2Passed = new ArrayList<>();
        // on fixe un padding au container parent
        this.setPadding(new Insets(15, 10, 15, 10));// on fait le padding du container parent
        // on instancie la vbox qui contiendra la question,la réponse et champ de saisie
        vbQuestion = new VBox(30);
        vbQuestion.setSpacing(60);
        vbQuestion.setAlignment(Pos.CENTER);

        // on instancie le champ question et on le place et on l'embellit avec du css
        tQuestion = new Label();
        tQuestion.setTranslateY(50);
        tQuestion.setTranslateX(35);
        tQuestion.setMaxWidth(780);
        tQuestion.setWrapText(true);
        tQuestion.setMaxHeight(400);
        tQuestion.getStyleClass().add("Question");

        //on remplit le texte avec une question aléatoire
        if (MenuForm.level == 1) {// de la liste 1 si niveau1
            this.questionBean = ListNiveau1.get(getRandomQuestionBean(ListNiveau1.size() - 1));
            List1Passed.add(questionBean);
            System.out.println(ListNiveau1.size());
        } else {// de la liste 2 si niveau 2
            this.questionBean = ListNiveau2.get(getRandomQuestionBean(ListNiveau2.size() - 1));
            List2Passed.add(questionBean);
        }
        // on affiche la question aléatoire 
        tQuestion.setText(questionBean.getQuestion());

        // on instancie et positionne le texte qui donne la réponse 
        answer = new Text();
        answer.setFont(new Font("Verdana", 20));

        // on instancie le champ réponse et on le positionne
        tfInput = new TextField();
        tfInput.setPromptText("Entrez votre réponse");
        tfInput.setPrefHeight(80);
        tfInput.setPrefWidth(20);
        tfInput.setLayoutY(100);
        tfInput.getStyleClass().add("TextField");// css pour le textfield          
        tfInput.requestFocus();
        tfInput.setPrefColumnCount(100);

        // on ajoute ces éléments à la vbox
        vbQuestion.getChildren().addAll(tQuestion, answer, tfInput);

        // on instancie la hbox qui contiendra les boutons:
        hbButton = new HBox(20);
        hbButton.setSpacing(150);
        // on instancie les boutons
        btCheck = new MyButton("Vérifier");
        btSolution = new MyButton("Solution");
        btOtherQuestion = new MyButton("Autre question");
        btCheck.setMinSize(130, 80);
        btOtherQuestion.setMinSize(130, 80);
        // on positionne ces boutons au centre de son container
        hbButton.setAlignment(Pos.CENTER);
        // on ajoute les boutons au hbox
        hbButton.getChildren().addAll(btCheck, btSolution, btOtherQuestion);

        // on ajoute les 3 box au container parent  et on les place
        this.setCenter(vbQuestion);
        this.setBottom(hbButton);
        // on donne un background en fonction du niveau
        this.setBackground(MenuForm.level == 1 ? backgroundquestionclassic : backgroundquestionexpert);

////////////////////////////GESTION EVENEMENTIELLE //////////////////////////////////
        // gestion evenementielle du bouton vérifier
        btCheck.setOnAction(e -> {
            answer.setText(null);
            tfInput.setStyle("-fx-background-color : #fff");
            // on appelle la methode booleenne de comparaison question réponse
            if (compareAnswerToRightAnswer(tQuestion.getText(), tfInput.getText())) {// reponse juste
                // on colorie la zone réponse en vert
                tfInput.setStyle("-fx-background-color : #0E8C1B");
            } else {// réponse fausse
                //on colorie la zone réponse en rouge
                tfInput.setStyle("-fx-background-color : #D20303;");
            }

        });
/////// // gestion évènement du bouton solution
        btSolution.setOnAction(e -> {
            answer.setText(null);
            tfInput.setStyle("-fx-background-color : #fff");
            // si réponse correcte
            if (compareAnswerToRightAnswer(tQuestion.getText(), tfInput.getText())) {
                // on colorie la zone réponse en vert
                tfInput.setStyle("-fx-background-color : #0E8C1B");
                answer.setFill(Color.web("#38591F"));
                answer.setText("Bonne réponse : " + this.questionBean.getReponse());// on affiche bonne réponse
            } else {
                //on colorie la zone réponse en rouge
                answer.setFill(Color.web("#D20303"));
                tfInput.setStyle("-fx-background-color : #D20303;");

                // on affiche la bonne réponse
                answer.setText("Mauvaise réponse. La réponse est : " + this.questionBean.getReponse());
            }
        });
//////// gestion évenementielle pour le bouton autre question
        btOtherQuestion.setOnAction(e -> {
            // on change le background en fonction du niveau
            this.setBackground(MenuForm.level == 1 ? backgroundquestionclassic : backgroundquestionexpert);
            answer.setText(null);// on levelInvide le texte qui mentionne la réponse
            tfInput.clear();// on vide le champ de saisie
            tfInput.setStyle("-fx-background-color : #FFF;");// on colorie en blanc le champ
//on remplit le texte avec une question aléatoire
            if (MenuForm.level == 1) {// si niveau 1 
                // on vide la liste de questions passées si elle contient ttes les questions
                if (ListNiveau1.size() == List1Passed.size()) {
                    List1Passed.removeAll(ListNiveau1);
                }
                do {
                    // on choisit une question tant qu'elle figure dans celles déjà passée
                    this.questionBean = ListNiveau1.get(getRandomQuestionBean(ListNiveau1.size() - 1));
                } while (List1Passed.contains(questionBean));
                List1Passed.add(questionBean);// on ajoute la nouvelle question à celles déjà passées

            } else {// si niveau 2 on fait pareil mais avec liste niveau 2
                if (ListNiveau2.size() == List2Passed.size()) {
                    List2Passed.removeAll(ListNiveau2);
                }
                do {
                    this.questionBean = ListNiveau2.get(getRandomQuestionBean(ListNiveau2.size() - 1));
                } while (List2Passed.contains(questionBean));
                List2Passed.add(questionBean);
            }
            tQuestion.setText(questionBean.getQuestion());// on affiche la question
        });
          ///gestion evenement clavier
        tfInput.setOnKeyPressed(ke->{
            
            if(ke.getCode() == KeyCode.ENTER){
            answer.setText(null);
            tfInput.setStyle("-fx-background-color : #fff");
            // on appelle la methode booleenne de comparaison question réponse
            if (compareAnswerToRightAnswer(tQuestion.getText(), tfInput.getText())) {// reponse juste
                // on colorie la zone réponse en vert
                tfInput.setStyle("-fx-background-color : #0E8C1B");
            } else {// réponse fausse
                //on colorie la zone réponse en rouge
                tfInput.setStyle("-fx-background-color : #D20303;");
            }
            }
                
        });        
    }
////////////////////////METHODES GESTION QUESTION ALEATOIRE    ///////////////////////////

    // methode qui compare la réponse donnée à la bonne et retourne vrai ou faux
    public boolean compareAnswerToRightAnswer(String question, String reponse) {
        ArrayList<QuestionBean> qb = new ArrayList<>();
        if (MenuForm.level == 1) {// si niveau 1 on charge la liste 1
            qb = ListNiveau1;
        } else {// sinon on charge la 2
            qb = ListNiveau2;
        }
        // on parcourt la nouvelle liste (créée à partir de la liste appropriée)
        for (QuestionBean q : qb) {
            // on crée une string qui va retirer les accents à la bonne réponse 
            String rightAnswer = Normalizer.normalize(q.getReponse(),
                    Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            // on crée une string qui va retirer les accents à la réponse donnée
            String givenAnswer = Normalizer.normalize(reponse,
                    Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            // on cherche  la question dans la liste et on compare  sa réponse à la réponse donnée
            // avec accents retirés et en minuscules
            if (q.getQuestion().equals(question)
                    && rightAnswer.toLowerCase().equals(givenAnswer.toLowerCase())) {
                return true;// la réponse est juste
            }
        }
        return false;// par défaut on dit que la réponse est fausse
    }

    // generation d'un nombre aleatoire pour charger une question
    public int getRandomQuestionBean(int size) {
        Random rand = new Random();
        int min = 0;// car une l'indice d'une liste commence à zéro
        int randomNum = 0;
        randomNum = rand.nextInt((size - min) + 1) + min;
        // numero aléatoire entre 0 et le nombre  max de questions dans liste facile
        return randomNum;// on retourne le nombre aléatoire
    }
// getter du textfield pour focus

    public TextField getTfInput() {
        return this.tfInput;
    }

}
