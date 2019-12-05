package View;

import Controller.Controller;
import Model.Exceptions.MyException;

public class RunExample extends Command {
    private Controller ctrl;
    public RunExample(String key, String desc,Controller ctrl){
        super(key, desc);
        this.ctrl=ctrl;
    }
    @Override
    public void execute() {
        try{
            ctrl.allStep(); }
        catch (MyException error) {
            System.out.println(error.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }

}
