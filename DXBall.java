package dxball;

import com.sun.javafx.cursor.CursorFrame;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

/**
 * Use this template to create Apps with Graphical User Interfaces.
 *
 * @author Shashwat Kumar
 */
public class DXBall extends Application {

    // TODO: Instance Variables for View Components and Model
    private Stage stage1;
    private Pane root,root1,root2;
    private Scene scene1, scene2, scene3;
    private Power power;
    private Circle ball, ball2;
    private Rectangle paddle,p,q,r,s,u, life1,life2,life3, brick;
    //private Brick brick;
    private Timeline timeline,timeline2,timeline3;
    private TranslateTransition t1,t2,t3,t4,t5;
    private TranslateTransition[] t;
    private PauseTransition[] pt;
    private Canvas canvas, canvas2, canvas3;
    private GraphicsContext gc, gc1,gc2;
    private SequentialTransition ss;
    private double ballSpeed = 1;
    private double dx = 10, dx2 = 10; //Step on x or velocity
    private double dy = 5, dy2 = 5; //Step on y
    private ArrayList<Rectangle> brickArray;
    //private Rectangle[] powerArray;
    private ImageView[] powerArray;
    private Image[] powers;
    private  Random rand;
    private boolean isFinished = false;
    private int k;
    private MediaPlayer mediaPlayer;
    private String musicFile;
    private Media sound;
    private ObservableBooleanValue[] obv;
    private  TableView tableView ;
    private boolean flag = true;
    private String fname = "";
    private String lname = "";
    private int a = 3;
    private int sc = 8764;
    private static int sc_cnt;
    // private Paddle paddle;
    
    // TODO: Private Event Handlers and Helper Methods
    
