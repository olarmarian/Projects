package UI;

import service.Service;

public class Menu {

    public static Menulist getMenu(Service s){
        return new Console(s);
    }
}
