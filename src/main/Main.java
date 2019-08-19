package main;

import controller.Controller;
import model.Model;
import model.MyModel;
import view.MyView;
import view.View;

public class Main {

    public static void main(String[] args) {
        Model m = new MyModel();
        View v = new MyView();
        Controller c = new Controller(m, v);
        c.startTurn();
    }

}
