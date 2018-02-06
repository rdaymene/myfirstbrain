/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question;

import DAO.Dao;
import DAO.QuestionBean;
import java.util.ArrayList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author $céline
 */
public class Questions extends BorderPane {

    private Dao questionDAO;
    private ArrayList<QuestionBean> ListNiveau1;
    private ArrayList<QuestionBean> ListNiveau2;
    private Text tQuestion;
    private TextField tfInput;
    private Text answer;
    private Button btCheck;
    private Button btSolution;
    private Button btOtherQuestion;
    private VBox vbText;//contient les 2 textfield (question et saisie réponse) 
    private VBox vbQuestion;
    private HBox hbButton;//contient les 3 boutons

    public Questions() {
        // on instancie l'objet Dao et les 2 listes d'objet question
        questionDAO = new Dao();
        ListNiveau1 = questionDAO.fillCollection(1);// remplit la collection avec les questions faciles
//         ListNiveau2 = questionDAO.fillCollection(2);// remplit la collection avec les questions difficiles

        this.setPadding(new Insets(15, 10, 15, 10));// on fait le padding du container parent
        vbText = new VBox(30);
        vbQuestion = new VBox();
        // on instancie le champ question
        tQuestion = new Text("Ici la question ?");
        tQuestion.setFont(new Font("Verdana", 30)); // grosse fonte pour la question 
        tQuestion.setTranslateY(20);
        tQuestion.setTranslateX(40);
        vbQuestion.setAlignment(Pos.CENTER);// on centre la question
        //tQuestion.setTextAlignment(TextAlignment.LEFT);
        // on instancie le champ réponse
        tfInput = new TextField();
        tfInput.setPromptText("Entrez votre réponse");
        tfInput.setPrefHeight(70);
        tfInput.setPrefWidth(20);
        tfInput.setPrefColumnCount(35);
        tfInput.setTranslateY(170);
        tfInput.setFont(new Font("Verdana", 30));
        tfInput.setStyle("-fx-background-color: #FEC3AC;");
        // on instancie le text qui fera apparaitre la bonne réponse
        answer = new Text();
        // on ajoute ces éléments à la vbox
        vbQuestion.getChildren().add(tQuestion);
        vbText.getChildren().addAll(tfInput, answer);
        // on instancie la hbox qui contiendra les boutons:
        hbButton = new HBox(20);
        hbButton.setSpacing(150);
        // on instancie les boutons
        btCheck = new Button("Vérifier");
        btCheck.setStyle("-fx-background-color: #FF6F7D;");
        btSolution = new Button("Solution");
        btSolution.setStyle("-fx-background-color: #FF6F7D;");
        btOtherQuestion = new Button("Autre question");
        btOtherQuestion.setStyle("-fx-background-color: #FF6F7D;");
        btCheck.setMinSize(130, 80);
        btSolution.setMinSize(130, 80);
        btOtherQuestion.setMinSize(130, 80);
        hbButton.setAlignment(Pos.CENTER);

        // on ajoute les boutons au hbox
        hbButton.getChildren().addAll(btCheck, btSolution, btOtherQuestion);

        // on ajoute les 2 box au container parent      
        this.setTop(vbQuestion);
        this.setCenter(vbText);
        this.setBottom(hbButton);
        this.setStyle("-fx-background-color: #D473D4;");

        // gestion evenementielle du bouton vérifier
        btCheck.setOnAction(e -> {
            // on appelle la methode booleenne de compraison question réponse
            if (getQuestionBeanFromList(tQuestion.getText(), tfInput.getText(), 1)) {
                // on colorie la zone réponse en vert
                tfInput.setStyle("-fx-background-color");
            } else {
                //on colorie la zone réponse en rouge
            }

        });
    }
    // methode qui rend le questionBean de la collection en fonction de l'attribut question

    public boolean getQuestionBeanFromList(String question, String reponse, int niveau) {
        
        if (niveau == 1) {
            for (QuestionBean qb : this.ListNiveau1) {// on parcourt la collection de liste facile
                // on compare la question à sa réponse            
                if (qb.getQuestion() == question && qb.getReponse() == reponse) {
                    return true;// la réponse est juste
                }
            }
        } else {// on parcourt la collection de liste difficile
            for (QuestionBean qb : this.ListNiveau1) {
                if (qb.getQuestion() == question && qb.getReponse() == reponse) {
                    return true;// la réponse est juste
               
                }
            }
        }
        return false;
            
    }
}