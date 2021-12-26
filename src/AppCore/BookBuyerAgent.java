package AppCore;

import java.util.HashMap;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class BookBuyerAgent extends Agent {

	private static final long serialVersionUID = 1423082934599287784L;
	private int negociality;
	public HashMap<String, Double> booksList = new HashMap<>();
	public ParallelBehaviour parallelBehaviour;

	@Override
	protected void setup() {
		this.parallelBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
		Object[] args = getArguments();
		setNegociality(Integer.parseInt((String) args[0]));
		String[] list = ((String) args[1]).split(",");
		for(int i=0;i<list.length;i+=2) {
			booksList.put(list[i], Double.parseDouble(list[i+1]));
		}
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("book-selling");
		sd.setName("book");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(parallelBehaviour);
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

			
			private static final long serialVersionUID = -4119616744965864400L;
			public double price = 0;
			public boolean find = false;
			
			

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch(msg.getPerformative()) {
					case ACLMessage.REQUEST:
							System.out.println("[Buyer] reciver request for seller");
							ACLMessage message = new ACLMessage(ACLMessage.INFORM);
							message.addReceiver(msg.getSender());
							String book = msg.getContent();
							System.out.println("---bookname in buyer: "+book);
							booksList.forEach((k,v)->{
								System.out.println("---k in buyer: "+k);
								if(book.equalsIgnoreCase(k))
									price=v;
							});
							message.setContent(price+"");
							send(message);
							System.out.println("[Buyer] book price is: "+price);
						break;
					case ACLMessage.PROPOSE:
						System.out.println("[buyer] reciver message for buyer [propose]");
						String[] rus = msg.getContent().split(",");
						double result =Double.parseDouble(rus[1]);
						
						booksList.forEach((k,v)->{
							if(rus[0].equalsIgnoreCase(k)) {
								double priceForNegocation = v-(((negociality)*v)/100);
								if(priceForNegocation<=result) {
									ACLMessage m = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
									m.addReceiver(msg.getSender());
									m.setContent("true");
									send(m);
									System.out.println("[buyer] send message for buyer [ACCEPT_PROPOSAL]");
								}
								else {
									ACLMessage m = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
									m.addReceiver(msg.getSender());
									m.setContent("false");
									send(m);
									System.out.println("[buyer] send message for buyer [REJECT_PROPOSAL]");
									
								}
								this.find =true;
							}
							
								
						});
						if(!find) {
							find =false;
							ACLMessage m = new ACLMessage(ACLMessage.CANCEL);
							m.addReceiver(msg.getSender());
							m.setContent("We have'nt this book");
							send(m);
							System.out.println("[buyer] send message for buyer [CANCEL]");
						}

						break;
					
					}
				} else {
					block();
				}

			}
		});
		
		//recive request acl messgae 
		

		/*
		 * Iterator<String> keys = booksList.keySet().iterator(); Iterator<Double>
		 * values = booksList.values().iterator(); while( keys.hasNext() ) {
		 * System.out.println(keys.next() + "\t | " + values.next()); }
		 * System.out.println("Salut je suis l'acheteur!");
		 * System.out.println("My Name is " + this.getAID().getName());
		 * System.out.println("Je me prépare ....."); Object[]args=getArguments();
		 * if(args.length==1){ livre=(String) args[0];
		 * System.out.println("Salut l'acheteur :"+
		 * this.getAID().getName()+" est prêt \n je tente d'acheter lelivre :"+livre); }
		 * else{ System.out.println("il faut spécifier le livre comme argument");
		 * doDelete(); }
		 * 
		 * addBehaviour(new OneShotBehaviour() { Random r = new Random(); private static
		 * final long serialVersionUID = 1L;
		 * 
		 * @Override public void action() {
		 * 
		 * ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		 * message.addReceiver(new AID("acheteur "+(int) (r.nextDouble()*10),
		 * AID.ISLOCALNAME)); double prix = sc.nextDouble(); try {
		 * message.setContentObject(prix); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } send(message); }
		 * 
		 * } );
		 * 
		 * addBehaviour(new CyclicBehaviour() {
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @Override public void action() { System.out.println("Cyclic Behaviour");
		 * ACLMessage msg = receive(); if(msg!=null) {
		 * System.out.println("Reception de message: "+msg.getContent()); ACLMessage
		 * message = new ACLMessage(ACLMessage.INFORM);
		 * message.addReceiver(msg.getSender()); double prix =
		 * Double.parseDouble(msg.getContent()); if(prix<=400)
		 * message.setContent("il est bon"); else message.setContent("tres elever");
		 * 
		 * send(message); } else { block(); }
		 * 
		 * } });
		 * 
		 * 
		 * addBehaviour(new OneShotBehaviour() {
		 * 
		 * private static final long serialVersionUID = -5767812980854501536L;
		 * 
		 * @Override public void action() { ACLMessage message = new
		 * ACLMessage(ACLMessage.INFORM); message.addReceiver(new
		 * AID("acheteur 5",AID.ISLOCALNAME)); message.setContent("Livre XML");
		 * send(message); System.out.println("--------ok-----------");
		 * 
		 * }
		 * 
		 * });
		 */

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
		deregisterService();
		System.out.println("Delete service");
	}
	
	public void deregisterService() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	public int getNegociality() {
		return negociality;
	}

	public void setNegociality(int negociality) {
		this.negociality = negociality;
	}

}