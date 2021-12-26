package AppCore;

import Screen.CommunicationResultScreen;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class BookClientAgent extends Agent {

	private static final long serialVersionUID = -2703312763019160104L;
	public int negociality;
	public ParallelBehaviour parallelBehaviour;
	public AID seller = null;
	public double budget;
	public String book;
	public String idConversation;

	@Override
	protected void setup() {
		this.parallelBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
		
		Object[] args = getArguments();
		
		this.negociality = Integer.parseInt((String) args[0]);
		String[] data = args[1].toString().split(",");
		this.book = data[0];
		this.budget = Double.parseDouble(data[1]);
		

		addBehaviour(parallelBehaviour);
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

			private static final long serialVersionUID = -4119616744265864400L;

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch(msg.getPerformative()) {
					case ACLMessage.ACCEPT_PROPOSAL:
						if(seller != null){
							System.out.println("[client] seller not == nill");
							break;
							
						}
						else {
							ACLMessage message = new ACLMessage(ACLMessage.INFORM);
							message.addReceiver(msg.getSender());
							message.setContent(book+","+budget);
							send(message);
							seller = msg.getSender();
							System.out.println("[Client] seller is: "+seller);
						}
						break;
					case ACLMessage.AGREE:
						seller = msg.getSender();
						idConversation = msg.getConversationId();
						break;
						
					case ACLMessage.CONFIRM:
						String s = msg.getContent();
						CommunicationResultScreen.addNewResulat(s);
						takeDown();
						break;
					case ACLMessage.DISCONFIRM:
						seller = null;
						String s1 = msg.getContent();
						CommunicationResultScreen.addNewResulat(s1);
						break;
					
					}
				} else {
					block();
				}

			}
		});

		parallelBehaviour.addSubBehaviour(new SearchBehaviour() {
			
			private static final long serialVersionUID = -2332984661998898954L;
			public void action() {
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("book-buying");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent, template);
					ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
					for (int i = 0; i < result.length; i++) {
						cfp.addReceiver(result[i].getName());
					}
					cfp.setContent("Buy a book for me?");
					myAgent.send(cfp);
					System.out.println("[client] send CFP for all seller");
					Thread.sleep(20000);
				} catch (FIPAException fe) {
					fe.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean done() {
				return seller == null ? false:true;
			}

		});
	}

	@Override
	protected void beforeMove() {
		System.out.println("Avant de migrer vers une nouvelle location .....");
	}

	@Override
	protected void afterMove() {
		System.out.println("Je viens d'arriver à une nouvelle location .....");
	}

	@Override
	protected void takeDown() {
		System.out.println("avant de mourir .....");
	}
	
	
}
