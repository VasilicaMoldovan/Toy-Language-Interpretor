package Model.Statement;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    Exp expression;
    String variableName;

    public ReadFileStmt(Exp newExp, String name){
        expression = newExp;
        variableName = name;
    }

    @Override
    public String toString(){
        return " read " + variableName + " from " + expression.toString();
    }

    @Override
    public IStmt deepCopy(){
        return new ReadFileStmt(expression, variableName);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        //state.getStack().pop();
        if ( state.getSymTable().isDefined(variableName) && state.getSymTable().getValue(variableName).getType().equals(new IntType())) {
                Value result = expression.eval(state.getSymTable(), state.getHeapTable());
                if ( result instanceof StringValue) {
                    //boolean hep = state.getFileTable().isDefined((StringValue)result);
                    if (state.getFileTable().isDefined((StringValue)result)) {
                        try {
                            BufferedReader buff = state.getFileTable().getValue((StringValue) result);
                            String line = buff.readLine();
                            IntValue newValue;
                            if (line == null) {
                                newValue = new IntValue(0);
                            } else {
                                newValue = new IntValue(Integer.parseInt(line));
                            }
                            state.getSymTable().update(variableName, newValue);
                        }
                        catch (IOException error){
                            throw new MyException(error.getMessage());
                        }
                    }
                    else
                        throw new MyException("Undefined key");
                }
                else
                    throw new MyException("Not String value");
        }
        else
            throw new MyException("Invalid variable name");
        return null;
    }
}
