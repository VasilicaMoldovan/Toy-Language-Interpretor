package Model.Statement;

import Model.Exceptions.MyException;
import Model.PrgState;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return null;
    }
}
