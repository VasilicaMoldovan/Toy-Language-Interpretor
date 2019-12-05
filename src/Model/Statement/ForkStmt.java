package Model.Statement;

import Model.DataStructures.MyStack;
import Model.Exceptions.MyException;
import Model.PrgState;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt newStatement){
        statement = newStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        return new PrgState(new MyStack<>(), state.getSymTable().deepcopy(),
                state.getOut(), this.statement, state.getFileTable(), state.getHeapTable(), state.getId());
    }

    @Override
    public IStmt deepCopy(){
        return new ForkStmt(statement);
    }

    @Override
    public String toString(){
        return "fork(" + statement.toString() + ")";
    }
}