    EventHandler<MouseEvent> paddleHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
            paddle.setTranslateX(t.getX());
        }   
    };
    
    EventHandler<ActionEvent> ballCollision = new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent t) {
                
            	//move the ball
            	ball.setLayoutX((ball.getLayoutX() + dx*ballSpeed));
            	ball.setLayoutY(ball.getLayoutY() + dy*ballSpeed);

                //If the ball reaches the left or right border make the step negative
                if(ball.getLayoutX() <= (0 + ball.getRadius()) || 
                        ball.getLayoutX() >= (canvas.getWidth() - ball.getRadius()) ){
                	dx = -dx;
                }

                //If the ball reaches the bottom or top border make the step negative
                if(ball.getLayoutY() <= (0 + ball.getRadius())){                    
                    dy = -dy;
                }
                
                if(ball.getLayoutY() >= (canvas.getHeight() + ball.getRadius())){
                    
                    if(root.getChildren().contains(ball2)){
                        timeline.stop();
                        root.getChildren().remove(ball);
                    }
                    else{
                        timeline.stop();
                        if(root.getChildren().contains(life3)){
                            ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
                            canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
                            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                            root.getChildren().remove(life3);
                        }
                        else if(root.getChildren().contains(life2)){
                            ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
                            canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
                            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                            root.getChildren().remove(life2);
                        }
                        else if(root.getChildren().contains(life1)){
                            ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
                            canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
                            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                            root.getChildren().remove(life1);
                        }
                        else{
                            timeline.stop();
                            flag = false;
                            
                            //System.out.println(sc_cnt);
                            Text txt_score = new Text();
                            txt_score.setFill(Color.WHITE);

                            txt_score.setText("Your Score is:\n"+"      "+sc_cnt);

                            txt_score.setFont(Font.font("Verdana",25));
                            txt_score.relocate(600, 320);
                            root2.getChildren().add(txt_score);
                            System.out.println("game over");
                            
                            FadeTransition fd = new FadeTransition();
                            fd.setDuration(Duration.millis(1000));
                            fd.setNode(root);
                            fd.setFromValue(1);
                            fd.setToValue(0);
                            fd.setOnFinished(e->{
                                stage1.setScene(scene3);
                                root2.setOpacity(0);
                                FadeTransition fd1 = new FadeTransition();
                                fd1.setDuration(Duration.millis(1000));
                                fd1.setNode(root2);
                                fd1.setFromValue(0);
                                fd1.setToValue(1);
                                fd1.setOnFinished(e2->root2.setOpacity(1));
                                fd1.play();
                                //ss.play(); 
                                    });
                            fd.play();
                            
                            //stage1.setScene(scene3);
                        }
                    }
                }
                
                if(ball.getLayoutX() >= (paddle.getTranslateX()-ball.getRadius())&&ball.getLayoutX()<=(paddle.getTranslateX()+paddle.getWidth())
                        && ball.getLayoutY() >= (paddle.getTranslateY()-ball.getRadius()) ){
                    double diff = (ball.getLayoutX()-(paddle.getTranslateX()+(paddle.getWidth()/2)+ball.getRadius()));
                     //System.out.println(diff);
                    if(diff<0){
                        if(dx > 0){
                            dx = diff/dx;
                            
                         //dx = -dx-diff;
                        }
                        
                         
                     //dx = dx-diff/50;
                    }
                    else if(diff>0){
                        if(dx<0){
                            dx = -diff/dx;
                            
                        }
                    }
                    dy = -dy;
                }
                 
                Iterator<Rectangle> iter = brickArray.iterator();

                while (iter.hasNext()) {
                    Rectangle b = iter.next();
                if(ball.getLayoutX()>=b.getX()&&ball.getLayoutX()<=b.getX()+b.getWidth()){                  
                      if(ball.getLayoutY()== (b.getY())||ball.getLayoutY() == (b.getY()+ball.getRadius()+b.getHeight())){
                        dy = -dy;
                        sc_cnt+=50;
                        //dx = -dx;
                        iter.remove();
                        root.getChildren().remove(b);
                        gc.clearRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
                     }
                      else if(ball.getLayoutY()>=b.getY()&&ball.getLayoutY()<=(b.getY()+b.getHeight()+ball.getRadius())){
                        dx = -dx;
                        //dy = -dy;
                        sc_cnt+=50;
                        iter.remove();
                        root.getChildren().remove(b);
                        gc.clearRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
                    }
                      
                      
                }
                    
                }
                
            }  
    };
    
    EventHandler<ActionEvent> ball2Collision = new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent t) {
                  
//                double dx2 = 7;
//                double dy2 = 8;
            	//move the ball
            	ball2.setLayoutX((ball2.getLayoutX()+ dx2*ballSpeed));
            	ball2.setLayoutY(ball2.getLayoutY() + dy2*ballSpeed);

                //If the ball reaches the left or right border make the step negative
                if(ball2.getLayoutX() <= (0 + ball2.getRadius()) || 
                        ball2.getLayoutX() >= (canvas.getWidth() - ball2.getRadius()) ){
                	dx2 = -dx2;
                }
                   
                //If the ball reaches the bottom or top border make the step negative
                if(ball2.getLayoutY() <= (0 + ball2.getRadius())){                    
                    dy2 = -dy2;
                }
                
                if(ball2.getLayoutY() >= (canvas.getHeight() + ball2.getRadius())){
                    if(root.getChildren().contains(ball)){
                        timeline2.stop();
                        root.getChildren().remove(ball2);
                    }
                    else{
//                        timeline2.stop();
//                         root.getChildren().add(ball);
//                        ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
//                        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
//                        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                        timeline2.stop();
                        root.getChildren().remove(ball2);
                        root.getChildren().add(ball);
                        if(root.getChildren().contains(life3)){
                            ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
                            canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
                            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                            root.getChildren().remove(life3);
                        }
                        else if(root.getChildren().contains(life2)){
                            ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
                            canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
                            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                            root.getChildren().remove(life2);
                        }
                        else if(root.getChildren().contains(life1)){
                            ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
                            canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
                            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                            root.getChildren().remove(life1);
                        }
                        else{
                            timeline.stop();
                            flag = false;
                            Text txt_score = new Text();
                            txt_score.setFill(Color.WHITE);

                            txt_score.setText("Your Score is:\n"+"      "+sc_cnt);

                            txt_score.setFont(Font.font("Verdana",25));
                            txt_score.relocate(600, 320);
                            root2.getChildren().add(txt_score);
                            //stage1.setScene(scene3);
                            
//                           gc = canvas.getGraphicsContext2D();
//                            gc.setFill(Color.BLACK);
//                            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            //stage.setScene(scene3);
                            //System.out.println("yob");
                            System.out.println("game over");
                            
                            
                            
                            FadeTransition fd = new FadeTransition();
                            fd.setDuration(Duration.millis(1000));
                            fd.setNode(root);
                            fd.setFromValue(1);
                            fd.setToValue(0);
                            fd.setOnFinished(e->{
                                stage1.setScene(scene3);
                                root2.setOpacity(0);
                                FadeTransition fd1 = new FadeTransition();
                                fd1.setDuration(Duration.millis(1000));
                                fd1.setNode(root2);
                                fd1.setFromValue(0);
                                fd1.setToValue(1);
                                fd1.setOnFinished(e2->root2.setOpacity(1));
                                fd1.play();
                                //ss.play(); 
                                    });
                            fd.play();
                        }
                    }
                    
//                    ball2.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
//                    canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
//                    canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
                }
                
                if(ball2.getLayoutX() >= (paddle.getTranslateX()-ball2.getRadius())&&ball2.getLayoutX()<=(paddle.getTranslateX()+paddle.getWidth())
                        && ball2.getLayoutY() >= (paddle.getTranslateY()-ball2.getRadius()) ){
                    double diff = (ball2.getLayoutX()-(paddle.getTranslateX()+(paddle.getWidth()/2)+ball2.getRadius()));
                     //System.out.println(diff);
                    if(diff<0){
                        if(dx2 > 0){
                            dx2 = diff/dx2;
                            
                         //dx = -dx-diff;
                        }
                        
                         
                     //dx = dx-diff/50;
                    }
                    else if(diff>0){
                        if(dx2<0){
                            dx2 = -diff/dx2;
                            
                        }
                    }
                    dy2 = -dy2;
                }
                 
                Iterator<Rectangle> iter = brickArray.iterator();

                while (iter.hasNext()) {
                    Rectangle b = iter.next();
                if(ball2.getLayoutX()>=b.getX()&&ball2.getLayoutX()<=b.getX()+b.getWidth()){                  
                      if(ball2.getLayoutY()== (b.getY())||ball2.getLayoutY() == (b.getY()+ball2.getRadius()+b.getHeight())){
                          
                        dy2 = -dy2;    
                        sc_cnt+=50;
                        iter.remove();
                        root.getChildren().remove(b);
                        gc.clearRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
                     }
                      else if(ball2.getLayoutY()>=b.getY()&&ball2.getLayoutY()<=(b.getY()+b.getHeight()+ball2.getRadius())){
                        dx2 = -dx2;
                        sc_cnt+=50;
                        iter.remove();
                        root.getChildren().remove(b);
                        gc.clearRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
                    }
                }
                }
                
            }  
    };
    
    EventHandler<MouseEvent> ballRelease = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
           canvas.removeEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
           canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
           timeline = new Timeline(new KeyFrame(Duration.millis(20), ballCollision));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
            
    };

    EventHandler<MouseEvent> ballMovementBeforeRelease = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent t) {
           
            paddle.setTranslateX(t.getX());
           // ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*2);
            ball.setLayoutX(paddle.getTranslateX()+paddle.getWidth()/2);
           // ball.setLayoutX(1270);
            ball.setLayoutY(paddle.getTranslateY()-ball.getRadius()*2);
            //ball.setLayoutY(300);
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
    public void start (Stage stage) throws Exception {
        stage1 = new Stage();
        root = new Pane();
        root1 = new Pane();
        root2 = new Pane();
        scene1 = new Scene(root1, 1500, 1000);
        scene2 = new Scene(root, 1500, 1000); // set the size here
        scene3 = new Scene(root2, 1500, 1000);
        scene3.getStylesheets().add(this.getClass().getResource("dxball.css").toExternalForm());
        stage.setTitle("FX GUI Template"); // set the window title here
        stage1.setScene(scene1);
        // TODO: Add your GUI-building code here
        // 1. Create the model
        //Ball ball = new Ball(20,10,10,Color.BLACK);
        /*
        Scene 1
        */
        canvas2 = new Canvas(1500,1000);
        gc1 = canvas2.getGraphicsContext2D();
        gc1.setFill(Color.BLACK);
        gc1.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        gc1.setFill(Color.WHITE);
        //gc.setStroke(Color.WHITE);
        gc1.strokeRect(30, 330, 900, 600);
        gc1.strokeRect(400, 30, 600, 100);
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
        
        
        
        /*
        Scene 2
        */
        musicFile = "Barnicle.mp3";     // For example

        sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
//        final java.net.URL resource = getClass().getResource("Barnicle.mp3");
//        final Media media = new Media(resource.toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
       //AudioClip mApplause = new AudioClip(this.getClass().getResource("test.mp3").toExternalForm());
        ball = new Circle(10,Color.BLACK);
        ball2 = new Circle(10,Color.BLACK);
        //brick = new Rectangle(50,30,Color.BLACK);
        power = new Power();
        paddle = new Rectangle(200,30,Color.BLACK);
        paddle.setTranslateX(330);
        paddle.setTranslateY(850);
        paddle.setArcWidth(20);
        paddle.setArcHeight(20);
        brickArray = new ArrayList();
        rand = new Random();
       // powerArray = new Rectangle[10];
        powerArray = new ImageView[10];
        powers = new Image[10];
        powers[0] = new Image(getClass().getResourceAsStream("p_split.png"));
        powers[1] = new Image(getClass().getResourceAsStream("p_slowball.png"));
        powers[2] = new Image(getClass().getResourceAsStream("p_fastball.png"));
        powers[3] = new Image(getClass().getResourceAsStream("p_shrink.png"));
        powers[4] = new Image(getClass().getResourceAsStream("p_expand.png"));
        
        for(int i = 0; i<powerArray.length;i++){
//            powerArray[i] = new Rectangle(50,50,Color.BLACK);
//            powerArray[i].setLayoutX(rand.nextInt(1300)+10);
            powerArray[i] = new ImageView(powers[i]);
            powerArray[i].setLayoutX(rand.nextInt(1300)+10); 
            //powerArray[i].setStyle("-fx-background-image: url(\"p.jpg\")");
        }
        
        // 2. Create the GUI components
        canvas = new Canvas(1300,1000);
        gc = canvas.getGraphicsContext2D();
        
        life1 = new Rectangle(55,15,Color.BLACK);
        life1.setTranslateX(1300);
        life1.setTranslateY(30);
        life1.setArcWidth(20);
        life1.setArcHeight(20);
        
        life2 = new Rectangle(55,15,Color.BLACK);
        life2.setTranslateX(1360);
        life2.setTranslateY(30);
        life2.setArcWidth(20);
        life2.setArcHeight(20);
        
        life3 = new Rectangle(55,15,Color.BLACK);
        life3.setTranslateX(1420);
        life3.setTranslateY(30);
        life3.setArcWidth(20);
        life3.setArcHeight(20);
        
        
        for(int i = 3; i<8; i++){
            
            for(int j =3; j <13; j++){
                brick = new Rectangle(0,0,50,30);
                root.getChildren().add(brick);
                brick.setY(i*60);
                brick.setX(j*100);
                //gc.fillRect(j*55, i*35, 50, 30);
               
                brick.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");
               //brick.draw(gc);
                brickArray.add(brick);   
            }
        }
        t = new TranslateTransition[5];
        pt = new PauseTransition[5];
        for(int  i=0; i<5;i++){
              t[i] = new TranslateTransition();
              t[i].setDuration(Duration.millis(4000));
              t[i].setNode(powerArray[i]);
              t[i].setToY(canvas.getHeight());
              
        }
          
        for(int i = 0; i<5; i++){
              
              pt[i] = new PauseTransition();
              pt[i].setDuration(Duration.seconds(rand.nextInt(6)+3));            
              pt[i].setOnFinished(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    root.getChildren().add(powerArray[k]);
                    k++;
                
                }
                
              });
              
        }
          
        obv = new ObservableBooleanValue[5];   
        obv[0] = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return  powerArray[0].getBoundsInParent().intersects(paddle.getBoundsInParent());  
            }

        }, powerArray[0].boundsInParentProperty(), paddle.boundsInParentProperty());

        obv[0].addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs,
                    Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //System.out.println("yo");
                    //double a = power.slowBall(ballSpeed);
                    //System.out.println(ballSpeed);
                    root.getChildren().remove(powerArray[0]);
                     root.getChildren().add(ball2);
                     ball2.relocate(ball.getLayoutX(), ball.getLayoutY());
                    timeline2 = new Timeline(new KeyFrame(Duration.millis(20), ball2Collision));
                   
                    timeline2.setCycleCount(Timeline.INDEFINITE);
                   
                    timeline2.play();
