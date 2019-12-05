package Model.DataStructures;

import Model.DataStructures.MyIStack;

import java.util.Iterator;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop(){
        return stack.pop();
    }

    @Override
    public void push(T el){
        stack.push(el);
    }

    @Override
    public boolean isEmpty(){
        return stack.empty();
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ExeStack:\n");
        int size = this.stack.size() - 1;
        for (int index = 0; index <= size; index++)
            result.append(this.stack.get(index).toString()).append("\n");

        return result.toString();
    }
}
