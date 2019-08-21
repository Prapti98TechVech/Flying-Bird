package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.praptik.game.GameMain;

import helpers.GameInfo;
import helpers.GameManager;
import scenes.GamePlay;

public class MainMenuButtons {

    private GameMain game;
    private Viewport gameViewPort;
    private Stage stage;
    private ImageButton playBtn,scoreBtn,changeBirdBtn;

    private Label scoreLabel;

    public MainMenuButtons(GameMain game){
        this.game=game;
        gameViewPort=new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT,new OrthographicCamera());

        stage=new Stage(gameViewPort,game.getBatch());

        createAndPositionButtons();
        stage.addActor(playBtn);
        stage.addActor(scoreBtn);

        changeBirds();
    }

    void createAndPositionButtons(){

        playBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Play.png"))));
        scoreBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Score.png"))));

        playBtn.setPosition(GameInfo.WIDTH / 2f - 100f , GameInfo.HEIGHT / 2f -50f, Align.center);
        scoreBtn.setPosition(GameInfo.WIDTH / 2f + 100f, GameInfo.HEIGHT / 2f -50f, Align.center);

        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GamePlay(game));
                stage.dispose();
            }
        });
        scoreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showScore();
            }
        });
    }

    void changeBirds(){

        if (changeBirdBtn != null){
            changeBirdBtn.remove();
        }

        changeBirdBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Birds/"+ GameManager.getInstance().getBirds()+"/Idle.png"))));

        changeBirdBtn.setPosition(GameInfo.WIDTH / 2f , GameInfo.HEIGHT / 2f + 200, Align.center);
        changeBirdBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getInstance().incrementIndex();

                changeBirds();
            }
        });

        stage.addActor(changeBirdBtn);
    }

    void showScore(){

        if (scoreLabel!=null){
            return;
        }

        FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("Flappy Bird Font/04b_19.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=100;

        BitmapFont font=generator.generateFont(parameter);

        Preferences prefs=Gdx.app.getPreferences("Data");

        scoreLabel=new Label(String.valueOf(prefs.getInteger("Score")),new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setPosition(GameInfo.WIDTH / 2f  , GameInfo.HEIGHT / 2f - 200,Align.center);

        stage.addActor(scoreLabel);
    }

    public Stage getStage(){
        return this.stage;
    }
}