//                    ball.setLayoutX((ball.getLayoutX() + dx*ballSpeed));
//                    ball.setLayoutY(ball.getLayoutY() + dy*ballSpeed);
                    //timeline.stop();
//                    timeline2 = new Timeline(new KeyFrame(Duration.millis(20), ballCollision));
//                    timeline2.setCycleCount(Timeline.INDEFINITE);
//                    timeline2.play();
                } 
            }
        });

        obv[1] = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return  powerArray[1].getBoundsInParent().intersects(paddle.getBoundsInParent());  
            }
        }, powerArray[1].boundsInParentProperty(), paddle.boundsInParentProperty());

        obv[1].addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs,
                    Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //System.out.println("hoe");
//                    power.fastBall(ballSpeed);
//                    ball.setLayoutX((ball.getLayoutX() + dx*ballSpeed));
//                    ball.setLayoutY(ball.getLayoutY() + dy*ballSpeed);
//                    timeline.stop();
//                   timeline2.stop();
//                    timeline3 = new Timeline(new KeyFrame(Duration.millis(20), slowBallMove));
//                    timeline3.setCycleCount(Timeline.INDEFINITE);
//                    timeline3.play();
                    root.getChildren().remove(powerArray[1]);
                    ballSpeed = 0.25;
                } 
            }
        });

        obv[2] = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return  powerArray[2].getBoundsInParent().intersects(paddle.getBoundsInParent());  
            }
        }, powerArray[2].boundsInParentProperty(), paddle.boundsInParentProperty());

        obv[2].addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs,
                    Boolean oldValue, Boolean newValue) {
                //System.out.println(obs.getValue());
                if (newValue) {
                    //System.out.println("yohoes");
                    root.getChildren().remove(powerArray[2]);
                    ballSpeed = 1;
                    
                } 
            }
        });

        obv[3] = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return  powerArray[3].getBoundsInParent().intersects(paddle.getBoundsInParent());  
            }

        }, powerArray[3].boundsInParentProperty(), paddle.boundsInParentProperty());

        obv[3].addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs,
                    Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //System.out.println("yo");
                    root.getChildren().remove(powerArray[3]);
                    power.small(paddle);
                } 
            }
        });

        obv[4] = Bindings.createBooleanBinding(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return  powerArray[4].getBoundsInParent().intersects(paddle.getBoundsInParent());  
            }

        }, powerArray[4].boundsInParentProperty(), paddle.boundsInParentProperty());

        obv[4].addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs,
                    Boolean oldValue, Boolean newValue) {
                if (newValue) {
                   // System.out.println("hoe");
                   //power.big(paddle);
                   root.getChildren().remove(powerArray[4]);
                   power.big(paddle);
                } 
            }
        });
          
        ss = new SequentialTransition(pt[0],t[0],pt[1],t[1],pt[2],t[2],pt[3],t[3],pt[4],t[4]);
