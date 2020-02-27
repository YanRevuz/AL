package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Infrastructure;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.exemple.CommunicationParMessage;
import MASInfrastructure.exemple.Message;

public class main {

    public static void main(String[] args) {
        Infrastructure i = new Infrastructure();  // un scheduler et un annuaire
        CommunicationParMessage maCom= new CommunicationParMessage(i);

        AgentComposant c1 = new AgentComposant("C1");
        AgentService s1 = new AgentService("S1",0,1);

        AgentComposant c2 = new AgentComposant("C2");
        AgentService s2 = new AgentService("S2",1,1);

        s1.setComposant(c1);
        s2.setComposant(c2);

        c1.addAgent(s1);
        c2.addAgent(s2);

        InfraAgent infraC1=i.createInfrastructureAgent(new LifeCycle(c1.getPerceptionComposant()), maCom);
        // la création ajoute l'agent dans l'infrastructure

        c1.setInfraAgent(infraC1);
        c1.getPerceptionComposant().setInfraAgent(infraC1);
        c1.getDecisionComposant().setInfraAgent(infraC1);
        c1.getDecisionComposant().setCommunication(i);

        InfraAgent infraC2=i.createInfrastructureAgent(new LifeCycle(c2.getPerceptionComposant()), maCom);
        // la création ajoute l'agent dans l'infrastructure

        c2.setInfraAgent(infraC2);
        c2.getPerceptionComposant().setInfraAgent(infraC2);
        c2.getDecisionComposant().setInfraAgent(infraC2);
        c2.getDecisionComposant().setCommunication(i);




        InfraAgent infraS1=i.createInfrastructureAgent(new LifeCycle(s1.getAttente()), maCom);

        s1.setInfraAgent(infraS1);
        s1.getAttente().setInfraAgent(infraS1);
        s1.getAction().setInfraAgent(infraS1);

        s1.getAction().setCommunication(i);

        InfraAgent infraS2=i.createInfrastructureAgent(new LifeCycle(s2.getAttente()), maCom);

        s2.setInfraAgent(infraS2);
        s2.getAttente().setInfraAgent(infraS2);
        s2.getAction().setInfraAgent(infraS2);

        s2.getAction().setCommunication(i);


        Message m = new Message(null, infraC1.getInfraAgentReference(),"Demande_Connexion");
        if (maCom != null) {
            System.out.println("gejrhgkerrejkherkjfhekrjfhkejfhek");
            maCom.sendMessage(m);
        }

        i.startScheduling();

    }
}
