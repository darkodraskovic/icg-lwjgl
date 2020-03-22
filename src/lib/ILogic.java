package lib;

public interface ILogic {

    void init(Window window) throws Exception;
    
    void input(Window window, Mouse mouseInput);

    void update(float interval, Mouse mouseInput);
    
    void render(Window window);
    
    void cleanup();
}