//        ss.play(); 

        /*
        Scene 3
        */
        
        canvas3 = new Canvas(1500,1000);
        gc2 = canvas3.getGraphicsContext2D();
        Text text = new Text();
        text.setText("High Score");
        text.setFont(Font.font("Ravie",100));
        text.setFill(Color.LIMEGREEN);
        text.relocate(400, 100);
        //if(stage1.getScene()==scene3){
           
//        Text txt_score = new Text();
//        txt_score.setFill(Color.WHITE);
//        if(stage1.getScene().equals(scene2)){
//            System.out.println(sc_cnt);
////        txt_score.setText("Your Score is:\n"+" \t "+sc_cnt);
//    
//        txt_score.setFont(Font.font("Verdana",25));
//        txt_score.relocate(600, 320);
//        //root2.getChildren().add(txt_score);
        //}
        gc2.setFill(Color.BLACK);
        gc2.fillRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
        gc2.setFill(Color.INDIGO);
        gc2.fillRect(350, 50, 800, 200);
        Text name = new Text();
        name.setText("Enter your name:");
        name.setFill(Color.WHITE);
        name.setFont(Font.font("Verdana",25));
        name.setLineSpacing(100);
        name.relocate(100, 400);
        TextField txt_name = new TextField();
        //txt_name.setStyle("-fx-display-caret: true");
        txt_name.relocate(90, 430);
        txt_name.setCursor(Cursor.TEXT);
        txt_name.setShape(Shape.subtract(name,name));
        txt_name.setStyle("-fx-text-fill: wheat;");
        txt_name.setFont(Font.font("Lucida Handwriting",20));
        
        
        
        root2.setOnKeyPressed(e->{
            
       root2.getChildren().removeAll(txt_name, name);
            
            
            
            if(e.getCode()==KeyCode.ENTER){
                int space_index = txt_name.getText().indexOf(" ");
                
                if(txt_name.getText().contains(" ")){
                    fname = txt_name.getText().substring(0, space_index);
                    lname = txt_name.getText().substring(space_index++);
                }
                else{
                    fname = txt_name.getText();
                    lname = "NULL";
                }
                
                fname = fname.trim();
                lname = lname.trim();
                //System.out.println(fname.trim());
                
                //System.out.println(lname.trim());
                
                
                
                 tableView = new TableView();
                 tableView.relocate(0, 400);
                 tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setPrefHeight(550);
            tableView.setPrefWidth(canvas3.getWidth());
           // tableView.setBorder(Border.);
          // tableView.setStyle("-fx-table-cell-border-color: transparent;");
           
        TableColumn column0 = new TableColumn<>("ID");
        column0.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn <String, Player>column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));


        TableColumn<String, Player> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        
        TableColumn column3 = new TableColumn<>("Score");
        column3.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        tableView.getColumns().add(column0);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        
//         column0.setStyle("-fx-font-size: 30; -fx-text-fill: #0ed9e0; -fx-background-color: black; -fx-alignment: center;"
//                 + "-fx-table-cell-border-color: transparent;");
//         column1.setStyle("-fx-font-size: 30; -fx-text-fill: #0ed9e0; -fx-background-color: black; -fx-alignment: center;"
//                 + "-fx-table-cell-border-color: transparent;");
//         column2.setStyle("-fx-font-size: 30; -fx-text-fill: #0ed9e0; -fx-background-color: black; -fx-alignment: center;"
//                 + "-fx-table-cell-border-color: transparent;");
//         column3.setStyle("-fx-font-size: 30; -fx-text-fill: #0ed9e0; -fx-background-color: black; -fx-alignment: center;"
//                 + "-fx-table-cell-border-color: transparent;");
//   
     try{
    
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn=DriverManager.getConnection("jdbc:ucanaccess://Database41.accdb");
        Statement stment = conn.createStatement();
        
        String qry2 = "INSERT INTO test VALUES('"+a+"','"+fname+"','"+lname+"', '"+sc_cnt+"')";
        stment = conn.createStatement();
        stment.executeUpdate(qry2);
        
        String qry = "SELECT * FROM test ORDER BY Score DESC";
        
        
        //ResultSet rs2 = stment.executeQuery(qry2);
        ResultSet rs = stment.executeQuery(qry);
        while(rs.next())
        {
            String id    = rs.getString("ID") ;
            String fn = rs.getString("First Name");
            String ln = rs.getString("Last Name");
            String score = rs.getString("Score");
            tableView.getItems().add(new Player(id,fn,ln,score));
            //System.out.println(id +" " +fn+" " +" "+ ln+" " + score);
        }
        
        root2.getChildren().addAll(tableView);
        
    }
    catch(Exception err)
    {
        System.out.println(err);
    }
                
                
                
            }
        });
        
        
