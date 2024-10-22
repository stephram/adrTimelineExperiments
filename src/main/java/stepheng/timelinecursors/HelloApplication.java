package stepheng.timelinecursors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 800;

    private GraphicsContext g;

    private long ps = 0;
    private long fr = 0;

    private Text text;
    private Line cursor;
    private Line lineNth;
    private Line lineSth;
    private Canvas canvas;

    private boolean bResizing = false;

    private Parent createContent() {
        final var root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        root.setCache(true);
        root.setCacheHint(CacheHint.SPEED);

        final var rootSizeListener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                final var w = root.getWidth();
                final var h = root.getHeight();

                System.out.println(w + " x " + h);
                canvas.setWidth(w);
                canvas.setHeight(h);
                layoutCanvas(w, h);
            }
        };

        root.widthProperty().addListener(rootSizeListener);
        root.heightProperty().addListener(rootSizeListener);

        canvas = new Canvas(WIDTH, HEIGHT);
        g = canvas.getGraphicsContext2D();

        final var timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!bResizing) {
                    Platform.runLater(() -> draw());
                }
            }
        };
        timer.start();

        text = new Text();
        text.setFont(Font.font("Monaco", 24));

        cursor = new Line();
        cursor.setStrokeWidth(3.0);
        cursor.setStrokeLineCap(StrokeLineCap.ROUND);
        cursor.setBlendMode(BlendMode.ADD);
        cursor.setCache(true);
        cursor.setCacheHint(CacheHint.QUALITY);

        lineNth = new Line();
        lineNth.setStrokeWidth(3.0);
        lineSth = new Line();
        lineSth.setStrokeWidth(3.0);

        layoutCanvas(WIDTH, HEIGHT);

        root.getChildren().addAll(lineNth, lineSth, text, cursor, canvas);

        return root;
    }

    private void layoutCanvas(double width, double height) {
        text.setX(10);
        text.setY(height - 20);

        cursor.setStartX(0.0);
        cursor.setStartY(20.0);
        cursor.setEndX(0.0);
        cursor.setEndY(height - 56);

        lineNth.setStartX(0.0);
        lineNth.setEndX(width);
        lineNth.setStartY(20.0);
        lineNth.setEndY(20.0);

        lineSth.setStartX(0.0);
        lineSth.setEndX(width);
        lineSth.setStartY(height - 55);
        lineSth.setEndY(height - 55);
    }


    private void draw() {
        ++fr;

        final var cs = System.currentTimeMillis();

        final var cw = canvas.getWidth();
        final var ch = canvas.getHeight();

        final var td = cs - ps;
        final var fps = 1000.0 / td;
        final var ppf = cw / (6.0 * 30.0);

        final var cx =  (fr % (6.0 * 30.0)) * ppf;

        final var txt = String.format("%3d, fps=%.1f, (%.2f x %.2f), cx=%4.1f, ppf=%.2f", td, fps, cw, ch, cx, ppf);
        text.setText(txt);
        ps = cs;

        cursor.relocate(cx, 20);
    }

    @Override
    public void start(Stage stage) throws IOException {
        final var contentPane = createContent();

        contentPane.onDragEnteredProperty().addListener(observable -> bResizing = true);
        contentPane.onDragExitedProperty().addListener(observable -> bResizing = false);

        stage.setScene(new Scene(contentPane));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}