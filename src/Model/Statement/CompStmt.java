package Model.Statement;

import Model.Exceptions.MyException;
import Model.DataStructures.MyIStack;
import Model.PrgState;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt newFirst, IStmt newSecond){
        this.first = newFirst;
        this.second = newSecond;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);

        return null;
    }

    @Override
    public String toString(){
        return first.toString() + " " + second.toString();
        //return "(" + first.toString() + ";" + second.toString()+")";
    }

    @Override
    public IStmt deepCopy(){
        return new CompStmt(first, second);
    }

}