//        txt_name.setTextFormatter(new TextFormatter<>((change) -> {
//            
//            change.setText(change.getText().substring(0, space_index));
//            return change;
//        }));
        
      // Cursor c = new Cursor();
//             timeline.setOnFinished(e->{
//                 //if(flag == false){
//                 System.out.println("bboo");
//                 //}
//                     });
             //System.out.println("bboo");
        //stage.setScene(scene3);
    
        //gc.clearRect(0, 0, dx, dx);
        //gc.fillRect(0, 0, canvas3.getWidth(), canvas3.getHeight());
        // 3. Add components to the root
       
        root.getChildren().addAll(canvas,ball, paddle, life1,life2,life3);
        root1.getChildren().addAll(canvas2,spin_ball,imgv_split,imgv_slow,imgv_fast,imgv_shrink,imgv_expand,imgv_logo,txt_split,txt_slow,txt_fast,txt_shrink,
                txt_expand,power_up);
        root2.getChildren().addAll(canvas3,text,name,txt_name);
        // 4. Configure the components (colors, fonts, size, location)
        // paddle.relocate(200, 700);
        ball.relocate(paddle.getTranslateX()+paddle.getWidth()/2-ball.getRadius(),paddle.getTranslateY()-ball.getRadius()*3);
        // 5. Add Event Handlers and do final setup
        paddle.requestFocus();
        canvas.setOnMouseMoved(paddleHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, ballMovementBeforeRelease);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, ballRelease);
        canvas2.setOnMouseClicked(event->{
            
            //root.translateYProperty().set(root1.getHeight());
            //root1.getChildren().add(root);
        
        
//        FadeTransition ft = new FadeTransition(Duration.millis(7000), root1);
//     ft.setFromValue(1.0);
//     ft.setToValue(0.3);
//     ft.setCycleCount(4);
//     ft.setAutoReverse(true);
// 
//     ft.play();
//     stage.setScene(scene2);
//     
//     Timeline timeline = new Timeline();
//
//    KeyValue transparent = new KeyValue(root1.opacityProperty(), 0.0);
//    KeyValue opaque = new KeyValue(root.opacityProperty(), 1.0);
//
//         KeyFrame startFadeIn = new KeyFrame(Duration.ZERO, transparent);
//    KeyFrame endFadeIn = new KeyFrame(Duration.millis(250), opaque);
//    KeyFrame startFadeOut = new KeyFrame(Duration.millis(750), opaque);
//    KeyFrame endFadeOut = new KeyFrame(Duration.millis(1000),e->stage.setScene(scene2),transparent);
//    timeline.getKeyFrames().addAll(startFadeIn, endFadeIn, startFadeOut, endFadeOut);
//
//    timeline.setCycleCount(Animation.INDEFINITE);
//    timeline.play();
    
    FadeTransition fd = new FadeTransition();
    fd.setDuration(Duration.millis(1000));
    fd.setNode(root1);
    fd.setFromValue(1);
    fd.setToValue(0);
    fd.setOnFinished(e->{
        stage1.setScene(scene2);
        root.setOpacity(0);
        FadeTransition fd1 = new FadeTransition();
        fd1.setDuration(Duration.millis(1000));
        fd1.setNode(root);
        fd1.setFromValue(0);
        fd1.setToValue(1);
        fd1.setOnFinished(e1->root.setOpacity(1));
        fd1.play();
        ss.play(); 
            });
    fd.play();
    
        
    
    //stage.setScene(scene2);
//        Timeline timeline = new Timeline();
//        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
//        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
//        timeline.getKeyFrames().add(kf);
//        timeline.setOnFinished(t -> {
//            parentContainer.getChildren().remove(anchorRoot);
//        });
        //timeline.play();
                });
        
   
        //root.addEventHandler(MouseEvent.MOUSE_CLICKED, bricks);
        // 6. Show the stage
        stage1.show();
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
