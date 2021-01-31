package dxball;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Use this template to create Apps with Graphical User Interfaces.
 *
 * @author Sam Scott
 */
public class WelcomeScreen extends Application {

    // TODO: Instance Variables for View Components and Model
    private Canvas canvas;
    private GraphicsContext gc;
    private Stage stage;
    // TODO: Private Event Handlers and Helper Methods
    EventHandler<MouseEvent> nxt_screen= new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            DXBall d = new DXBall();
            try {
                d.start(stage);
            } catch (Exception ex) {
                Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            };
    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene2 = new Scene(root, 1500, 1000); // set the size here
        stage.setTitle("Welcome"); // set the window title here
        
        stage.setScene(scene2);
        // TODO: Add your GUI-building code here

        // 1. Create the model
        // 2. Create the GUI components
        canvas = new Canvas(1500,1000);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        //gc.setStroke(Color.WHITE);
        gc.strokeRect(30, 330, 900, 600);
        gc.strokeRect(400, 30, 600, 100);
        Image logo= new Image(getClass().getResourceAsStream("logo.png"));
        ImageView imgv_logo = new ImageView(logo);
        imgv_logo.relocate(500, 20);
        Text power_up = new Text(280,380,"Power-Ups");
        power_up.setFont(Font.font("Bradley Hand ITC",FontWeight.BOLD, 45));
        power_up.setFill(Color.ANTIQUEWHITE);
        power_up.setUnderline(true);
        Image spinning_ball = new Image(getClass().getResourceAsStream("dx_ball.gif"));
        ImageView spin_ball = new ImageView(spinning_ball);
        spin_ball.relocate(1050, 700);
        
        Image split = new Image(getClass().getResourceAsStream("p_split.png"));
        ImageView imgv_split = new ImageView(split);
        imgv_split.relocate(100, 450);
        Text txt_split = new Text(170,475,"Split-ball");
        txt_split.setFont(Font.font("Verdana",20));
        txt_split.setFill(Color.WHITE);
        Image slow = new Image(getClass().getResourceAsStream("p_slowball.png"));
        ImageView imgv_slow = new ImageView(slow);
        imgv_slow.relocate(500, 450);
        Text txt_slow = new Text(570,475,"Slow-ball");
        txt_slow.setFont(Font.font("Verdana",20));
        txt_slow.setFill(Color.WHITE);
        Image fast = new Image(getClass().getResourceAsStream("p_fastball.png"));
        ImageView imgv_fast = new ImageView(fast);
        imgv_fast.relocate(100, 650);
        Text txt_fast = new Text(170,675,"Fast-ball");
        txt_fast.setFont(Font.font("Verdana",20));
        txt_fast.setFill(Color.WHITE);
        Image shrink = new Image(getClass().getResourceAsStream("p_shrink.png"));
        ImageView imgv_shrink = new ImageView(shrink);
        imgv_shrink.relocate(500, 650);
        Text txt_shrink = new Text(570,670,"Shrink\nPaddle");
        txt_shrink.setFont(Font.font("Verdana",20));
        txt_shrink.setFill(Color.WHITE);
        Image expand = new Image(getClass().getResourceAsStream("p_expand.png"));
        ImageView imgv_expand = new ImageView(expand);
        imgv_expand.relocate(100, 850);
        Text txt_expand = new Text(170,870,"Expand\nPaddle");
        txt_expand.setFont(Font.font("Verdana",20));
        txt_expand.setFill(Color.WHITE);
        
        // 3. Add components to the root
        root.getChildren().addAll(canvas,spin_ball,imgv_split,imgv_slow,imgv_fast,imgv_shrink,imgv_expand,imgv_logo,txt_split,txt_slow,txt_fast,txt_shrink,
                txt_expand,power_up);
        // 4. Configure the components (colors, fonts, size, location)
        // 5. Add Event Handlers and do final setup
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, nxt_screen);
        // 6. Show the stage
        stage.show();
    }

    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
