package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository myController;
    private GarbageCollector myGarbageCollector;
    private ExecutorService executor;

    public Controller(IRepository repository){
        myController = repository;
        this.myGarbageCollector = new GarbageCollector();
    }

   public ArrayList removeCompletedProgram(ArrayList<PrgState> inProgramList){
        return (ArrayList)inProgramList.stream().filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
   }

   public IStmt getOriginalProgram(){
        return myController.getCurrentProgramState().getOriginalProgram();
   }

    public void oneStepForAllPrograms(List<PrgState> programs) throws MyException,InterruptedException
    {
        programs.forEach(program-> {
            try
            {
                myController.logPrgStateExec(program);
            }
            catch (MyException e)
            {
                System.out.println(e.toString());
            }
        });

        List<Callable<PrgState>> callList=programs.stream().map((PrgState p) -> (Callable<PrgState>)()->{return p.oneStep();}).collect(Collectors.toList());

        List<PrgState> newProgramList=null;
        try
        {
            newProgramList=executor.invokeAll(callList).stream().
                    map(future-> {
                        try
                        {
                            return future.get();
                        }
                        catch(InterruptedException | ExecutionException e)
                        {
                            System.out.println(e.toString());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

        }
        catch (InterruptedException e)
        {
            System.out.println(e.toString());
        }

        programs.addAll(newProgramList);
        programs.forEach(program->
        {
            try
            {
                myController.logPrgStateExec(program);
                System.out.println(myController.getCurrentProgramState().toString()+"\n");
            }
            catch(MyException e)
            {
                System.out.println(e.toString());
            }
        });
        myController.setProgramList((ArrayList)programs);
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        ArrayList prgList=removeCompletedProgram(myController.getProgramList());
        while(prgList.size() > 0){

            myController.getCurrentProgramState().getHeapTable().setContent(
                    myGarbageCollector.garbageCollector(
                            myGarbageCollector.addIndirectRef(myGarbageCollector.getAddressFromTables((ArrayList)myController.getProgramList()),myController.getCurrentProgramState().getHeapTable()),
                            myController.getCurrentProgramState().getHeapTable()));

            try {
                oneStepForAllPrograms(prgList);
            }catch (MyException | InterruptedException e){
               System.out.println("Exception");
            }

            prgList = removeCompletedProgram(myController.getProgramList());
        }
        executor.shutdownNow();
        myController.setProgramList((ArrayList)prgList);
    }
}
