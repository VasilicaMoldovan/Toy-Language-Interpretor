package Model.Statement;

import Model.Expressions.Exp;
import Model.Exceptions.MyException;
import Model.PrgState;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp newExp){
        this.exp = newExp;
    }

    @Override
    public String toString(){

        return "Print(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try{
            state.addOutput(exp.eval(state.getSymTable(), state.getHeapTable()));
        }
        catch(MyException error){
            throw new MyException(error.getMessage());
        }
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new PrintStmt(exp);
    }
}
