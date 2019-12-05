package Model.Statement;

import Model.*;
import Model.DataStructures.MyIStack;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString(){
        return "(If " + exp.toString() + " Then " + thenS.toString() + " Else " + elseS.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStack();
        Value value = null;
        try{
            value = exp.eval(state.getSymTable(), state.getHeapTable());
        }
        catch(MyException error){
            throw new MyException(error.getMessage());
        }
        if (value.equals(new BoolValue(true)))
            stack.push(thenS);
        else
            stack.push(elseS);

        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new IfStmt(exp, thenS, elseS);
    }
}
