package UI.Gui;

public interface Observer<E extends Event> {
    void update(E e) throws Exception;
}
