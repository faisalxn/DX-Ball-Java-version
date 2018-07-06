
import java.util.Random;


public class GenerateBrickNumberForBonus {
    int brickNumbers[] = new int[10];

    public GenerateBrickNumberForBonus() {
        Random rand = new Random();
        for(int i = 0 ; i<10 ; i++){
            int nmb = rand.nextInt(105) ;
            brickNumbers[i]= nmb ;
        }
    }
    
}
