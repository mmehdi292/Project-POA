package AppCore;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class BookSellerAgent extends Agent {

	private static final long serialVersionUID = -2703312763019160104L;
	public int negociality;
	public AID client = null;
	public ParallelBehaviour parallelBehaviour;
	public String bookName;
	public Double budget;
	public AID bestOffer = null;
	public double bestPrice = 0;
	public double priceForNegocation = 0;
	public AID[] sellerAgents;
	public String idConversationWithClient;
	public int count = 0;

	@Override
	protected void setup() {
		this.parallelBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
		Object[] args = getArguments();
		if (args.length == 1) {
			this.negociality = Integer.parseInt((String) args[0]);
		}
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("book-buying");
		sd.setName("free");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(parallelBehaviour);

		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

			private static final long serialVersionUID = -5767812980854501536L;

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					switch (msg.getPerformative()) {
					case ACLMessage.CFP:
						if (client != null) {
							System.out.println("[seller] client not == null");
							break;
						} else {
							ACLMessage message = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
							message.addReceiver(msg.getSender());
							message.setContent("Ok");
							send(message);
							System.out.println("[seller] client ACCEPT_PROPOSAL sended");
						}
						break;
					case ACLMessage.INFORM:
						if (client != null) {
							System.out.println("[seller] inform client != null");
							double price = Double.parseDouble(msg.getContent());
							if (bestOffer == null && price != 0) {
								bestPrice = price;
								bestOffer = msg.getSender();
							}
							if (price < bestPrice && price != 0) {
								bestPrice = price;
								bestOffer = msg.getSender();
							}
							count--;
							if (count == 0) {
								negocation(myAgent);
							}
							System.out.println("[seller] inform negosaitaion");

						}

						else {
							ACLMessage message = new ACLMessage(ACLMessage.AGREE);
							message.addReceiver(msg.getSender());
							message.setContent("Ok wait");
							send(message);
							canelOffer();
							client = msg.getSender();
							String[] data = msg.getContent().split(",");
							bookName = data[0];
							System.out.println("Bookname: " + bookName);
							budget = Double.parseDouble(data[1]);
							idConversationWithClient = message.getConversationId();
							System.out.println("[Seller] client is: " + client);
							updateBuyerAgentList(myAgent);
							System.out.println("[seller] seach best offer");
							parallelBehaviour.addSubBehaviour(new BestOffreBehaviour() {

								private static final long serialVersionUID = 2357832758328538832L;

								@Override
								public void action() {
									if (count == 0)
										updateBuyerAgentList(myAgent);
									try {
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								@Override
								public boolean done() {
									return bestOffer != null;
								}
							});
						}
						System.out.println("[seller] end seach bihavoirs");
						break;
					case ACLMessage.ACCEPT_PROPOSAL:
						System.out.println("[seller] send message for buyer [ACCEPT_PROPOSAL]");
						ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);
						message.addReceiver(client);
						message.setContent("Your book (" + bookName + ") has been successfully purchased for "
								+ priceForNegocation + " DA and you will have " + (budget - bestPrice)
								+ " DA left And I took " + (bestPrice - priceForNegocation) + " dinars");
						send(message);
						System.out.println("[seller] send message for client [CONFIRM]");
						reinitValues();
						break;
					case ACLMessage.REJECT_PROPOSAL:
						System.out.println("[seller] send message for buyer [reject]");
						ACLMessage ms = new ACLMessage(ACLMessage.DISCONFIRM);
						ms.addReceiver(client);
						ms.setContent("I couldn't buy the book");
						send(ms);
						System.out.println("[seller] send message for client [DISCONFIRM]");
						reinitValues();
						break;
					case ACLMessage.CANCEL:
						System.out.println("[seller] send message for buyer [CANCEL]");
						ACLMessage ms1 = new ACLMessage(ACLMessage.DISCONFIRM);
						ms1.addReceiver(client);
						ms1.setContent("I couldn't buy the book");
						send(ms1);
						System.out.println("[seller] send message for client [DISCONFIRM]");
						reinitValues();
						break;
					default:
						ACLMessage mess = new ACLMessage(ACLMessage.REFUSE);
						mess.addReceiver(msg.getSender());
						mess.setContent("REFUSE");
						send(mess);
						reinitValues();

					}
				} else {
					block();
				}

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
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	public void canelOffer() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	


	public void updateBuyerAgentList(Agent myAgent) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("book-selling");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			sellerAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				sellerAgents[i] = result[i].getName();
				ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
				message.addReceiver(sellerAgents[i]);
				message.setContent(bookName);
				send(message);
				System.out.println("[seller] send message for buyer [request]");
				this.count++;
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	public void negocation(Agent myAgent) {
		ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
		if (bestPrice != 0) {
			message.addReceiver(bestOffer);
			if (budget <= bestPrice) {
				priceForNegocation = bestPrice - (((100 - negociality) * bestPrice) / 100);
			} else {
				priceForNegocation = bestPrice - ((10 * bestPrice) / 100);
			}
			message.setContent(bookName + "," + priceForNegocation);
			send(message);
			System.out.println("[seller] send message for buyer [propose](negociation):  " + priceForNegocation);
		} else {
			updateBuyerAgentList(myAgent);
		}

	}

	public void reinitValues() {
		client = null;
		bookName = "";
		budget = 0.0;
		bestOffer = null;
		bestPrice = 0;
		priceForNegocation = 0;
		sellerAgents = null;
		count = 0;
	}

}
