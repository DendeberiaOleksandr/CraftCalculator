import com.google.gson.Gson;
import gui.Frame;
import service.ItemService;

public class Main {

    public static void main(String[] args) {

        try {
            Frame frame = new Frame(new ItemService(new Gson()));
            frame.postConstruct();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
