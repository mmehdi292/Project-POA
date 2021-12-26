package AppCore;

import java.util.ArrayList;
import java.util.List;

import Configuration.AppTexts;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class Container {
	AgentContainer agentContainer;
	private List<DataSync> agent;
	public Container() {
		this.agent = new ArrayList<DataSync>();
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			this.agentContainer = runtime.createAgentContainer(profileImpl);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addAgent(String agentName,String agentClassName,int negociality,String listBook) {
		
		try {
			
			AgentController agentController = this.agentContainer.createNewAgent(agentName, AppTexts.corePackageName+agentClassName,new Object[] {negociality+"",listBook});
			this.agent.add(new DataSync(agentName,agentClassName,negociality,listBook));
			agentController.start();
			return true;
		} catch (StaleProxyException e) {
			return false;
		}
		
		
	}
	
	public List<AgentController> getAllAgent(){
		List<AgentController> allAgents = new ArrayList<AgentController>();
		for(DataSync name:agent) {
			try {
				allAgents.add(agentContainer.getAgent(name.agentName));
			} catch (ControllerException e) {
				System.out.println("Agent Name error");
			}
		}
		return allAgents;
	}

	public List<String> getAgentNames() {
		List<String> agentNames = new ArrayList<String>();
		for(DataSync d :agent) {
			agentNames.add(d.agentName);
		}
		return agentNames;
	}

	
	public AgentController getAgentByName(String name) {
		try {
			return agentContainer.getAgent(name);
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public AgentContainer getAgentContainer() {
		return agentContainer;
	}

	public List<DataSync> getAgents() {
		return agent;
	}
	
	public DataSync getAgentInfo(String name) {
		for(DataSync c :agent) {
			if(c.agentName.equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
		
	}
	
	
}