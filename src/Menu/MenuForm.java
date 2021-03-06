package Menu;

import Administration.AdministrationLogin;
import Question.Questions;
import calcul.Calcul;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import myfirstbrain.MyFirstBrain;
import static myfirstbrain.MyFirstBrain.questions;
import static myfirstbrain.MyFirstBrain.selectionModel;

public class MenuForm extends MenuBar {

    public static int level;
    public MenuForm(Stage primaryStage) {

        //=============================== CHANGEMENT DU FOND DU MENU ==================================================================================
        this.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));;
        //=============================== DECLARATION DES MENUS DU MENU PRINCIPAL =====================================================================
        this.level = 1;
        Menu menuActivite = new Menu();
        menuActivite.setText("Activité");
        Menu menuNiveau = new Menu();
        menuNiveau.setText("Niveau");
        Menu menuAdministration = new Menu();
        menuAdministration.setText("Administration");
        //=============================== AJOUT DES MENUS AU MENU PRINCIPAL ===========================================================================
        this.getMenus().setAll(menuActivite, menuNiveau, menuAdministration);
        //=============================== DECLARATION DES MENU ITEM DE CHAQUE MENU DU MENU PRINCIPAL ======================================
        MenuItem menuDrawItem = new MenuItem();
        menuDrawItem.setText("Dessin");
        MenuItem menuCalculItem = new MenuItem();
        menuCalculItem.setText("Calcul");
        MenuItem menuQuestionItem = new MenuItem();
        menuQuestionItem.setText("Questions");
        MenuItem menuNiveau1Item = new MenuItem();
        menuNiveau1Item.setText("Niveau 1");
        MenuItem menuNiveau2Item = new MenuItem();
        menuNiveau2Item.setText("Niveau 2");
        MenuItem menuSeConnecterItem = new MenuItem();
        menuSeConnecterItem.setText("Se connecter");
        menuSeConnecterItem.setOnAction((ActionEvent event) -> {
            AdministrationLogin calculette = new AdministrationLogin(primaryStage);
        });
        menuNiveau1Item.setOnAction(e -> {
            level = 1;
            MyFirstBrain.questions = new Questions();
            MyFirstBrain.questionTab.setContent(questions);           
            MyFirstBrain.calcul = new Calcul();
            MyFirstBrain.calculTab.setContent( MyFirstBrain.calcul);
        });

        menuNiveau2Item.setOnAction(e -> {
            level = 2;
            MyFirstBrain.questions = new Questions();
            MyFirstBrain.questionTab.setContent(questions);
          
            MyFirstBrain.calcul = new Calcul();
            MyFirstBrain.calculTab.setContent( MyFirstBrain.calcul);
        });

        //gestion évènements du menu activité pour qu'on ouvre l'onglet choisi
        menuQuestionItem.setOnAction(e -> {
            MyFirstBrain.selectionModel.select(2);
            MyFirstBrain.questions = new Questions();
            MyFirstBrain.questionTab.setContent(questions);
           
        });
        menuCalculItem.setOnAction(e -> {
            MyFirstBrain.selectionModel.select(1);
            MyFirstBrain.calcul = new Calcul();
            MyFirstBrain.calculTab.setContent( MyFirstBrain.calcul);
            
        });
        menuDrawItem.setOnAction(e -> {
            MyFirstBrain.selectionModel.select(0);
        });
        //=============================== AJOUT A CHAQUE MENU DE SES ITEMS ======================================
        menuNiveau.getItems().setAll(menuNiveau1Item, menuNiveau2Item);
        menuActivite.getItems().setAll(menuDrawItem, menuCalculItem, menuQuestionItem);
        menuAdministration.getItems().setAll(menuSeConnecterItem);
    }

}
