package bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;
import helpers.GameManager;

public class Bird extends Sprite {

    private World world;
    private Body body;

    private TextureAtlas birdAtlas;
    private Animation animation;
    private float elapsedTime;

    private Texture birdDead;

    private boolean isAlive1;

    public Bird(World world, float x, float y){
        super(new Texture("Birds/"+ GameManager.getInstance().getBirds()+"/Idle.png"));

        birdDead=new Texture("Birds/"+GameManager.getInstance().getBirds()+"/Dead.png");
        this.world=world;
        setPosition(x, y);
        createBody();
        createAnimation();

        //isAlive1=true;
    }

    private void createBody() {
        BodyDef bodyDef=new BodyDef();

        bodyDef.type=BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(getX()/ GameInfo.PPM,getY()/GameInfo.PPM);

        body=world.createBody(bodyDef);
        body.setFixedRotation(false);

        CircleShape cshape=new CircleShape();
        cshape.setRadius((getHeight()/2f)/GameInfo.PPM);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.shape=cshape;
        fixtureDef.density=1f;
        fixtureDef.filter.categoryBits = GameInfo.BIRD;
        fixtureDef.filter.maskBits = GameInfo.GROUND | GameInfo.PIPE |GameInfo.SCORE;

        Fixture fixture=body.createFixture(fixtureDef);
        fixture.setUserData("Bird");

        cshape.dispose();

        body.setActive(false);
    }

    public void activateBird(){
        isAlive1=true;
        body.setActive(true);
    }

    public void birdFlap(){
        body.setLinearVelocity(0,3);
    }

    public void drawIdle(SpriteBatch batch){

        if (!isAlive1) {
            batch.draw(this, getX() - getWidth() / 2f, getY() - getHeight() / 2f);
        }
    }

    public void animateBird(SpriteBatch batch){
        if (isAlive1){
            elapsedTime+= Gdx.graphics.getDeltaTime();

            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),getX()-getWidth()/2f,getY()-getHeight()/2f);
        }
    }

    public void updateBird(){
        setPosition(body.getPosition().x * GameInfo.PPM,body.getPosition().y * GameInfo.PPM);
    }

    public void createAnimation(){
        birdAtlas=new TextureAtlas("Birds/"+GameManager.getInstance().getBirds()+"/"+GameManager.getInstance().getBirds()+" Bird.atlas");
        animation=new Animation(1f/7f,birdAtlas.getRegions());
    }

    public void setAlive(boolean isAlive){
        this.isAlive1=isAlive;
    }
    public boolean getAlive(){
        return isAlive1;
    }

    public void birdDie(){
        this.setTexture(birdDead);
    }
}
