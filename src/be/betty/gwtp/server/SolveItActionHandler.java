package be.betty.gwtp.server;

import java.util.Map;
import java.util.TreeSet;

import net.sf.cpsolver.ifs.solution.Solution;
import net.sf.cpsolver.ifs.solver.Solver;
import net.sf.cpsolver.ifs.util.DataProperties;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import be.betty.gwtp.client.ClientUtils;
import be.betty.gwtp.client.action.SolveIt;
import be.betty.gwtp.client.action.SolveItResult;
import be.betty.gwtp.server.solver.Activity;
import be.betty.gwtp.server.solver.Location;
import be.betty.gwtp.server.solver.TimetableModel;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class SolveItActionHandler implements
		ActionHandler<SolveIt, SolveItResult> {

	@Inject
	public SolveItActionHandler() {
	}

	@Override
	public SolveItResult execute(SolveIt action, ExecutionContext context)
			throws ActionException {
		SolveItResult result = new SolveItResult("");
		
		DataProperties properties = new DataProperties();
		
		System.out.println("--------------- time start (0 sec)");
		long timeMilis= System.currentTimeMillis();
		int project_id=15;
		int nrTests = 1;
		TimetableModel m = TimetableModel.loadFromHibernate(project_id);
		System.out.println("model Loaded: ");
		System.out.println("---(model done)------------  time: "+(System.currentTimeMillis()-timeMilis)/1000+ " sec.");




		Solver<Activity, Location> s = new Solver<Activity, Location>(properties);
		s.setInitalSolution(m);
		s.currentSolution().clearBest();
		s.start();
		try {
			s.getSolverThread().join();
		} catch (NullPointerException npe) {
			System.out.println("NullPointerException has been catched");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (s.lastSolution().getBestInfo() == null)
			System.out.println("no solution found");
		System.out.println("Last solution:" + s.lastSolution().getInfo());
		Solution<Activity, Location> best = s.lastSolution();
		System.out.println("Best solution:" + s.lastSolution().getBestInfo());
		best.restoreBest();
		int val = 0;
		System.out.println("-- (solver Done)----------  time: "+(System.currentTimeMillis()-timeMilis)/1000+ " sec.");
		for (Activity varr : ((TimetableModel) best.getModel()).assignedVariables())
			val += (int) varr.getAssignment().toDouble();

		// print comment
        if (!m.assignedVariables().isEmpty()) {
            StringBuffer comments = new StringBuffer("Solution Info:\n");
            Map<String, String> solutionInfo = (best == null ? m.getInfo() : best.getInfo());
            for (String key : new TreeSet<String>(solutionInfo.keySet())) {
                String value = solutionInfo.get(key);
                comments.append("    " + key + ": " + value + "\n");
            }
          //  System.out.println("comments==>"+comments);
            ClientUtils.notifyUser("comments==>"+comments);
        }
        
        m.saveToHibernate(best, action.getInstanceId());

        System.out.println("---------------  time: "+(System.currentTimeMillis()-timeMilis)/1000+ " sec.");
		
		return result;
	}

	@Override
	public void undo(SolveIt action, SolveItResult result, ExecutionContext context)
			throws ActionException {
	}

	@Override
	public Class<SolveIt> getActionType() {
		return SolveIt.class;
	}
}
