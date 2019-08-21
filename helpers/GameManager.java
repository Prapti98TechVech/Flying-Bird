package helpers;

public class GameManager {
    private static final GameManager ourInstance = new GameManager();

    private String[] birds={"Blue" , "Red","Green"};
    private int index=0;

    private GameManager() {
    }

    public void incrementIndex(){
        index++;
        if (index==birds.length){
            index=0;
        }
    }

    public String getBirds(){
        return birds[index];
    }

    public static GameManager getInstance() {
        return ourInstance;
    }
}
